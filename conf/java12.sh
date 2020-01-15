# definir cette variable au répertoire ou vous avez decider d'installer les
# Logiciels pour vos ED TP projets
# puis dans votre .bashrc ajouter cette ligne source <PathDuScript>/java12.sh [<install dir>] 
export C2INSTALL=${1:-$HOME}
echo $C2INSTALL

# Le reste sera basé sur $C2INSTALL

# Java
export JAVA_HOME="$C2INSTALL/jdk-12"
export PATH=$JAVA_HOME/bin:$PATH

# Netbeans
export NETBEANS_HOME="$C2INSTALL/netbeans"

# maven
export MAVEN_HOME="$NETBEANS_HOME/java/maven"
export PATH=$MAVEN_HOME/bin:$PATH