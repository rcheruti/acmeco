@echo off

set "arquivo=./application.pid"
set /p spring_pid=< %arquivo%

echo.Terminando o processo Spring de PID: %spring_pid%
taskkill /PID %spring_pid%

rem Apagar o arquivo com o PID do Spring
rem del "%arquivo%"
