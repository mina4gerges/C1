# un script d'init et les installations

```bash
# definir cette variable au répertoire ou vous avez decider d'installer les
# Logiciel pour vos ED TP projets
export C2INSTALL=$HOME

# Le reste sera basé sur $C2INSTALL

# Java
export JAVA_HOME="$C2INSTALL/jdk-12"
export PATH=$JAVA_HOME/bin:$PATH

# Netbeans
export NETBEANS_HOME="$C2INSTALL/netbeans"

# maven
export MAVEN_HOME="$NETBEANS_HOME/java/maven"
export PATH=$MAVEN_HOME/bin:$PATH
```

# Verifier l'installation de Java

* java -version
* javac -version

```bash
pfares@cnamBackup:~$ java -version

openjdk version "12" 2019-03-19
OpenJDK Runtime Environment (build 12+33)
OpenJDK 64-Bit Server VM (build 12+33, mixed mode, sharing)

pfares@cnamBackup:~$ javac -version
javac 12
```

Si pas installé l'installer dans votre répertoire racine `$HOME` si linux ou par exemple `c:\sharing` si windows

La dernière en date 
* [openjdk12 tar](https://download.java.net/java/GA/jdk12/GPL/openjdk-12_linux-x64_bin.tar.gz)

```bash
cd
tar xzf openjdk-12_linux-x64_bin.tar.gz
```

pour utiliser cette version ajouter au fichier .bashrc (dans le répertorie racine) les lignes suivantes

```bash
export JAVA_HOME="$HOME/jdk-12"
export PATH=$JAVA_HOME/bin:$PATH
```


# récupérer la dernière version netbeans d'apache (en incubation pour quelques semaines encore)

* [Le version netbeans 11](https://www.apache.org/dyn/closer.cgi/incubator/netbeans/incubating-netbeans/incubating-11.0/incubating-netbeans-11.0-bin.zip)

Unzip dans votre home (`~`) si linux ou par exemple `c:\sharing` si windows

# Récupérer ou référencer mvn (Maven) 

Nous allons utiliser la version maven fournie avec netbeans

Elle se trouve dans le répertoire `~/netbeans/java/maven` si vous avez installer netbeans dans votre répertoire racine ou `<INSTALLDIR>/netbeans/java/maven`

```bash
mpfares@cnamBackup:~$ mvn -version
Apache Maven 3.3.9 (bb52d8502b132ec0a5a3f4c09453c07478323dc5; 2015-11-10T18:41:47+02:00)
Maven home: /home/pfares/netbeans/java/maven
Java version: 12, vendor: Oracle Corporation
Java home: /home/pfares/jdk-12
Default locale: fr_FR, platform encoding: UTF-8
OS name: "linux", version: "4.18.0-17-generic", arch: "amd64", family: "unix"

```

# Créer un dépôt git, github racine pour tous vos ED/TP dans votre organisation github dédié au Cours C2

Nous allons partir de cette [idée issue des TP de l'année précedente](http://opentraining.cofares.net/TP-GLG-Cnam-Liban/PremierTP/) puis étoffer...

# récupère la version dernière version de tomcat 

Au moment de la rédaction de cette page 9.0.16

# Lectures complémentaires

* [Comment déployer vers maven central](https://central.sonatype.org/pages/ossrh-guide.html)
* [Nous pouvons déployer aussi dans une depot maven dans github](/C2/MavenEtGithub/index.md)




