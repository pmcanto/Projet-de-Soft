#!/bin/bash

clear
echo "========================================"
echo "  LIMPANDO AMBIENTE - PROJETO FEIRA"
echo "========================================"

echo "[1/2] Removendo arquivos compilados (.class)..."
find . -name "*.class" -type f -delete

echo "[2/2] Removendo assinaturas.txt..."
rm -f src/assinaturas.txt
rm -f assinaturas.txt

echo ""
echo "Ambiente resetado com sucesso!"