# Acme Co. API

Na pasta `acmeco-api` está o código Java, que fornece a API.
Ele usa **Spring Boot 2 Web**, **LogBack** para os logs, **Lombok** para *códigos comuns*, e bilioteca da **AWS S3** para consumir os serviço da **AWS**.

Na pasta `acmeco-web` está o código Web.
Criado com **Angular 6**, usa **Angular Materials** e **Moment Js** *(não é de fato necessário, apenas caso seja usado algum tipo de Date Picker do Materials, para facilitar a configuração de apresentação de datas)*.


Para compilar o código com o `maven` execute o comando `mvn clean package` dentro da pasta `acmeco-api`.

O `pom.xml` está configurado para compilar o código Web *(na pasta `../acmeco-web`)* com o comando `npm run build`, e incluir o *compilado* do Angular dentro do `JAR` final do **Spring**. 


--------------------------------------------------------------------------
## Docker

Para criar a imagem do `Docker` é necessário executar o comando `mvn clean package` e executar o arquivo `docker_build.bat`, dentro da pasta `acmeco-api`.

Para criar um container a partir dessa imagem pode ser executado o arquivo `docker_run.bat` na pasta `acmeco-api`.

Para criar o container é necessário informar algumas variáveis de ambiente: 

| Variável                  | Descrição |
| ------------------------- | --------- |
| `config.s3_region`        | Nome da região que deve ser usada por padrão ao se conectar nos serviços da AWS. *Ex.: sa-east-1* |
| `config.s3_access_key_id` | Chave de acesso aos serviços da AWS. *'Usuário'* de API.  |
| `config.s3_secret_key_id` | Chave de acesso aos serviços da AWS. *'Senha'* de API.  |
| `config.s3_bucket_padrao` | Nome do Bucket que deve ser usado ao acessar os serviços do AWS S3.  |

*Obs.: a API está preparada apenas para se conectar e executar operações com o Bucket configurado nas variáveis de ambiente!*

```bat 
docker run  -p 8080:8080 ^
            -e "config.s3_region=sa-east-1" ^
            -e "config.s3_access_key_id=AKI...YNA" ^
            -e "config.s3_secret_key_id=m8Y...XM/grcai8MMT" ^
            -e "config.s3_bucket_padrao=rcc-desafio-kernel" ^
            rcc-acmeco-api:1.0.0
```


--------------------------------------------------------------------------
## Desenvolvimento

Para o desenvolvimento foi utilizado o `VS Code`, tendo como principais plugins:
- `Language Support for Java(TM) by Red Hat` da Red Hat
- `Java Test Runner` da Microsoft
- `Debugger for Java` da Microsoft
- `Maven for Java` da Microsoft
- `Lombok Annotations Support for VS Code` do Gabriel Basilio Brito 
*O VS Code já fornece 'nativamente' um bom suporte para HTML, TypeScript e SASS.*
*Já para Java o suporte é fraco. IDEs como Eclipse e IntelliJ são muito superiores, mas o Eclipse é muito pesado para carregar e lento para usar.*

Para executar os códigos durante o desenvolvimento pode ser executado os seguintes passos *(para Windows)*:
- Na pasta `acmeco-api` executar o comando `mvn clean package` para compilar o código Java
- Na pasta `acmeco-api` executar o arquivo `.\execute.bat` para executar o Java com o JAR criado acima *(um novo processo CMD será iniciado)*, ouvindo no endereço `http://127.0.0.1:8080`
- Na pasta `acmeco-web` executar o comando `npm start` (que está configurado para executar o comando `ng serve --proxy-config proxy.conf.json`) para iniciar o servidor do `node` ouvindo no endereço `http://127.0.0.1:4200`, com configurações para redirecionar requisições para o endereço `http://127.0.0.1:8080`



