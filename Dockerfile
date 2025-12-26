# Utiliser une image Java 17 légère
FROM eclipse-temurin:17-jdk-alpine

# Copier le JAR construit
COPY target/gestion-dechets-1.0.0.jar app.jar

# Exposer le port 8080
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java","-jar","/app.jar"]
