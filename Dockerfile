# Use the official OpenJDK 23 image as the base image, matching the version in your pom.xml
FROM openjdk:23-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and pom.xml to the working directory
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .

# Run Maven to download dependencies and prepare the project (this avoids re-downloading dependencies on subsequent builds)
RUN ./mvnw dependency:go-offline

# Copy the source code into the container
COPY src ./src

# Build the application using Maven and skip tests to speed up the build process (can be changed as needed)
RUN ./mvnw clean package -DskipTests

# Copy the final JAR file from target folder into the container
# It assumes your application JAR will be built and placed in the target directory after running 'mvn package'
COPY target/*.jar app.jar

# Expose the port your Spring Boot application runs on (default is 8080)
EXPOSE 8083

# Command to run the Spring Boot application inside the container
ENTRYPOINT ["java", "-jar", "/app/app.jar"]