#!/bin/bash

# Grupo Netshoes

# startup parameters
export MONGO=$1			# URI to MongoDB. ex: mongodb://localhost:27017/athena
export RABBITMQ=$2		# RabbitMQ hostname or IP address
export GITHUB_URI=$3		# GitHub URI
export GITHUB_ORGANIZATION=$4	# GitHub Oragnization
export GITHUB_TOKEN=$5	# GitHub Token

exec $(type -p java) \
  -jar /opt/athena-dependency-analyzer.jar \
  --spring.data.mongodb.uri=${MONGO} \
  --spring.rabbitmq.host=${RABBITMQ} \
  --application.github.host=${GITHUB_URI} \
  --application.github.organization=${GITHUB_ORGANIZATION} \
  --application.github.token=${GITHUB_TOKEN}
