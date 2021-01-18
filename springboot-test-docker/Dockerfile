FROM development1/jre:1.8.0_192
MAINTAINER R&D Depart.1 <gy-yykf1b@19pay.com.cn>

RUN mkdir /log
RUN mkdir /app

ADD ./target/springboottestdocker.jar /app/

COPY ./run.sh /app/run.sh
RUN chmod u+x /app/run.sh

WORKDIR /app

CMD [ "/app/run.sh" ]

EXPOSE 8080

