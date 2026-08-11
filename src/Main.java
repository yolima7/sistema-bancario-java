import java.util.InputMismatchException;
import java.util.Scanner;
void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Digite o nome do titular: ");
    String titular = sc.next();
    Banco banco = new Banco();
    ContaBancaria conta = banco.cadastrarConta(titular);
    int opcao;
    int numeroConta;
    int numeroDestino;
    do {
        try {
            System.out.println("-----------------------------");
            System.out.println("===BEM VINDO AO BANCO SGZ===");
            System.out.println("ESCOLHA UMA DAS OPÇÕES ABAIXO: ");
            System.out.println("------------------------------");
            System.out.println("DIGITE [1] PARA FAZER DEPOSITO");
            System.out.println("DIGITE [2] PARA FAZER SAQUE");
            System.out.println("DIGITE [3] PARA FAZER TRANSFERÊNCIA");
            System.out.println("DIGITE [4] PARA VER O EXTRATO BANCÁRIO");
            System.out.println("DIGITE [5] SE DESEJA CADASTRAR UMA NOVA CONTA");
            System.out.println("DIGITE [6] PARA EXCLUIR SUA CONTA");
            System.out.println("DIGITE [7] PARA ENCERRAR O PROGRAMA");
            opcao = sc.nextInt();
        }catch (InputMismatchException e){
            System.out.println("Inválido, digite apenas o número sem letras.");
            sc.next();
            opcao = 0;
        }
        switch(opcao){
            case 1:
                System.out.println("Digite o valor de deposito: R$");
                try{
                double valorDeposito = sc.nextDouble();
                conta.depositar(valorDeposito);
                System.out.println("Saldo atual: R$" + conta.getSaldo());
                }catch(InputMismatchException e ){
                    System.out.println("Valor inválido, digite apenas números!");
                    sc.nextLine();
                }
                break;
            case 2:
                System.out.println("Digite o valor de saque: R$");
                try{
                double valorSaque = sc.nextDouble();
                conta.sacar(valorSaque);
                System.out.println("Saldo atual: R$" + conta.getSaldo());}
                catch(InputMismatchException e ){
                    System.out.println("Valor inválido, digite apenas números!");
                    sc.nextLine();
                }
                break;
            case 3:
                System.out.println("Digite o número da conta destinatária: ");
                try {
                    numeroDestino = sc.nextInt();
                    ContaBancaria contaDestino = banco.buscarConta(numeroDestino);
                    if (contaDestino == null) {
                        System.out.println("Conta não encontrada! Acesse a opção TRANSFERÊNCIA e tente novamente.");
                        break;
                    } else {
                        System.out.println("Valor a transferir: R$");
                        double valorTransferir = sc.nextDouble();
                        conta.transferir(contaDestino, valorTransferir);
                        System.out.println("Seu saldo atual: R$" + conta.getSaldo());
                        break;
                    }
                }catch(InputMismatchException e ){
                    System.out.println("Informe um valor númerico, sem letras!");
                }
                break;

            case 4:
                System.out.println("====EXTRATO BANCÁRIO====");
                conta.exibirExtrato();
                break;

            case 5:
                System.out.println("====CADASTRO DE CONTA====");
                System.out.println("Digite o nome do titular: ");
                titular = sc.next();
                ContaBancaria novaConta = banco.cadastrarConta(titular);
                System.out.println("O número da sua conta é: "+novaConta.getNumeroConta());
                break;

            case 6:
                System.out.println("Digite o numero da conta que deseja excluir: ");
                try {
                    numeroConta = sc.nextInt();
                    ContaBancaria contaRemovida = banco.removerConta(numeroConta);
                    if (contaRemovida == null) {
                        System.out.println("Conta não encontrada!");
                        break;
                    }else{
                        System.out.println("Conta excluida com sucesso!");
                    }
                }catch(InputMismatchException e ){
                    System.out.println("ERRO! Digite apenas números.");
                    sc.nextLine();
                }
                break;
            case 7:
                System.out.println("Saindo do BANCO SGZ...");
                System.out.println("Programa finalizado!");
                break;
            default:
                System.out.println("Opção invalida, tente novamente.");
        }

    } while(opcao != 7);
}
