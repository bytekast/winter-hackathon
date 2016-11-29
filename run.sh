#!/usr/bin/env bash
gradle build
docker build -t bytekast/winter-hackathon .
docker run -d -p 8080:8080 --env-file ./env.dev bytekast/winter-hackathon