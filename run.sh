#!/bin/bash

# Grupo Netshoes

# startup parameters
export MONGO=$1			# URI to MongoDB. ex: mongodb://localhost:27017/athena
export RABBITMQ=$2		# RabbitMQ hostname or IP address
export GITHUB_URI=$3		# GitHub URI
export GITHUB_ORGANIZATION=$4	# GitHub Oragnization
export GITHUB_USERNAME=$5	# GitHub username
export GITHUB_PASSWORD=$6     	# Github password

exec $(type -p java) \
  -jar /opt/athena-dependency-analyzer.jar \
  --spring.data.mongodb.uri=${MONGO} \
  --spring.rabbitmq.host=${RABBITMQ} \
  --application.github.host=${GITHUB_URI} \
  --application.github.organization=${GITHUB_ORGANIZATION} \
  --application.github.username=${GITHUB_USERNAME} \
  --application.github.password=${GITHUB_PASSWORD}
