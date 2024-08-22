# Etapa de construcción
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# Copiar el pom.xml y descargar las dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa de ejecución
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copiar el JAR desde la etapa de construcción
COPY --from=build /app/target/demoAmarelo-0.0.1-SNAPSHOT.jar /app/demoAmarelo-0.0.1-SNAPSHOT.jar 

# Exponer el puerto en el que se ejecuta la aplicación Spring Boot
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "/app/demoAmarelo-0.0.1-SNAPSHOT.jar"]