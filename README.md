# rbac-app
Application web implémentant une gestion d’accès RBAC (Role Base Access Contrôl).
# Exigences
* Un IDE Java (préférablement IntelliJ) car c’est ce qui a été utilisé pour ce projet
* Docker pour la base de données
# Installation
* Pour IntelliJ voici le lien pour télécharger :

  * https://www.jetbrains.com/idea/download/#section=windows (Windows)
  * https://www.jetbrains.com/idea/download/#section=mac (macOS)
  * https://www.jetbrains.com/idea/download/#section=linux (Linux)

* Pour Docker, voici le lien pour télécharger (NOTE: Télécharger la version stable):

  * https://store.docker.com/editions/community/docker-ce-desktop-windows (Windows)
  * https://store.docker.com/editions/community/docker-ce-desktop-mac (macOS)

# Démarrer le projet
1. Télécharger ou cloner le répertoire
    * git clone https://github.com/JadMaa/rbac-app.git
1. Ouvrir l’IDE
1. Pour IntelliJ:
   1. Open file
   1. Chercher le fichier pom.xml et l’ouvrir
   1. Cliquer sur « open as a project »
1. Ouvrir un terminal
1. Effectuer les commandes ci-dessous pour démarrer la base de donnée Mongo (NOTE : Ne pas fermer ce terminal)
    1. docker run –d –p 27017 :27017 mongo
    1. docker exec –it <idContainer> bash
    1. mongo
    1. use GTI619
1. Démarrer le main de l’application dans le fichier RbacApplication.java
1. Sur le navigateur, se connecter à http://localhost:8080/
1. Initialiser la base de données des utilisateurs et des clients en entrant dans le navigateur :
    1. http://localhost:8080/init/users
    1. http://localhost:8080/init/clients
1. Retourner sur http://localhost:8080/ et l’application est prête
1. Pour consulter la BD, aller sur le terminal où docker est en marche et exécuter :
    1. db.users.find().pretty()
    1. db.clients.find().pretty()
1. La journalisation des connections et des historiques sont dans le fichier authLogs.txt
# Construit avec
* IntelliJ : https://www.jetbrains.com/idea/
* SpringBoot : https://projects.spring.io/spring-boot/
* Docker : https://www.docker.com/
* MongoDB : https://www.mongodb.com/
