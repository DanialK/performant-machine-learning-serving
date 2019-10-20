FROM ubuntu:18.04

RUN apt-get update \
	&& apt install -y default-jre \
	&& apt-get install -y gcc

RUN mkdir -p /opt/app

WORKDIR /opt/app

COPY ./model-scoring/target/scala-2.11/model-scoring-assembly-0.1.jar ./

RUN mkdir -p ./data

COPY ./data/modelFile.bin ./data/

EXPOSE 5000

CMD exec java -cp "model-scoring-assembly-0.1.jar" com.solvemprobler.SentimentModelServer

