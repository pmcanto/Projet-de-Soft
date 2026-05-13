clear

echo "========================================"
echo "  BUILD AUTOMATICO - SERVICO DE FEIRA"
echo "========================================"

cd src

echo "[1/2] Compilando classes (Padrao BCE)..."
javac views/InterfaceApp.java

if [ $? -ne 0 ]; then
    echo ""
    echo "[ERRO] Falha na compilação. Verifique o código."
    exit 1
fi

echo "[2/2] Iniciando a aplicação..."
echo "----------------------------------------"
java views.InterfaceApp

echo ""
echo "----------------------------------------"
echo "Processo finalizado."

clear