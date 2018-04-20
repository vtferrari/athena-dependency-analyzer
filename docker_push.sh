#!/usr/bin/env bash
echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
docker push netshoes/athena-dependency-analyzer:1.0-SNAPSHOT
