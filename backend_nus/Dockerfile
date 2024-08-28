# Use an official Maven runtime as a parent image

FROM eclipse-temurin:17-jdk-jammy AS build
# Install Maven
RUN apt-get update && apt-get install -y maven
# Set the working directory to /app
WORKDIR /app
# Copy the Maven POM and project source files
COPY pom.xml .
COPY src ./src
# Build the project and package it into a JAR file
# RUN mvn clean package
RUN mvn -f pom.xml clean test package
# Create a new stage for the deployment image
# FROM openjdk:17-jre-slim
FROM openjdk:17-oracle
# Set the working directory to /app
WORKDIR /app
# Copy the JAR file from the build stage to the deployment stage
COPY --from=build /app/target/nucleus_iss-1.0-SNAPSHOT.jar app.jar
# Expose the port on which your Java application will run
EXPOSE 90
# Define the command to run your Java application
CMD ["java","-Dserver.port=90" , "-jar", "app.jar"]
