# 🌟 Lumine Empório -- Documentação do Projeto

Bem-vindo ao repositório oficial do **Lumine Empório**!\
Este projeto consiste em um **e-commerce completo**, com **frontend em
HTML/CSS/JS** e **backend em Spring Boot**, além de integração com banco
de dados MySQL.

Abaixo estão todas as formas de testar o projeto, seja como **usuário
final** ou **desenvolvedor**.

------------------------------------------------------------------------

## ✧ 1. Testar o Projeto na Nuvem (Modo Usuário)

A versão oficial do site já está publicada e funcional.\
Acesse:

➜ **https://lumineemporio.store**

Nenhuma configuração é necessária.

------------------------------------------------------------------------

## ✧ 2. Testar Localmente (Modo Desenvolvedor)

Siga os passos abaixo para rodar o projeto completo em sua máquina.

------------------------------------------------------------------------

## ✧ 2.1 Instalar Dependências Necessárias

### 🔧 Backend

-   Baixar **JDK mais recente**\
    ➜ https://www.oracle.com/br/java/technologies/downloads/

-   Baixar **Visual C++ Redistribuível v14**\
    ➜ https://learn.microsoft.com/pt-br/cpp/windows/latest-supported-vc-redist?view=msvc-170#latest-supported-redistributable-version

-   Baixar **MySQL Server (última versão)**\
    ➜ https://dev.mysql.com/downloads/mysql/8.0.html

-   Baixar **MySQL Workbench (última versão)**\
    ➜ https://dev.mysql.com/downloads/workbench/

------------------------------------------------------------------------

## ✧ 2.2 VS Code + Extensões Necessárias

Baixe o VS Code:\
➜ https://code.visualstudio.com/

Instale as seguintes extensões:

-   Live Server\
-   Debugger for Java\
-   Gradle for Java\
-   Language Support for Java by Red Hat\
-   Spring Boot Dashboard\
-   Spring Boot Tools\
-   Spring Initializr Java Support\
-   Thunder Client

------------------------------------------------------------------------

## ✧ 2.3 Baixar o Repositório

Baixe o repositório como .zip:\
➜ https://github.com/GAlves7/LumineEmporio/tree/main

Abra **duas janelas/abas** do VS Code: - Uma para o **frontend** - Outra
para o **backend**

------------------------------------------------------------------------

## ✧ 2.4 Configurar o Banco de Dados

1.  Abra o **MySQL Workbench**

2.  Crie o schema:

        dble

3.  Deixe sempre o banco conectado e ligado enquanto estiver rodando o projeto.

------------------------------------------------------------------------

## ✧ 2.5 Configurar o Backend

No VS Code, acesse o arquivo:

    backend/lumine-emporio/src/main/resources/application.properties

Edite os seguintes parâmetros:

### 🔹 URL do banco

    spring.datasource.url=jdbc:mysql://localhost:3306/dble?useSSL=false

Se o nome do schema for diferente, substitua `dble`.

### 🔹 Senha do banco

    spring.datasource.password=SUA_SENHA_AQUI

Use a senha da sua conexão MySQL.

------------------------------------------------------------------------

## ✧ 2.6 Iniciar os Servidores

### 🔧 Backend

Use a extensão **Spring Boot Dashboard** no VS Code para iniciar o servidor
backend.

### 💻 Frontend

Na pasta do Frontend, clique com o botão direito do mouse em `index.html` e clique em **Open with Live Server**.

------------------------------------------------------------------------

## ✧ 2.7 Popular o Banco com Dados de Teste

Abra a extensão **Thunder Client** no VS Code e crie 3 requisições:

### 1️⃣ Adicionar produtos do catálogo

    POST http://localhost:8080/teste/adicionar-podutos

### 2️⃣ Adicionar imagens do catálogo

    POST http://localhost:8080/teste/imagem-adicionar

### 3️⃣ Adicionar variações dos produtos

    POST http://localhost:8080/teste/produtoVar-adicionar

Após enviar todas, o banco estará populado.

------------------------------------------------------------------------

# 🎉 Projeto Funcionando!

Agora o projeto estará totalmente ativo na sua máquina: 
- Frontend pelo Live Server\
- Backend pelo Spring Boot\
- Banco MySQL conectado e ligado
