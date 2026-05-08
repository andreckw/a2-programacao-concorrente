#!/bin/bash

export PATH=$PATH:/usr/lib/jvm/java-21-openjdk-amd64/bin

echo "=== Compilando projeto ==="
mkdir -p out
javac -d out $(find . -name "*.java")
if [ $? -ne 0 ]; then
    echo "ERRO na compilacao!"
    exit 1
fi
echo "Compilado com sucesso!"
echo ""
echo "=== Como executar (abra 5 terminais) ==="
echo ""
echo "Terminal 1 - Fabrica:"
echo "   java -cp out fabrica.ServidorFabrica"
echo ""
echo "Terminal 2 - Loja 1:"
echo "   java -cp out loja.ServidorLoja 1 localhost 9001"
echo ""
echo "Terminal 3 - Loja 2:"
echo "   java -cp out loja.ServidorLoja 2 localhost 9002"
echo ""
echo "Terminal 4 - Loja 3:"
echo "   java -cp out loja.ServidorLoja 3 localhost 9003"
echo ""
echo "Terminal 5 - Clientes:"
echo "   java -cp out cliente.MainClientes localhost localhost localhost"
