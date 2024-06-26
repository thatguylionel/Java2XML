package de.tgl.Java2XML.services;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.tgl.Java2XML.models.Employee;
import de.tgl.Java2XML.models.Simple;
import de.tgl.Java2XML.util.FileReader;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service interface for generating XML documents.
 */
public interface XmlService {
    /**
     * Generates an XML document based on predefined objects and returns its content as a string.
     *
     * @return the generated XML document content as a string
     * @throws Exception if an error occurs during XML generation
     */
    String generate() throws Exception;

    /**
     * Implementation of the {@link XmlService} interface.
     */
    class XmlServiceImpl implements XmlService {
        private static final Logger LOGGER = Logger.getLogger(XmlServiceImpl.class.getName());

        /**
         * Generates an XML document based on predefined objects and returns its content as a string.
         * <p>
         * This method creates a temporary directory and file, writes the XML content to the file,
         * and then reads the file content back as a string.
         *
         * @return the generated XML document content as a string
         * @throws Exception if an error occurs during XML generation
         */
        public String generate() throws Exception {
            // Create the directory if it doesn't exist
            java.nio.file.Path tempDirectory = Files.createTempDirectory("generated-");

            String fileName = UUID.randomUUID() + ".xml";
            java.nio.file.Path filePath = tempDirectory.resolve(fileName);

            // First create Stax components we need
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath.toFile())) {
                XMLStreamWriter sw = xmlOutputFactory.createXMLStreamWriter(fileOutputStream, StandardCharsets.UTF_8.name());
                XmlMapper mapper = new XmlMapper();

                // Start document and setup content of document
                sw.writeStartDocument();
                setupRootAndContent(sw, mapper, filePath.toString());
                sw.writeEndDocument();

                // Flush and close writer/stream
                sw.flush();

                LOGGER.log(Level.INFO, "File Created - {0}", filePath);

                // Return contents of file as a string
                return FileReader.readFile(filePath.toString(), StandardCharsets.UTF_8);
            } catch (XMLStreamException | IOException e) {
                LOGGER.log(Level.SEVERE, "Error generating document", e);
                throw e;
            }
        }

        /**
         * The contents of the below method will generate this file example:
         * <?xml version='1.0' encoding='UTF-8'?>
         * <root>
         * <details>
         * <Simple><x>1</x><y>2</y></Simple>
         * <Employee><code>CAJ</code><name>Cathy Jones</name><salary>300000</salary></Employee>
         * </details>
         * <!--The file has generated at location _EXAMPLE_-->
         * </root>
         *
         * @param sw     {@link XMLStreamWriter}
         * @param mapper {@link XmlMapper}
         * @param name   {@link String}
         * @throws XMLStreamException raises {@link XMLStreamException}
         * @throws IOException        raises {@link IOException}
         */
        private void setupRootAndContent(XMLStreamWriter sw, XmlMapper mapper, String name) throws XMLStreamException, IOException {
            sw.writeStartElement("root");
            sw.writeStartElement("details");
            mapper.writeValue(sw, new Simple(1, 2));
            mapper.writeValue(sw, new Employee("CAJ", "Cathy Jones", 300000, List.of("Max", "Minnie")));
            sw.writeEndElement();
            sw.writeComment("The file has generated at location " + name);
            sw.writeEndElement();
        }
    }
}
