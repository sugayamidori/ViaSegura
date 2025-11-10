#!/bin/bash

echo "========================================="
echo "CARREGADOR DE DADOS - H3 ACIDENTES"
echo "========================================="

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Verifica se Docker está rodando
if ! docker info > /dev/null 2>&1; then
    echo -e "${RED}✗ Docker não está rodando${NC}"
    exit 1
fi

# Verifica se o container data-loader existe
if ! docker ps -a --format '{{.Names}}' | grep -q '^data-loader$'; then
    echo -e "${YELLOW}Container data-loader não existe. Execute 'docker-compose up -d' primeiro${NC}"
    exit 1
fi

# Aguarda banco estar pronto
echo -e "${YELLOW}Aguardando banco de dados...${NC}"
sleep 2

# Instala dependências Python no container (apenas primeira vez)
echo -e "${YELLOW}Instalando dependências Python...${NC}"
docker exec data-loader pip install -q -r requirements.txt

# Executa o script de carga
echo -e "${GREEN}Iniciando carga de dados...${NC}"
docker exec -it data-loader python load_data.py

# Captura código de saída
EXIT_CODE=$?

if [ $EXIT_CODE -eq 0 ]; then
    echo -e "\n${GREEN}✓ Carga concluída com sucesso!${NC}"
else
    echo -e "\n${RED}✗ Erro na carga de dados (código: $EXIT_CODE)${NC}"
fi

exit $EXIT_CODE