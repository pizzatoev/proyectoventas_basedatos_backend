# Usa Java 17 como base
FROM eclipse-temurin:17-jdk

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo pom y descarga dependencias
COPY pom.xml .
RUN mkdir -p src/main
RUN mkdir -p src/test

# Copia el proyecto completo
COPY . .

# Compila el proyecto sin tests
RUN ./mvnw clean package -DskipTests

# Expone el puerto dinámico de Render
ENV PORT=8080
EXPOSE 8080

# Ejecuta el jar generado (Render usa PORT)
CMD ["java", "-jar", "target/SalesMaster-0.0.1-SNAPSHOT.jar"]
