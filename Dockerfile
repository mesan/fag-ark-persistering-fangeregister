FROM maven:3-jdk-8

MAINTAINER Erlend Kristiansen, erlendk@mesan.no, Mesan AS

EXPOSE 8080

COPY . /opt/fangerepo

WORKDIR /opt/fangerepo

RUN mvn dependency:resolve

RUN mvn package

CMD ["/usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java", "-jar", "target/persistering.fangerepo-0.0.1-SNAPSHOT.jar", "server", "fangerepo_docker.yml"]
