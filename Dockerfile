# Etapa 1: Clonar y construir el proyecto con Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Clona el repositorio de GitHub
WORKDIR /app
RUN git clone https://github.com/XLex0/GR06_1BT3_622_25A.git .

# Compila el proyecto y genera el WAR
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final con Tomcat y Java 21
FROM tomcat:11.0.6-jre21

# Copia el WAR generado desde la etapa anterior
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Exponer el puerto de Tomcat
EXPOSE 8080

# Iniciar Tomcat en primer plano
CMD ["catalina.sh", "run"]
