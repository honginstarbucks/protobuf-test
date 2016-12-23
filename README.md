# Overview 

This is a minimum POC of using protobuf with Kafka. 

## Run it 

first bring up the docker images by using 

`docker-compose -f all.yml up`

next, simply run it

`sbt run`

There will be a bunch junk printed out on console and you should see a text: `I GOT IT`, and followed by an object printed twice. 

*NOTE*: if you are running Mac, update all the hard-coded IP to match your local docker IP. 

