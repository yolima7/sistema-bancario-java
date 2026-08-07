# 🏦 Banco SGZ — Sistema Bancário em Java

Sistema bancário via console, desenvolvido em Java aplicando conceitos de Programação Orientada a Objetos. O projeto simula as operações essenciais de um banco: cadastro de contas, depósitos, saques, transferências entre contas reais, extrato bancário e exclusão de contas — um CRUD completo, focado inteiramente em back-end/lógica de negócio.

## 📋 Sobre o projeto

Este projeto foi construído de forma incremental, começando com uma única classe simples (`ContaBancaria`, com depósito/saque/transferência) e evoluindo até uma arquitetura com múltiplas classes, cada uma com responsabilidade bem definida — um exercício prático de POO, não só de sintaxe.

Sou estudante de Análise e Desenvolvimento de Sistemas (2º semestre) e este é um projeto pessoal de estudo, desenvolvido também com apoio de IA como mentoria (orientação sobre design e conceitos, não geração de código pronto) — a lógica, a escrita e as decisões de implementação são minhas.

## ⚙️ Funcionalidades

- **Cadastro de contas**: cada conta recebe automaticamente um número único, gerado pelo sistema (o usuário nunca escolhe seu próprio número de conta)
- **Depósito**: com validação de valores inválidos (menores ou iguais a zero)
- **Saque**: com validação de saldo insuficiente
- **Transferência entre contas**: busca a conta de destino pelo número (não pelo nome, evitando ambiguidade entre titulares com nomes iguais)
- **Extrato bancário**: histórico completo de transações, com data e hora formatadas. Em transferências, o extrato se adapta à perspectiva de quem está consultando (mostra "enviada para" ou "recebida de", conforme o caso)
- **Exclusão de conta**: remove uma conta do sistema a partir do número

## 🧠 Principais decisões de design

- **Separação em `Transacao` como classe própria**: em vez de armazenar apenas texto solto, cada transação é um objeto com tipo, valor, data e participantes — permitindo reaproveitar o mesmo objeto no extrato de ambas as contas envolvidas em uma transferência (por referência, sem duplicação de dados)
- **Uso de `enum` para o tipo de transação**: evita erros de digitação e garante, em tempo de compilação, que só valores válidos (`DEPOSITO`, `SAQUE`, `TRANSFERENCIA`) sejam usados
- **Classe `Banco` como gerenciadora central**: aplica o princípio de responsabilidade única — `ContaBancaria` cuida apenas do que é próprio de uma conta; `Banco` cuida de cadastrar, buscar e remover contas dentro do sistema (relação de composição, não herança)
- **Geração automática de número de conta**: o banco controla um contador interno, eliminando qualquer risco de números duplicados
- **Separação entre lógica de negócio e interface**: toda a interação via `Scanner`/menu fica isolada na `Main`; as classes de domínio (`ContaBancaria`, `Banco`, `Transacao`) não sabem nada sobre como o usuário interage com o sistema

## 🏗️ Estrutura de classes

```
├── Main.java              → Menu interativo via Scanner (interface do usuário)
├── Banco.java              → Gerencia a coleção de contas (cadastro, busca, remoção)
├── ContaBancaria.java       → Representa uma conta (saldo, extrato, operações)
├── Transacao.java           → Representa uma transação individual (histórico imutável)
└── TipoTransacao.java       → Enum com os tipos possíveis de transação
```

## ▶️ Como executar

1. Clone o repositório
2. Abra o projeto na sua IDE de preferência (desenvolvido com IntelliJ IDEA)
3. Execute a classe `Main`
4. Siga as instruções exibidas no menu do console

## 🚧 Limitações conhecidas / próximos passos

- Os dados existem apenas durante a execução do programa (não há persistência em arquivo ou banco de dados ainda)
- Não há tratamento de exceção para entradas inválidas no `Scanner` (ex: digitar letras em campos numéricos)
- Próxima evolução planejada: persistência de dados (arquivo ou banco de dados)

## 🛠️ Tecnologias

- Java

---

Projeto em constante evolução como parte da minha jornada de aprendizado em programação.
