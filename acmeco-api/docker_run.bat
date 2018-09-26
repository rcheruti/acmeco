@echo off

rem Ao iniciar um container do docker é necessário passar as configurações do ambiente.
rem O comando abaixo deve ser guardado!! (Backup!)
rem O docker fornece o comando que inicio um container, então não temos como criar um segundo container
rem igual sem termos os valores das variáveis de ambiente
docker run  -p 8080:8080 ^
            -e "config.s3_region=sa-east-1" ^
            -e "config.s3_access_key_id=AKIAJOR2VKAOQ4VFMYNA" ^
            -e "config.s3_secret_key_id=m8YD8dehUitwA7aqGm98u/rtcWlDXM/grcai8MMT" ^
            -e "config.s3_bucket_padrao=rcc-desafio-kernel" ^
            rcc-acmeco-api:1.0.0

