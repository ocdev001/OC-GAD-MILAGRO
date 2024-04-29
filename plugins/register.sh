#!/bin/bash
OKM_VERSION="7.1.34"

wget https://download.openkm.com/okm/portable/jar/openkm-${OKM_VERSION}-client.jar -O openkm-client.jar
wget https://download.openkm.com/okm/portable/jar/openkm-${OKM_VERSION}-jar-with-dependencies.jar -O openkm-jar-with-dependencies.jar

mvn install:install-file -DgroupId=com.openkm -DartifactId=openkm -Dversion=${OKM_VERSION} -Dclassifier=client -Dpackaging=jar -Dfile=openkm-client.jar
mvn install:install-file -DgroupId=com.openkm -DartifactId=openkm -Dversion=${OKM_VERSION} -Dclassifier=jar-with-dependencies -Dpackaging=jar -Dfile=openkm-jar-with-dependencies.jar




#rm -f openkm-client.jar openkm-jar-with-dependencies.jar
