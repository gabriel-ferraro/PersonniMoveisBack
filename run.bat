@ECHO off

ECHO Fazendo rebuild da aplicacao e subindo docker.
mvnw clean package -DskipTests && cd Docker && docker-compose up --build --force-recreate

REM Verifica se ocorreu algum erro.
IF %ERRORLEVEL% NEQ 0 (
    COLOR 4
    ECHO Erro durante a execucao do docker-compose - SEU DOCKER FOI INICIALIZADO?
    EXIT /B
)