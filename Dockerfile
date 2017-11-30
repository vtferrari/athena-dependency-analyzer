FROM openjdk:latest

MAINTAINER Grupo Netshoes

ADD run.sh /opt/run.sh
RUN chmod +x /opt/run.sh
ADD app/target/athena-dependency-analyzer-1.0-SNAPSHOT.jar /opt/athena-dependency-analyzer.jar
EXPOSE 8080
CMD exec /opt/run.sh $MONGO $RABBITMQ $GITHUB_URI $GITHUB_ORGANIZATION $GITHUB_TOKEN
