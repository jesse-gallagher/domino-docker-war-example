# Initial Maven build
FROM maven:3.5.4-jdk-8-alpine AS builder
COPY pom.xml settings.xml ./
RUN mkdir /root/.m2/ \
    && mv settings.xml /root/.m2/ \
    && mvn dependency:go-offline package \
    && rm /root/.m2/settings.xml
COPY src src/
#RUN mvn install -DskipTests --offline
RUN mvn install -DskipTests

# Bring in the Liberty server and extract portions of the wrapped project server distribution
FROM open-liberty as server-setup
USER root

# Configure the runtime image
FROM open-liberty

# Bring in the Domino runtime
COPY --chown=default:users notesdata /local/notesdata
COPY --from=domino-docker:V1101_03212020prod /opt/hcl/domino/notes/11000100/linux /opt/hcl/domino/notes/latest/linux
# TODO figure out the required subset
#COPY --from=domino-docker:V1101_03212020prod /opt/hcl/domino/notes/11000100/linux/*.so* /opt/hcl/domino/notes/latest/linux/
#COPY --from=domino-docker:V1101_03212020prod /opt/hcl/domino/notes/11000100/linux/*.lss /opt/hcl/domino/notes/latest/linux/
#COPY --from=domino-docker:V1101_03212020prod /opt/hcl/domino/notes/11000100/linux/*.res /opt/hcl/domino/notes/latest/linux/
#COPY --from=domino-docker:V1101_03212020prod /opt/hcl/domino/notes/11000100/linux/*.ini /opt/hcl/domino/notes/latest/linux/
#COPY --from=domino-docker:V1101_03212020prod /opt/hcl/domino/notes/11000100/linux/*.tlb /opt/hcl/domino/notes/latest/linux/
#COPY --from=domino-docker:V1101_03212020prod /opt/hcl/domino/notes/11000100/linux/*.dtd /opt/hcl/domino/notes/latest/linux/
#COPY --from=domino-docker:V1101_03212020prod /opt/hcl/domino/notes/11000100/linux/res /opt/hcl/domino/notes/latest/linux/res
#COPY --from=domino-docker:V1101_03212020prod /opt/hcl/domino/notes/11000100/linux/xmlschemas /opt/hcl/domino/notes/latest/linux/xmlschemas

COPY --chown=1001:0 --from=server-setup /config/ /config/
COPY --from=builder /target/org.openntf.docker.example.war /apps/
#COPY resources /opt/ol/wlp/output/defaultServer/resources/
COPY src/main/liberty/config/ /config/
EXPOSE 9080 9443
