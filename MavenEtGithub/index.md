# Maven et GitHub: "Forker" un dépôt GitHub et l'hébergement de ses artefacts Maven

Nous allons utilisez GitHub pour héberger nos dépôts Maven pour C2. Un référentiel Maven est, en son cœur, juste un ensemble structuré de fichiers et de répertoires accessibles au public via http, et GitHub vous permet de le faire facilement avec son support de téléchargement brut. La même technique est utilisée par GitHub lui-même pour servir les sites Web de GitHub Pages.

## La solution de base comprend trois étapes:

* Créez une branche appelée mvn-repo pour héberger vos artefacts Maven.
* Utilisez le site Github site-maven-plugin pour envoyer vos artefacts vers Github.
* Configurez Maven pour utiliser le référentiel mvn distant en tant que référentiel Maven.

### Cette approche présente plusieurs avantages:

* Il s'intègre naturellement avec la cible de déploiement de sorte qu'il n'y ait aucune nouvelle commande Maven à apprendre. Utilisez simplement mvn deploy comme vous le feriez normalement.
* Les artefacts Maven sont conservés séparément de votre source dans une branche appelée mvn-repo, un peu comme les pages github sont conservées dans une branche distincte appelée gh-pages (si vous utilisez des pages github).
* Il n’y a aucune surcharge d’héberger un serveur distinct de stockage Maven Nexus ou Cloud, et vos artefacts Maven étant conservés à proximité de votre dépôt github, il est facile pour les utilisateurs de trouver l’un s’ils savent où se trouve l’autre.

La manière habituelle de déployer des artefacts sur un dépôt Maven distant consiste à utiliser mvn deploy, nous allons donc adapter ce mécanisme pour la faire fonctionner avec notre dépôt github.

# Comment faire?

## Etape1: Indiquez à maven de déployer des artefacts dans un emplacement temporaire dans votre répertoire cible. Ajoutez ceci à votre pom.xml: 

```xml
<distributionManagement>
    <repository>
        <id>internal.repo</id>
        <name>Dépôt temporaire de stockage intermédiaire (Staging)</name>
        <url>file://${project.build.directory}/mvn-repo</url>
    </repository>
</distributionManagement>
 
<plugins>
    <plugin>
        <artifactId>maven-deploy-plugin</artifactId>
        <version>2.8.1</version>
        <configuration>
            <altDeploymentRepository>internal.repo::default::file://${project.build.directory}/mvn-repo</altDeploymentRepository>
        </configuration>
    </plugin>
</plugins>
```

Maintenant, essayez d’exécuter `mvn clean deploy`. Vous verrez qu'il a déployé votre référentiel Maven pour cibler / mvn-repo. La prochaine étape consiste à le faire télécharger ce répertoire sur github.

## Etape2: Ajoutez vos informations d’authentification à `~/.m2/settings.xml` afin que github site-maven-plugin puisse envoyer à github:

```xml
<!-- NOTE: MAKE SURE THAT settings.xml IS NOT WORLD READABLE! -->
<settings>
  <servers>
    <server>
      <id>github</id>
      <username>YOUR-USERNAME</username>
      <password>YOUR-PASSWORD</password>
    </server>
  </servers>
</settings>
```

2. N'oublier pas d'utiliser vos talent de bon administrateur système pour protégé ce fichier contre les regards indiscret. par exemple `chmod 700 ~/.m2/settings.xml`
3. Indiquez ensuite à github site-maven-plugin le nouveau serveur que vous venez de configurer en ajoutant ce qui suit à votre pom:

```xml
<properties>
    <!-- github server corresponds to entry in ~/.m2/settings.xml -->
    <github.global.server>github</github.global.server>
</properties>
```

## Etape3 : configurez le site-maven-plugin pour qu'il soit envoyé depuis votre référentiel intermédiaire temporaire (staging) vers votre branche mvn-repo sur github:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>com.github.github</groupId>
            <artifactId>site-maven-plugin</artifactId>
            <version>0.9</version>
            <configuration>
                <message>Maven artifacts for ${project.version}</message>  <!-- git commit message -->
                <noJekyll>true</noJekyll>                                  <!-- disable webpage processing -->
                <outputDirectory>${project.build.directory}/mvn-repo</outputDirectory> <!-- matches distribution management repository url above -->
                <branch>refs/heads/mvn-repo</branch>                       <!-- remote branch name peut être master si vous souhaitez --> 
                <includes><include>**/*</include></includes>
                <merge>true</merge>                                        <!-- don't delete old artifacts -->
                <repositoryName>YOUR-REPOSITORY-NAME</repositoryName>      <!-- github repo name -->
                <repositoryOwner>YOUR-GITHUB-USERNAME</repositoryOwner>    <!-- github username  -->
            </configuration>
            <executions>
              <!-- run site-maven-plugin's 'site' target as part of the build's normal 'deploy' phase -->
              <execution>
                <goals>
                  <goal>site</goal>
                </goals>
                <phase>deploy</phase>
              </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

Maintenant, exécutez à nouveau `mvn clean deploy`. Vous devriez voir maven-deploy-plugin «télécharger» les fichiers dans votre référentiel de transfert local dans le répertoire cible, puis site-maven-plugin valider ces fichiers et les envoyer au serveur.

## Voila!

On peut maintenant déployer nos artefacts maven sur le dépôt public sans frais en exécutant simplement  `mvn clean deploy`

# La question comment utiliser ce dépôts (qui n'est pas maven central) dans d'autre projet

1. Vous créer vos dépendances comme d'habitude dans maven
2. Dire au pom au trouver les repository

```xml
<repositories>
    <repository>
        <id>YOUR-PROJECT-NAME-mvn-repo</id>
        <url>https://github.com/userOuOrganisation/Nomrepo/tree/branch/path</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```
