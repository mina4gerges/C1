JAVA=$JAVA_HOME/bin/java
DEPLOY_DIR=../build/libs

CLASSPATH=$DEPLOY_DIR/clientCatalog-1.0.jar

# $JAVA -cp "$CLASSPATH" com.yaps.petstore.ui.MenuCatalog
$JAVA -jar $DEPLOY_DIR/clientCatalog-1.0.jar
