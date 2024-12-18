# Étape 1 : Construire le projet avec Maven en utilisant l'image Maven
FROM maven:3.9.9-eclipse-temurin-17 AS builder
WORKDIR /app
# Copier uniquement les fichiers nécessaires pour accélérer le processus de build
COPY pom.xml .
COPY src ./src
# Construire le projet avec Maven
RUN mvn clean package -DskipTests

# Étape 2 : Utiliser l'image JDK pour exécuter l'application
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Copier le fichier JAR généré depuis l'étape de construction
COPY --from=builder /app/target/PigeonSkyRaceSpringSecurity-0.0.1-SNAPSHOT.jar app.jar
# Exposer le port 8080 (ou celui utilisé par Spring Boot)
EXPOSE 8080
# Démarrer l'application Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
