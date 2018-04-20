FROM openjdk:8-jdk

MAINTAINER Grupo Netshoes

ADD run.sh /opt/run.sh
RUN chmod +x /opt/run.sh
ADD app/target/athena-dependency-analyzer.jar /opt/athena-dependency-analyzer.jar
EXPOSE 8080
CMD exec /opt/run.sh $MONGO_URI $RABBITMQ_ADDRESSES $GITHUB_HOST $GITHUB_ORGANIZATION $GITHUB_TOKEN $ADMIN_USERNAME $ADMIN_PASSWORD
