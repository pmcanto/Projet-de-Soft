========================================================================

SISTEMA DE ASSINATURA - SERVIÇO DE FEIRA (ENTREGA N2)

Este repositório contém a implementação funcional do Caso de Uso "Assinar 
Serviço de Feira", desenvolvida para a disciplina de Projeto de Software.
O sistema utiliza a arquitetura BCE (Boundary-Control-Entity) e uma 
Interface Gráfica de Usuário (GUI) construída com Java Swing.

------------------------------------------------------------------------
1. INTEGRANTES
------------------------------------------------------------------------
* Nicolas Aguiar - 10425450
* Pedro Canto - 10426546

------------------------------------------------------------------------
2. TECNOLOGIAS E ARQUITETURA
------------------------------------------------------------------------
* Linguagem: Java 21
* Interface: Java Swing (Janelas nativas)
* Persistência: Arquivo de texto ("assinaturas.txt")
* Padrão: BCE (Fronteira, Controle e Entidade)
    - views/: Interface e diálogos com o usuário.
    - controllers/: Coordenação das regras de negócio.
    - models/: Entidades (Produto, Assinante, Plano, Cesta).
    - repositories/: Camada de gravação de dados.

O projeto foi construído separando as responsabilidades em 4 camadas:
Views (Telas), Controllers (Regras lógicas), Models (Dados) e 
Repositories (Banco de Dados/Arquivos).

--- CAMADA DE FRONTEIRA (VIEWS / BOUNDARY) ---

1. InterfaceApp.java
É a Interface Gráfica do Usuário (GUI). Utiliza a biblioteca Swing 
para criar as telas visuais simulando um aplicativo de celular. É onde 
o usuário clica, digita e vê os alertas. Não contém regras de negócio, 
apenas repassa os dados para o Controller.

--- CAMADA DE CONTROLE (CONTROLLERS / CONTROL) ---

2. AssinaturaController.java
É o "cérebro" ou o maestro do sistema. Ele faz a ponte entre a View e 
os Models. Ele recebe a ação do usuário (ex: "clicou em pagar"), 
chama as classes de dados para processar a compra, e depois aciona o 
Repository para salvar o arquivo.

--- CAMADA DE ENTIDADES (MODELS / ENTITY) ---

3. Assinante.java
Representa o cliente logado. Guarda o número do celular do usuário e 
qual plano ele escolheu para a assinatura.

4. PlanoAssinatura.java
Define as regras do plano contratado. Guarda o nome do plano (ex: Premium), 
o valor em dinheiro e qual é o limite de itens que a pessoa pode pegar.

5. Produto.java
É a representação simples de um item da feira. Guarda apenas o nome 
(ex: "Maçã") e o preço unitário do item.

6. CestaSemanal.java
Funciona como o "carrinho de compras". Guarda a lista de produtos que 
o assinante escolheu e tem a responsabilidade de verificar se a adição 
de um novo item não ultrapassou o limite permitido pelo plano.

7. Pagamento.java
Responsável pela lógica financeira. Simula a aprovação do cartão de 
crédito e gera um código de protocolo único (UUID) para a compra.

--- CAMADA DE DADOS (REPOSITORIES) ---

8. PersistenciaTXT.java
Funciona como o banco de dados do sistema. Pega os dados do cliente, 
os produtos escolhidos e o total gasto, formata tudo como um "cupom 
fiscal" e grava no arquivo de texto (assinaturas.txt) para não 
perder os dados quando o programa for fechado.

------------------------------------------------------------------------
3. COMO EXECUTAR VIA SCRIPTS (RECOMENDADO)
------------------------------------------------------------------------
Os scripts verificam o ambiente e iniciam o programa automaticamente.

No WINDOWS:
    Basta dar um duplo clique no arquivo: executar.bat
    (Ou execute ".\executar.bat" via terminal na raiz do projeto)

No LINUX / CODESPACES:
    1. Dê permissão de execução: chmod +x executar.sh
    2. Rode o script: ./executar.sh

------------------------------------------------------------------------
4. COMO EXECUTAR MANUALMENTE (TERMINAL)
------------------------------------------------------------------------

1. Abra o terminal na pasta raiz do projeto.

2. Navegue até a pasta fonte:
   cd src

3. Compile o projeto a partir da classe principal:
   javac views/InterfaceApp.java

4. Execute a aplicação:
   java views.InterfaceApp

------------------------------------------------------------------------
5. LIMPEZA E MANUTENÇÃO (RESET)
------------------------------------------------------------------------
Para remover os arquivos compilados (.class) e resetar o arquivo de 
dados (assinaturas.txt) antes de uma nova demonstração:

Via Script:
    Windows: rodar limpar.bat
    Linux:   rodar ./limpar.sh

Via Terminal (Manual):
    Windows: del /s /q *.class
    Linux:   find . -name "*.class" -type f -delete


Link para o Vídeo na Wiki: <LINK>
========================================================================

