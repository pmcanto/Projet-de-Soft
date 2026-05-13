# 🛒 Sistema de Assinatura - Serviço de Feira (Entrega N2)

Este repositório contém a implementação em Java do fluxo principal do Caso de Uso **"Assinar Serviço de Feira"**, correspondente à entrega N2 da disciplina de Projeto de Software.

O código foi desenvolvido traduzindo o modelo dinâmico (Diagrama de Sequência UML) e o modelo estrutural (Diagrama de Classes) em uma aplicação prática utilizando o padrão **BCE (Boundary-Control-Entity)**.

## 👥 Integrantes
* Nicolas Aguiar (10425450)
* Pedro Canto (10426546)

## 🛠️ Tecnologias e Estrutura
* **Linguagem:** Java (Sem uso de frameworks externos)
* **Persistência:** Manipulação nativa de arquivos (`.txt`)
* **Padrão de Arquitetura:** BCE (Fronteira, Controle e Entidade)
  * `InterfaceApp.java` atua como a Fronteira (View).
  * `AssinaturaController.java` orquestra as regras de negócio.
  * `Produto.java`, `Assinante.java`, `CestaSemanal.java`, etc., representam as Entidades de Domínio.

## 🚀 Como Compilar e Executar (Via Terminal)

Como as classes não utilizam pacotes nomeados (estão no *default package*), o processo de compilação e execução é bastante simples. 

### Pré-requisitos
Certifique-se de ter o [Java JDK](https://www.oracle.com/br/java/technologies/downloads/) instalado em sua máquina. Você pode verificar executando `java -version` no seu terminal.

### Passo 1: Navegue até a pasta do código
Abra o terminal (Prompt de Comando, PowerShell ou Terminal do Linux/Mac) e vá até a pasta onde os arquivos `.java` estão salvos:
```bash
cd src

### Passo 2: Compile os arquivos
```bash
javac *.java

### Passo 3: Executar a aplicação
java InterfaceApp