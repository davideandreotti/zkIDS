ARG PARENT_VERSION=latest
FROM ubuntu:20.04
LABEL maintainer="daviand"


ENV APT_DEPS git \
			python3 \
			python-is-python3 \
			pip \
			tshark

ENV PIP_DEPS requests flask pyshark

#ENV BM_RUNTIME_DEPS net-tools \
#			sudo \
#			iproute2


COPY ./Middlebox /home/Middlebox
COPY ./xjsnark_decompiled /home/xjsnark_decompiled
COPY ./libsnark /home/libsnark



RUN apt-get update -qq && \
	apt-get upgrade -qq
RUN DEBIAN_FRONTEND=noninteractive apt-get install -qq --no-install-recommends $APT_DEPS
#RUN  apt-get -y install tzdata
RUN DEBIAN_FRONTEND=noninteractive apt-get install -qq --no-install-recommends openjdk-8-jre
RUN pip install --no-cache-dir --upgrade pip && \
	pip install --no-cache-dir $PIP_DEPS
RUN apt-get autoremove --purge -qq && \
    echo 'Build image ready'

WORKDIR /home/