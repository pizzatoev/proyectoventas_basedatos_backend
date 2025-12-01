FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copiar archivos de Maven primero
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Dar permisos de ejecución al mvnw
RUN chmod +x ./mvnw

# Descargar dependencias (capa cacheable)
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar la aplicación
RUN ./mvnw clean package -DskipTests

# Exponer el puerto (Render usa variable PORT)
EXPOSE 8080

# Ejecutar la aplicación (Render inyecta PORT como variable de entorno)
CMD ["java", "-jar", "target/SalesMaster-0.0.1-SNAPSHOT.jar"]
