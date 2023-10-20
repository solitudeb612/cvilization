FROM mysql:8.0.16

MAINTAINER swust_jsj(huizhuoli@foxmail.com)

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

COPY ./db/schema.sql /docker-entrypoint-initdb.d