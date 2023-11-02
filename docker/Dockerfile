FROM eclipse-temurin:17-jdk-jammy
EXPOSE 8080
WORKDIR /@name@
COPY ./libs/@name@-@version@.jar .
ENTRYPOINT ["java","-jar","/@name@/@name@-@version@.jar"]