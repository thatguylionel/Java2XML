package de.tgl.Java2XML;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import de.tgl.Java2XML.obj.Employee;
import de.tgl.Java2XML.obj.Simple;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("generateDocument")
public class Java2XMLDemo {
    private static final Logger LOGGER = Logger.getLogger(Java2XMLDemo.class.getName());

    @GET
    public static String generateDocument() throws Exception {
        //  change directory
        String directory = "D:\\tmp";
        String name = directory + "\\Example - " + UUID.randomUUID() + ".xml";
        Util.generateDirectory(directory);

        // First create Stax components we need
        XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newFactory();
        FileOutputStream fileOutputStream = new FileOutputStream(name);
        XMLStreamWriter sw = xmlOutputFactory.createXMLStreamWriter(fileOutputStream, "UTF-8");

        // then Jackson components
        XmlMapper mapper = new XmlMapper(xmlInputFactory);

        // Start document and setup content of document
        sw.writeStartDocument();
        setupRootAndContent(sw, mapper, name);
        sw.writeEndDocument();

        //  Flush and Close writer/stream
        sw.flush();
        sw.close();
        fileOutputStream.close();
        fileOutputStream.flush();

        LOGGER.log(Level.INFO, "File Created - " + name);

        //  return contents of file to string
        return Util.readFile(name, Charset.forName("UTF-8"));
    }


    /**
     * The contents of the below method will generate this file example:
     * <?xml version='1.0' encoding='UTF-8'?>
     * <root>
     *      <details>
     *          <Simple><x>1</x><y>2</y></Simple>
     *          <Employee><code>CAJ</code><name>Cathy Jones</name><salary>300000</salary></Employee>
     *      </details>
     *      <!--The file has generated at location D:\tmp\ex-6ce24f53-7967-4c18-911f-cd9ce6cbaf53.xml-->
     * </root>
     *
     * @param sw {@link XMLStreamWriter}
     * @param mapper {@link XmlMapper}
     * @param name {@link String}
     * @throws XMLStreamException raises {@link XMLStreamException}
     * @throws IOException raises {@link IOException}
     */
    private static void setupRootAndContent(XMLStreamWriter sw, XmlMapper mapper, String name) throws XMLStreamException, IOException {
        sw.writeStartElement("root");
        sw.writeStartElement("details");

        // Write whatever content POJOs...
        mapper.writeValue(sw, new Simple());
        Employee object = new Employee();
        object.setCode("CAJ");
        object.setName("Cathy Jones");
        object.setSalary(300000);


        mapper.writeValue(sw, object);
        sw.writeEndElement();   //  close DETAILS element

        sw.writeComment("The file has generated at location " + name);
        sw.writeEndElement();   //  close ROOT element
    }


}

