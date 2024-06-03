package de.tgl.Java2XML.web;

import de.tgl.Java2XML.services.XmlService;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("document")
public class DocumentController {
    private final XmlService xmlService = new XmlService.XmlServiceImpl();

    @GET
    public String createXML() {
        try {
            return xmlService.generate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}