#!/bin/bash
OKM_VERSION="7.1.34"

# Download OpenKM libraries
wget http://download.openkm.com/okm/portable/jar/openkm-${OKM_VERSION}-client.jar -O openkm-client.jar
wget http://download.openkm.com/okm/portable/jar/openkm-${OKM_VERSION}-jar-with-dependencies.jar -O openkm-jar-with-dependencies.jar

# Register OpenKM libraries
mvn install:install-file -DgroupId=com.openkm -DartifactId=openkm -Dversion=${OKM_VERSION} -Dclassifier=client -Dpackaging=jar -Dfile=openkm-client.jar
mvn install:install-file -DgroupId=com.openkm -DartifactId=openkm -Dversion=${OKM_VERSION} -Dclassifier=jar-with-dependencies -Dpackaging=jar -Dfile=openkm-jar-with-dependencies.jar
