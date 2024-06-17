FROM amazoncorretto:17-alpine-jdk

MAINTAINER Ale

COPY target/dailylog-0.0.1-SNAPSHOT.jar dailylog.jar

ENTRYPOINT ["java","-jar","/dailylog.jar"]