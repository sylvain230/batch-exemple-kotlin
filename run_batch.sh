#!/bin/bash

java -Dspring.profiles.active=local -jar build/libs/batch-exemple-kotlin-0.0.1-SNAPSHOT.jar

STATUSCODE=${?}
echo ${STATUSCODE}
exit ${STATUSCODE}
echo "Fin du traitement"
