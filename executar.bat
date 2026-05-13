@echo off
setlocal enabledelayedexpansion
cls
echo ========================================
echo      EXECUTOR FINAL N2 - SWING
echo ========================================

:: 1. Tenta o comando padrao
where javac >nul 2>&1
if %errorlevel% equ 0 (
    set "COMPILADOR=javac"
    goto COMPILAR
)

:: 2. Tenta o caminho especifico que voce encontrou
echo [!] Javac nao esta no Path. Tentando caminho manual...
set "CAMINHO_MANUAL=C:\Program Files\Java\jdk-26.0.1\bin"

if exist "%CAMINHO_MANUAL%\javac.exe" (
    echo [OK] JDK encontrado em: %CAMINHO_MANUAL%
    set "PATH=%PATH%;%CAMINHO_MANUAL%"
    goto COMPILAR
)

:: 3. Busca exaustiva (Plano C)
set "BUSCA=C:\Program Files\Java;C:\Program Files\Microsoft;C:\Program Files\Eclipse Foundation"
for %%P in (%BUSCA%) do (
    if exist "%%P" (
        for /f "delims=" %%I in ('dir /s /b "%%P\javac.exe" 2^>nul') do (
            set "PASTA_BIN=%%~dpI"
            set "PATH=!PATH!;!PASTA_BIN!"
            goto COMPILAR
        )
    )
)

echo [ERRO] Nao consegui localizar o compilador Java.
pause
exit /b

:COMPILAR
echo [*] Ambiente configurado. Compilando o projeto...
cd src
javac views/InterfaceApp.java

if %errorlevel% neq 0 (
    echo [ERRO] Falha na compilacao. Verifique o codigo.
    pause
    exit /b
)

echo [*] Iniciando Interface Grafica (Swing)...
java views.InterfaceApp
pause