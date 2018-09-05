#!/bin/sh
mvn clean package && docker build -t de.tgl/Java2XML .
docker rm -f Java2XML || true && docker run -d -p 8080:8080 -p 4848:4848 --name Java2XML de.tgl/Java2XML
