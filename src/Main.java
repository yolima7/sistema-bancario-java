import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
    void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Banco banco = new Banco();
        ArrayList<ContaBancaria> contas = Banco.carregarContas();
        ContaBancaria conta = null;

        // === ENTRADA NO SISTEMA ===
        System.out.println("=== BEM VINDO AO BANCO SGZ ===");
        System.out.println("[1] Entrar com número da conta (ID)");
        System.out.println("[2] Criar uma nova conta");
        System.out.print("Escolha uma opção: ");

        int opcaoInicial = 0;
        try {
            opcaoInicial = sc.nextInt();
            sc.nextLine(); // Consome o \n
        } catch (InputMismatchException e) {
            sc.nextLine();
        }

        if (opcaoInicial == 1) {
            System.out.print("Digite o número da sua conta (ID): ");
            int idConta = sc.nextInt();
            sc.nextLine();
            conta = banco.buscarConta(idConta);

            if (conta == null) {
                System.out.println("Conta não encontrada! Encerrando...");
                return;
            }
            System.out.println("Bem-vindo(a) de volta, " + conta.getTitular() + "!");

        } else if (opcaoInicial == 2) {
            System.out.print("Digite o nome do titular: ");
            String titular = sc.nextLine();
            conta = banco.cadastrarConta(titular);
            System.out.println("Conta criada com sucesso! O número da sua conta é: " + conta.getNumeroConta());
        } else {
            System.out.println("Opção inválida! Encerrando...");
            return;
        }
        int opcao;
        int numeroConta;
        int numeroDestino;

        do {
            try {
                System.out.println("-----------------------------");
                System.out.println("=== BANCO SGZ - MENU ===");
                System.out.println("------------------------------");
                System.out.println("DIGITE [1] PARA FAZER DEPOSITO");
                System.out.println("DIGITE [2] PARA FAZER SAQUE");
                System.out.println("DIGITE [3] PARA FAZER TRANSFERÊNCIA");
                System.out.println("DIGITE [4] PARA VER O EXTRATO BANCÁRIO");
                System.out.println("DIGITE [5] SE DESEJA CADASTRAR UMA NOVA CONTA");
                System.out.println("DIGITE [6] PARA EXCLUIR SUA CONTA");
                System.out.println("DIGITE [7] PARA ENCERRAR O PROGRAMA");
                System.out.print("Opção: ");
                opcao = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Inválido, digite apenas o número sem letras.");
                sc.nextLine();
                opcao = 0;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Digite o valor de deposito: R$");
                    try {
                        double valorDeposito = sc.nextDouble();
                        sc.nextLine();
                        conta.depositar(valorDeposito);
                        System.out.println("Saldo atual: R$" + conta.getSaldo());
                    } catch (InputMismatchException e) {
                        System.out.println("Valor inválido, digite apenas números!");
                        sc.nextLine();
                    }
                    break;

                case 2:
                    System.out.print("Digite o valor de saque: R$");
                    try {
                        double valorSaque = sc.nextDouble();
                        sc.nextLine();
                        conta.sacar(valorSaque);
                        System.out.println("Saldo atual: R$" + conta.getSaldo());
                    } catch (InputMismatchException e) {
                        System.out.println("Valor inválido, digite apenas números!");
                        sc.nextLine();
                    }
                    break;

                case 3:
                    System.out.print("Digite o número da conta destinatária: ");
                    try {
                        numeroDestino = sc.nextInt();
                        sc.nextLine();
                        ContaBancaria contaDestino = banco.buscarConta(numeroDestino);

                        if (contaDestino == null) {
                            System.out.println("Conta não encontrada!");
                        } else {
                            System.out.print("Valor a transferir: R$");
                            double valorTransferir = sc.nextDouble();
                            sc.nextLine();
                            conta.transferir(contaDestino, valorTransferir);
                            System.out.println("Seu saldo atual: R$" + conta.getSaldo());
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Informe um valor numérico, sem letras!");
                        sc.nextLine();
                    }
                    break;

                case 4:
                    System.out.println("==== EXTRATO BANCÁRIO ====");
                    conta.exibirExtrato();
                    break;

                case 5:
                    System.out.println("==== CADASTRO DE OUTRA CONTA ====");
                    System.out.print("Digite o nome do novo titular: ");
                    String novoTitular = sc.nextLine();
                    ContaBancaria novaConta = banco.cadastrarConta(novoTitular);
                    System.out.println("O número da nova conta é: " + novaConta.getNumeroConta());
                    break;

                case 6:
                    System.out.print("Digite o numero da conta que deseja excluir: ");
                    try {
                        numeroConta = sc.nextInt();
                        sc.nextLine();
                        ContaBancaria contaRemovida = banco.removerConta(numeroConta);
                        if (contaRemovida == null) {
                            System.out.println("Conta não encontrada!");
                        } else {
                            System.out.println("Conta excluída com sucesso!");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("ERRO! Digite apenas números.");
                        sc.nextLine();
                    }
                    break;

                case 7:
                    System.out.println("Saindo do BANCO SGZ...");
                    System.out.println("Programa finalizado!");
                    break;

                default:
                    if (opcao != 0) System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcao != 7);
    }
