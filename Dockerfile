FROM openjdk:8-jdk

MAINTAINER Grupo Netshoes

ADD run.sh /opt/run.sh
RUN chmod +x /opt/run.sh
ADD app/target/athena-dependency-analyzer.jar /opt/athena-dependency-analyzer.jar
EXPOSE 8080
ENTRYPOINT /opt/run.sh $MONGO_URI $RABBITMQ_ADDRESSES $RABBITMQ_USER $RABBITMQ_PASS $GITHUB_TOKEN $GITHUB_HOST $GITHUB_ORGANIZATION $ADMIN_USERNAME $ADMIN_PASSWORD