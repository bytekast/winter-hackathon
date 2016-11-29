FROM java:8

# Install maven
RUN apt-get update
RUN apt-get install curl

WORKDIR /app

# Prepare
ADD ./build/libs/winter-hackathon-0.1.0.jar /app/winter-hackathon-0.1.0.jar

EXPOSE 8080

CMD ["java", "-jar", "winter-hackathon-0.1.0.jar"]