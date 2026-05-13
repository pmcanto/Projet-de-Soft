#!/bin/bash

clear
echo "========================================"
echo "   AUTO-INSTALLER E EXECUÇÃO (LINUX)"
echo "========================================"

# 1. Função para instalar o Java se necessário
install_java() {
    echo "[!] Java não detectado. Tentando instalar..."
    if command -v apt &> /dev/null; then
        sudo apt update && sudo apt install -y openjdk-21-jdk
    elif command -v pacman &> /dev/null; then
        sudo pacman -Syu jdk-openjdk
    else
        echo "[ERRO] Gerenciador de pacotes não suportado."
        exit 1
    fi
}

# 2. Check de dependências
if ! command -v java &> /dev/null; then
    install_java
fi

# 3. Compilação e Execução
echo "[*] Compilando projeto..."
cd src || exit
javac views/InterfaceApp.java

if [ $? -eq 0 ]; then
    echo "[*] Iniciando Interface Gráfica (Swing)..."
    java views.InterfaceApp
else
    echo "[ERRO] Falha na compilação."
fi