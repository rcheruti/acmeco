
FROM openjdk:8-alpine

COPY ./target/acmeco-api.jar .

EXPOSE 8080

# Para detectar limites de memória e CPU em OS Linux
ENV java_config=" -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap "

CMD java $java_config -jar acmeco-api.jar
