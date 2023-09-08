ARG PARENT_VERSION=latest
FROM ubuntu:20.04
LABEL maintainer="daviand"


ENV APT_DEPS git \
			python3 \
			python-is-python3 \
			pip \
			nginx \
			openssl iproute2 
			#traceroute iputils-ping


#ENV BM_RUNTIME_DEPS net-tools \
#			sudo \
#			iproute2
RUN apt-get update -qq && \
	apt-get upgrade -qq
RUN ln -fs /usr/share/zoneinfo/Europe/Rome /etc/localtime
RUN DEBIAN_FRONTEND=noninteractive apt-get install -qq --no-install-recommends $APT_DEPS
RUN mkdir /etc/nginx/ssl
RUN mkdir /etc/nginx/ssl/private
RUN mkdir /etc/nginx/ssl/certs
COPY Server/default /etc/nginx/sites-enabled/
COPY Server/nginx.conf /etc/nginx/
RUN openssl req -x509 -nodes -days 365 -newkey rsa:2048 -keyout /etc/ssl/private/serverone_key.key -out /etc/ssl/certs/serverone_cert.crt -subj "/C=IT/ST=Denial/L=Milan/O=Polimi/CN=polimi.it"
#COPY Server/serverone_cert.crt /etc/ssl/certs/
#COPY Server/serverone_key.key /etc/ssl/private/
#RUN  apt-get -y install tzdata
#RUN ip route add 172.19.0.3 via 172.19.0.2 dev eth0 src 172.19.0.4

RUN apt-get autoremove --purge -qq && \
    echo 'Build image ready'

