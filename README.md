# 🏦 Banco SGZ — Sistema Bancário em Java

Sistema bancário via console, desenvolvido em Java aplicando conceitos de Programação Orientada a Objetos (POO) e persistência de dados relacional. O projeto simula as operações essenciais de um banco: cadastro de contas, depósitos, saques, transferências entre contas reais, extrato bancário e exclusão de contas — um CRUD completo, integrado ao PostgreSQL.

## 📋 Sobre o projeto

Este projeto foi construído de forma incremental, começando com uma única classe simples (`ContaBancaria`) e evoluindo até uma arquitetura em camadas com padrão DAO (Data Access Object) e integração com banco de dados.

Sou estudante de Análise e Desenvolvimento de Sistemas (2º semestre) e este é um projeto pessoal de estudo, desenvolvido também com apoio de IA como mentoria (orientação sobre design e conceitos, não geração de código pronto) — a lógica, a escrita e as decisões de implementação são minhas.

## ⚙️ Funcionalidades

- **Cadastro de contas**: cada conta recebe automaticamente um número único (ID) gerado pela sequência do banco de dados.
- **Login e busca por ID**: o acesso às operações e a busca de contas são realizados via ID numérico único.
- **Depósito e Saque**: operações com validação de valores inválidos e saldo insuficiente, atualizando o saldo diretamente no PostgreSQL.
- **Transferência entre contas**: busca a conta de destino pelo ID e registra a movimentação de forma atômica para ambas as contas.
- **Extrato bancário persistente**: histórico completo de transações gravado no banco de dados, com data e hora. Em transferências, o extrato adapta a exibição conforme a perspectiva da conta consultada ("enviada para" ou "recebida de").
- **Exclusão de conta**: remove a conta e suas transações vinculadas no banco de dados.

## 🧠 Principais decisões de design

- **Persistência Relacional (PostgreSQL + JDBC)**: os dados deixaram de existir apenas na memória e agora são persistidos de forma definitiva no banco de dados.
- **Padrão DAO (Data Access Object)**: a classe `ContaDAO` isola toda a manipulação do banco de dados (comandos SQL, conexões e `ResultSet`), separando a regra de negócio da camada de dados.
- **Classe `ConexaoBanco`**: centraliza a criação e o gerenciamento de conexões com o PostgreSQL via driver JDBC.
- **Separação em `Transacao` como classe própria**: cada movimentação é mapeada como um objeto imutável no Java e persistida na tabela `transacao`.
- **Classe `Banco` e `Main` desacopladas**: a `Main` cuida apenas da interação via console (`Scanner`), enquanto `Banco` e `ContaDAO` gerenciam as regras de negócio e operações de dados.

## 🏗️ Estrutura de arquivos

```text
├── src/
│   ├── Main.java          → Menu interativo via Scanner (interface do usuário)
│   ├── Banco.java         → Gerencia regras de negócio e intermediação com o DAO
│   ├── ContaBancaria.java → Representa a entidade Conta (saldo, titular, operações)
│   ├── Transacao.java     → Representa a entidade Transação
│   ├── TipoTransacao.java → Enum com os tipos de transação (DEPOSITO, SAQUE, etc.)
│   ├── ContaDAO.java      → Camada de acesso ao banco (operações CRUD em SQL)
│   ├── ConexaoBanco.java  → Gerenciador de conexão com o PostgreSQL (JDBC)
│   └── schema.sql         → Script DDL para criação da estrutura de tabelas
🛠️ Tecnologias e Ferramentas
Java
PostgreSQL
Driver JDBC (PostgreSQL Driver)
IntelliJ IDEA

🗄️ Configuração do Banco de Dados
Para rodar o projeto no seu computador, é necessário ter o PostgreSQL instalado e configurado.

1. Criar o Banco de Dados
No seu cliente SQL (como DBeaver ou pgAdmin), crie um banco de dados chamado sistema_bancario:

SQL
CREATE DATABASE sistema_bancario;
2. Executar o Script DDL (schema.sql)
Abra e execute o arquivo src/schema.sql (disponível no projeto) dentro da sua base de dados para criar as tabelas conta e transacao:

SQL
DROP TABLE IF EXISTS transacao;
DROP TABLE IF EXISTS conta;

CREATE TABLE conta (
    id SERIAL PRIMARY KEY,
    titular VARCHAR(100) NOT NULL,
    saldo NUMERIC(10, 2) DEFAULT 0.00
);

CREATE TABLE transacao (
    id SERIAL PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    data TIMESTAMP NOT NULL,
    remetente VARCHAR(100),
    destinatario VARCHAR(100),
    conta_id INT REFERENCES conta(id) ON DELETE CASCADE
);
3. Ajustar as credenciais no Java
Abra o arquivo src/ConexaoBanco.java e atualize com as credenciais do seu PostgreSQL local (usuário e senha):

Java
private static final String URL = "jdbc:postgresql://localhost:5432/sistema_bancario";
private static final String USUARIO = "seu_usuario"; // ex: postgres
private static final String SENHA = "sua_senha";
▶️ Como executar
Clone o repositório:

Bash
git clone https://github.com/yolima7/sistema-bancario-java.git
Abra o projeto na sua IDE (ex: IntelliJ IDEA).

Adicione o driver JDBC do PostgreSQL (postgresql-42.x.x.jar) às dependências/bibliotecas do projeto.

Execute o passo a passo de Configuração do Banco de Dados acima.

Execute a classe Main.java.

🚧 Próximos passos
Tratamento de exceções personalizadas para erros do Scanner e conexões com o banco.

Migração para Spring Boot e Spring Data JPA / Hibernate (mapeamento relacional automático).

Projeto em constante evolução como parte da minha jornada de aprendizado em programação.
