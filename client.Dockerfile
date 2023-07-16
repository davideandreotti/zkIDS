ARG PARENT_VERSION=latest
FROM ubuntu:20.04
LABEL maintainer="daviand"


ENV APT_DEPS git \
			python3 \
			python-is-python3 \
			pip \
			libgomp1
ENV PIP_DEPS requests ecdsa cryptography pyshark

RUN apt-get update -qq && \
	apt-get upgrade -qq
RUN ln -fs /usr/share/zoneinfo/Europe/Rome /etc/localtime
RUN DEBIAN_FRONTEND=noninteractive apt-get install -qq --no-install-recommends $APT_DEPS
#RUN  apt-get -y install tzdata
RUN DEBIAN_FRONTEND=noninteractive apt-get install -qq --no-install-recommends openjdk-8-jre
RUN pip install --no-cache-dir --upgrade pip && \
	pip install --no-cache-dir $PIP_DEPS
RUN apt-get autoremove --purge -qq
COPY ./Client /home/Client
COPY ./xjsnark_decompiled /home/xjsnark_decompiled
COPY ./libsnark /home/libsnark
WORKDIR /home/