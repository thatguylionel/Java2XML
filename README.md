# Java2XML Application

I was initially looking for a way to convert objects to an XML document and after some experimentation, found this to be
a viable solution.This can be useful for
various data serialization tasks, data interchange formats, and ensuring data compatibility across different systems.

As I haven't looked at the original Java 8 code in years, I decided to migrate the base to Java 21. This of course means
that quite a number of initial processes had to change. I do feel there are some additional boilerplate introduced,
however, the project is structured more soundly and makes sense from an API perspective (in my opinion of course).

## Standard Maven Build

To build the application, use the following Maven command:

```sh
mvn clean package
```

## Run from Your Application Server

1. **Deploy the WAR File:**
    - Deploy the `Java2XML.war` file to your application server of choice.
    - This can typically be done by copying the `Java2XML.war` file to the deployment directory of your server.

2. **Access the Application:**
    - Start your application server.
    - Open your browser and navigate to the following URL to generate the XML document:
      ```
      http://localhost:8080/Java2XML/api/document
      ```

**Note:** This application has been tested with Tomcat 10 (version 10.1.24 as of this writing).

## Acknowledgements

Special thanks to:

- **@AdamBien** for providing the original inspiration.
- The comprehensive writeup from FasterXML's GitHub repository for guidance on XML processing with Jackson.
