@echo off

rem Compilar recursos Java e Web (irá terminar o processo no fim da criação)
rem mvn clean package 

rem Apagar imagem anterior, se existir
docker rmi rcc-acmeco-api:1.0.0
rem Criar nova imagem do docker
docker build --compress -t rcc-acmeco-api:1.0.0 . 
