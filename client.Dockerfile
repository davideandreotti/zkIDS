ARG PARENT_VERSION=latest
FROM ubuntu:22.04
LABEL maintainer="daviand"


ENV APT_DEPS git \
			python3 \
			python-is-python3 \
			pip \
			libgomp1 nano iproute2 
ENV PIP_DEPS requests ecdsa cryptography pyshark psutil

RUN apt-get update -qq && \
	apt-get upgrade -qq
RUN ln -fs /usr/share/zoneinfo/Europe/Rome /etc/localtime
RUN DEBIAN_FRONTEND=noninteractive apt-get install -qq --no-install-recommends $APT_DEPS
#RUN  apt-get -y install tzdata

RUN DEBIAN_FRONTEND=noninteractive apt-get install -qq --no-install-recommends openjdk-17-jre
RUN pip install --no-cache-dir --upgrade pip && \
	pip install --no-cache-dir $PIP_DEPS

#RUN ip route add 172.19.0.4 via 172.19.0.2 dev eth0 src 172.19.0.3

RUN apt-get autoremove --purge -qq
COPY ./Client /home/Client
COPY ./Middlebox/trackers.py /home/Middlebox/
COPY ./Middlebox/runprocess.py /home/Middlebox/
COPY ./xjsnark_decompiled /home/xjsnark_decompiled
COPY ./libsnark /home/libsnark
WORKDIR /home/
