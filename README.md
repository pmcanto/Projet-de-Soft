========================================================================
    #SISTEMA DE ASSINATURA - SERVIÇO DE FEIRA (ENTREGA N2)

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
