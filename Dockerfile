# Use the base image
FROM openjdk:17-oracle

# Create a directory for your application
WORKDIR /app

# Copy the JAR file
COPY target/*.jar cafemanagementsystem.jar

# Copy the resources directory
COPY src/main/resources /app/src/main/resources

# Expose the port
EXPOSE 8090

# Define the entry point
ENTRYPOINT ["java", "-jar", "cafemanagementsystem.jar"]

ENV POSTGRES_DB_NAME=cafemgmtsys_db \
            POSTGRES_USER=POSTGRES \
            POSTGRES_PASSWORD='By@$hna%'
