FROM openjdk:17-jdk-slim as build

WORKDIR /app

# --- NUEVAS LÍNEAS PARA INSTALAR MAVEN ---
# Instalar wget o curl para descargar Maven
RUN apt-get update && apt-get install -y wget && rm -rf /var/lib/apt/lists/*
# Descargar Maven
ARG MAVEN_VERSION=3.9.11
ARG BASE_URL=https://dlcdn.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries
RUN wget ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz -P /tmp/
RUN tar xf /tmp/apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /opt/
RUN ln -s /opt/apache-maven-${MAVEN_VERSION} /opt/maven
ENV MAVEN_HOME /opt/maven
ENV PATH=${MAVEN_HOME}/bin:${PATH}
# --- FIN DE LAS NUEVAS LÍNEAS ---


# Copia los archivos de Maven para descargar dependencias y construir
COPY pom.xml .
COPY src ./src

# Build de la aplicación Java con Maven
RUN mvn clean package -DskipTests

# --- STAGE 2: Crear la imagen final ligera ---
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia el JAR compilado desde la etapa de build
COPY --from=build /app/target/gestion-backend.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]