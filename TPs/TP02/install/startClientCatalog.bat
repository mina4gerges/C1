@echo off

set JAVA=%JAVA_HOME%\bin\java
set DEPLOY_DIR=..\build

set CLASSPATH=%DEPLOY_DIR%\clientCatalog-1.0.jar

rem %JAVA% -cp %CLASSPATH% com.yaps.petstore.ui.MenuCatalog
%JAVA% -jar %DEPLOY_DIR%\clientCatalog-1.0.jar
