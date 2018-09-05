FROM airhacks/glassfish
COPY ./target/Java2XML.war ${DEPLOYMENT_DIR}
