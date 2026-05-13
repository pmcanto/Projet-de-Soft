#!/bin/bash

echo "[*] Removendo binários (.class)..."
find . -name "*.class" -type f -delete
echo "[*] Removendo log de dados (assinaturas.txt)..."
rm -f assinaturas.txt src/assinaturas.txt
echo "[OK] Ambiente resetado."