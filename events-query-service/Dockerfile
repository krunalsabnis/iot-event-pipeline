FROM java:8u72-jre

# expose port for APIs

EXPOSE 8080

COPY /build/libs/events-query-service*jar /service.jar
COPY ./wait-for-it.sh /wait-for-it.sh
COPY ./run.sh /run.sh


RUN chmod +x /run.sh /wait-for-it.sh


ENTRYPOINT ["/run.sh"]
