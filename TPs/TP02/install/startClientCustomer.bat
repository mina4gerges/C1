@echo off

set JAVA=%JAVA_HOME%\bin\java
set DEPLOY_DIR=..\build

set CLASSPATH=%DEPLOY_DIR%\clientCustomer-1.0.jar

%JAVA% -cp %CLASSPATH% com.yaps.petstore.ui.MenuCustomer