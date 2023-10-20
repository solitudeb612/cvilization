FROM anapsix/alpine-java:8_server-jre_unlimited

MAINTAINER huizhuoli@foxmail.com

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /my-civ-eva

WORKDIR /my-civ-eva

EXPOSE 8022

ADD ./my-civ-eva/target/my_civ_eva.jar ./

CMD java -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar my_civ_eva.jar
