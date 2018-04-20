#!/bin/bash

# Grupo Netshoes

# startup parameters
export MONGO_URI=$1			    # URI to MongoDB. ex: mongodb://localhost:27017/athena
export RABBITMQ_ADDRESSES=$2	# Addresses to RabbitMQ ex: localhost:5672
export GITHUB_HOST=$3			# GitHub API HOST
export GITHUB_ORGANIZATION=$4	# GitHub Organization
export GITHUB_TOKEN=$5	        # GitHub Token
export ADMIN_USERNAME=$6	    # Username for admin
export ADMIN_PASSWORD=$7	    # Password for admin

exec $(type -p java) \
  -jar /opt/athena-dependency-analyzer.jar \
  --spring.data.mongodb.uri=${MONGO_URI} \
  --spring.rabbitmq.addresses=${RABBITMQ_ADDRESSES} \
  --application.github.host=${GITHUB_HOST} \
  --application.github.organization=${GITHUB_ORGANIZATION} \
  --application.github.token=${GITHUB_TOKEN} \
  --application.security.admin.username=${ADMIN_USERNAME} \
  --application.security.admin.password=${ADMIN_PASSWORD}
