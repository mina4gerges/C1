JAVA=$JAVA_HOME/bin/java
DEPLOY_DIR=../build/libs

CLASSPATH=$DEPLOY_DIR/clientCustomer-1.0.jar

$JAVA -cp "$CLASSPATH" com.yaps.petstore.ui.MenuCustomer
