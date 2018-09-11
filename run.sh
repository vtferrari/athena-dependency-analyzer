#!/bin/bash

# Grupo Netshoes

# startup parameters
export MONGO_URI=$0			    # URI to MongoDB. ex: mongodb://localhost:27017/athena
export RABBITMQ_ADDRESSES=$1	# Addresses to RabbitMQ ex: localhost:5672
export RABBITMQ_HOST=$2     # Rabbitmq vhost
export RABBITMQ_USER=$3     #User for rabbitmq
export RABBITMQ_PASS=$4     #Pass for rabbitmq
export GITHUB_TOKEN=$5	        # GitHub Token
export GITHUB_HOST=$6       #User for rabbitmq
export GITHUB_ORGANIZATION=$7       #Pass for rabbitmq
export ADMIN_USERNAME=$8	    # Username for admin
export ADMIN_PASSWORD=$9	    # Password for admin


exec $(type -p java) \
  -jar /opt/athena-dependency-analyzer.jar \
  --spring.data.mongodb.uri=${MONGO_URI} \
  --spring.rabbitmq.addresses=${RABBITMQ_ADDRESSES} \
  --spring.rabbitmq.host=${RABBITMQ_HOST} \
  --spring.rabbitmq.user=${RABBITMQ_USER} \
  --spring.rabbitmq.pass=${RABBITMQ_PASS} \
  --application.github.token=${GITHUB_TOKEN} \
  --application.github.host=${GITHUB_HOST} \
  --application.github.organization=${GITHUB_ORGANIZATION} \
  --application.security.admin.username=${ADMIN_USERNAME} \
  --application.security.admin.password=${ADMIN_PASSWORD} 