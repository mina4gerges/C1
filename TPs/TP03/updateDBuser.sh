#!/bin/sh

if test $# -le 0
then
  echo nombre de parametres invalide
  echo "Usage : sh $0 dbUser [ dbPassword ]"
  echo "     ex 1 : sh $0 petstoreDB_User  petstoreDB_Password "
  echo "     ex 2 : sh $0 petstoreDB  "
  echo "     ex 3 : sh $0 root ''  "
  exit 2; 
fi

# dbUser=petstoreDB
dbUser=$1
dbPassword=7002n*
if test $# -eq 2
then
    dbPassword=$2
fi

ANT_BUILDS=`find . -name   build.xml`
if [ -n "$ANT_BUILDS" ]
then
    sed -i.bak -e "s/\"db.user\" value=\".*\"/\"db.user\" value=\"$dbUser\"/;s/\"db.password\" value=\".*\"/\"db.password\" value=\"$dbPassword\"/"  $ANT_BUILDS
fi

GRADLE_BUILDS=`find . -name   build.gradle`
if [ -n "$GRADLE_BUILDS" ]
then
    sed -i.bak -e "s/dbUser = '.*'/dbUser = '$dbUser'/;s/dbPassword = '.*'/dbPassword = '$dbPassword'/;"  $GRADLE_BUILDS
fi
GRADLE_BUILDS=`find . -name  gradle.properties `
if [ -n "$GRADLE_BUILDS" ]
then
    sed -i.bak -e "s/dbUser=.*/dbUser=$dbUser/;s/dbPassword=.*/dbPassword=$dbPassword/;"  $GRADLE_BUILDS
fi

DataAccessConstants=`find . -name DataAccessConstants.java | fgrep src`
if [ -n "$DataAccessConstants" ]
then
    sed -i.bak -e "s/USER_DB = \".*\"/USER_DB = \"$dbUser\"/;s/PASSWD_DB = \".*\"/PASSWD_DB = \"$dbPassword\"/" $DataAccessConstants
fi
DataSourceConfig=`find . -name DataSourceConfig.java | fgrep src`
if [ -n "$DataSourceConfig" ]
then
    sed -i.bak -e "s/username(\".*\"/username(\"$dbUser\"/;s/password(\".*\"/password(\"$dbPassword\"/" $DataSourceConfig
fi
DataSourceProps=`find . -name application.properties | fgrep src`
if [ -n "$DataSourceProps" ]
then
    sed -i.bak -e "s/datasource.username=.*/datasource.username=$dbUser/;s/datasource.password=.*/datasource.password=$dbPassword/" $DataSourceProps
fi

context_xml=`find . -name context.xml`
if [ -n "$context_xml" ]
then
    sed -i.bak -e  "s/username=\".*\" password=\".*\"/username=\"$dbUser\" password=\"$dbPassword\"/" $context_xml
fi

persistence_xml=`find . -name persistence.xml`
if [ -n "$persistence_xml" ]
then
    sed -i.bak -e "s/persistence.jdbc.user\" value=\".*\"/persistence.jdbc.user\" value=\"$dbUser\"/;s/persistence.jdbc.password\" value=\".*\"/persistence.jdbc.password\" value=\"$dbPassword\"/" $persistence_xml
fi

glassfish_resources_xml=`find . -name glassfish-resources.xml`
if [ -n "$glassfish_resources_xml" ]
then
    sed -i.bak -e "s/name=\"User\" value=\".*\"/name=\"User\" value=\"$dbUser\"/;s/name=\"Password\" value=\".*\"/name=\"Password\" value=\"$dbPassword\"/" $glassfish_resources_xml
fi
