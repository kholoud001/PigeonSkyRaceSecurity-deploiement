# Documentation CI/CD

## **1. Introduction**
Ce document explique comment configurer et utiliser le pipeline CI/CD pour ce projet Maven. Il inclut des instructions sur Jenkins, Docker et Maven.

---

## **2. Pré-requis**
### Outils nécessaires :
- **Maven** (pour compiler le projet)
- **Docker** (pour gérer les containers)
- **Jenkins** (pour automatiser le pipeline)
- **Java 17** (version utilisée par le projet)

### Commandes Maven courantes :
- **Nettoyer le projet** :
  ```bash
  mvn clean
  ```
- **Compiler le projet** :
  ```bash
  mvn compile
  ```
- **Lancer les tests** :
  ```bash
  mvn test
  ```
- **Construire un package** :
  ```bash
  mvn package
  ```

---

## **3. Configuration Jenkins**
### **Installation des plugins nécessaires** :
- Git
- Maven Integration
- Docker Pipeline

### **Création d'un Pipeline**
1. **Créer un nouveau pipeline dans Jenkins** :
    - Cliquez sur `New Item`.
    - Choisissez `Pipeline`.
    - Configurez la source Git du projet.

2. **Configurer le script de pipeline** :  
   Ajoutez ce script pour Maven dans Jenkins :
   ```groovy
   pipeline {
       agent any
       tools {
           maven 'Maven 3.9.9' 
           jdk 'JDK 17'     
       }
       stages {
           stage('Checkout') {
               steps {
                   git branch: 'main', url: 'https://github.com/kholoud001/PigeonSkyRaceSecurity-deploiement.git'
               }
           }
           stage('Build') {
               steps {
                   sh 'mvn clean package'
               }
           }
           stage('Test') {
               steps {
                   sh 'mvn test'
               }
           }
           stage('Docker Build') {
               steps {
                   sh 'docker-compose build'
               }
           }
           stage('Deploy') {
               steps {
                   sh 'docker-compose up -d'
               }
           }
       }
   }
   ```

---

## **4. Workflow CI/CD**
1. **Étape 1 : Tests automatiques**  
   Maven exécute les tests avec `mvn test`.

2. **Étape 2 : Compilation et Packaging**  
   Maven compile le code source avec `mvn clean package`.

3. **Étape 3 : Création des images Docker**  
   Docker crée les images à partir des fichiers `Dockerfile` et `docker-compose.yml`.

4. **Étape 4 : Déploiement**  
   Les containers sont déployés avec `docker-compose up -d`.

---

## **5. Dépannage**
### Problème 1 : Les tests échouent dans Jenkins
- **Cause possible** : Configuration Java ou dépendances manquantes.
- **Solution** : Vérifiez que Maven utilise Java 17 dans Jenkins.

### Problème 2 : Docker ne fonctionne pas
- **Cause possible** : Permissions manquantes pour Jenkins.
- **Solution** : Ajoutez Jenkins au groupe Docker :
  ```bash
  sudo usermod -aG docker jenkins
  ```

---

## **6. Améliorations futures**
- Ajout d'un rapport de tests dans Jenkins avec des plugins comme `JUnit`.
- Mise en place d'une étape de déploiement sur un environnement cloud.
