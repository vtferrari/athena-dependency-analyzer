#!/bin/bash

# Grupo Netshoes

# startup parameters
export MONGO_URI=$1			    # URI to MongoDB. ex: mongodb://localhost:27017/athena
export RABBITMQ_ADDRESSES=$2	# Addresses to RabbitMQ ex: localhost:5672
export RABBITMQ_HOST=$3     # Rabbitmq vhost
export RABBITMQ_USER=$4     #User for rabbitmq
export RABBITMQ_PASS=$5     #Pass for rabbitmq
export GITHUB_TOKEN=$6	        # GitHub Token
export GITHUB_HOST=$7       #User for rabbitmq
export GITHUB_ORGANIZATION=$8       #Pass for rabbitmq
export ADMIN_USERNAME=$9	    # Username for admin
export ADMIN_PASSWORD=$10	    # Password for admin

echo "=============================="
echo "      startup parameters"
echo "=============================="
echo $1
echo $2
echo $3
echo $4
echo $5
echo $6
echo $7
echo $8
echo $9
echo $10

echo "=============================="


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
