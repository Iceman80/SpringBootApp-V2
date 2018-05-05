#! /bin/bash
./wait-for-it.sh mypostgres:5432 -t 10
java -Djava.security.egd=file:/dev/./urandom -jar app.jar
