@echo off

set "params= "
set "params=%params% -Xms24m -Xmx120m -XX:MaxMetaspaceSize=70m "
set "params=%params% -XX:MaxHeapFreeRatio=2 -XX:MinHeapFreeRatio=1 "
set "params=%params% -XX:+UnlockCommercialFeatures "
set "params=%params% -XX:+FlightRecorder "
rem -XX:StartFlightRecording=duration=10s,filename=myrecording.jfr 

rem Parâmetros para configurar a conexão com o AWS S3
set "params=%params% -Dconfig.s3_region=sa-east-1 "
set "params=%params% -Dconfig.s3_access_key_id=AKIAJOR2VKAOQ4VFMYNA "
set "params=%params% -Dconfig.s3_secret_key_id=m8YD8dehUitwA7aqGm98u/rtcWlDXM/grcai8MMT "
set "params=%params% -Dconfig.s3_bucket_padrao=rcc-desafio-kernel "

start java %params% -jar .\target\acmeco-api.jar
