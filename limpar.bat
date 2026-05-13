@echo off
cls
echo [*] Removendo binarios (.class)...
del /s /q *.class >nul 2>&1
echo [*] Removendo log de dados (assinaturas.txt)...
if exist assinaturas.txt del /q assinaturas.txt
if exist src\assinaturas.txt del /q src\assinaturas.txt
echo [OK] Ambiente resetado.
pause