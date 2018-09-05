## BACKGROUND AND PURPOSE

This demo project is to understand and generate an XML file using JacksonXML. The project structure is generated off Adam Bien's Lightweight Restful Archetype (V 0.0.2). 

# STANDARD MAVEN BUILD 
# BUILD	
	mvn clean package

# RUN FROM YOUR APPLICATION SERVER
Fire up the war from your Application Server of choice, copy the Java2XML.war over and run url http://localhost:8080/Java2XML/resources/generateDocument from your browser, 
	
	
# OR RUN WITH DOCKER
# BUILD
mvn clean package && docker build -t de.tgl/Java2XML .

# RUN
docker rm -f Java2XML || true && docker run -d -p 8080:8080 -p 4848:4848 --name Java2XML de.tgl/Java2XML 


# Word of thanks
Special thanks to @AdamBien for providing the archetype, as well as the comprehensive writeup from FasterXML's Github repo.

Links:
Adam's Github Repos:	https://github.com/AdamBien

FASTERXML Github Repo:	https://github.com/FasterXML/jackson-dataformat-xml
