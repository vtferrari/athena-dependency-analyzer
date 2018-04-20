#!/usr/bin/env bash
docker build . -t netshoes/athena-dependency-analyzer:1.0-SNAPSHOT;
docker login -u "$DOCKER_USERNAME" -p "$DOCKER_PASSWORD";
docker push netshoes/athena-dependency-analyzer;
