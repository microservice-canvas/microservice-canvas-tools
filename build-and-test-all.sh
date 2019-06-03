#! /bin/bash -e

 ./gradlew build composeBuild

mkdir -p build

ADOC_FILE=build/example.adoc

rm -f ${ADOC_FILE}

docker run --rm -v `pwd`:/doc \
    microservicesio/microservice-canvas-cli:0.1.1-SNAPSHOT \
    --input /doc/microservice-canvas-cli/src/test/resources/example.yml --output /doc/${ADOC_FILE?}

adocFileSize=$(wc -c < "${ADOC_FILE?}")
if [ $adocFileSize -ge 2500 ]; then
    echo size is over 2500 bytes at $adocFileSize
else
    echo actual size is $adocFileSize
    exit 99
fi


