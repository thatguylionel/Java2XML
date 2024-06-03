package de.tgl.Java2XML.web;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.tgl.Java2XML.obj.Employee;
import de.tgl.Java2XML.obj.Simple;
import de.tgl.Java2XML.util.FileReader;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("generateDocument")
public class GeneratorController {
    private static final Logger LOGGER = Logger.getLogger(GeneratorController.class.getName());
    private static final String RESOURCE_DIRECTORY = "generated";


    @GET
    public static String generateDocument() throws Exception {
        //  change directory
        java.nio.file.Path directoryPath = Paths.get(RESOURCE_DIRECTORY);
        Files.createDirectories(directoryPath);

        String fileName = "Example-" + UUID.randomUUID() + ".xml";
        directoryPath.resolve(fileName);

        // First create Stax components we need
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        XMLStreamWriter sw = xmlOutputFactory.createXMLStreamWriter(fileOutputStream, StandardCharsets.UTF_8.name());

        // then Jackson components
        XmlMapper mapper = new XmlMapper(xmlInputFactory);

        // Start document and setup content of document
        sw.writeStartDocument();
        setupRootAndContent(sw, mapper, fileName);
        sw.writeEndDocument();

        //  Flush and Close writer/stream
        sw.flush();
        sw.close();
        fileOutputStream.close();
        fileOutputStream.flush();

        LOGGER.log(Level.INFO, "File Created - " + fileName);

        //  return contents of file to string
        return FileReader.readFile(fileName, StandardCharsets.UTF_8);
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
    private static void setupRootAndContent(XMLStreamWriter sw, XmlMapper mapper, String name) throws XMLStreamException, IOException {
        sw.writeStartElement("root");
        sw.writeStartElement("details");

        // Write whatever content POJOs...
        mapper.writeValue(sw, new Simple(1, 2));
        mapper.writeValue(sw, new Employee("CAJ", "Cathy Jones", 300000, List.of("Max", "Minnie")));
        sw.writeEndElement();   //  close DETAILS element

        sw.writeComment("The file has generated at location " + name);
        sw.writeEndElement();   //  close ROOT element
    }


}

