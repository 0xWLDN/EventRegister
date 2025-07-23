# Use OpenJDK 24 as base image
FROM openjdk:24-jdk-slim


# Set the working directory inside the container (optional but neat)
WORKDIR /app


# Copy the repackaged JAR file from target folder into the image
COPY target/EventRegister.jar app.jar


# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]