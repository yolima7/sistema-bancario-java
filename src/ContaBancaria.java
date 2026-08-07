import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
public class ContaBancaria {
    private String titular;
    private int numeroConta;
    private double saldo;
    private ArrayList<Transacao> extrato;

    public ContaBancaria(String titular, int numeroConta, double saldo){
        this.titular = titular;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
        this.extrato = new ArrayList<>();
    }
    public String getTitular(){
        return titular;
    }
    public int getNumeroConta(){
        return numeroConta;
    }
    public double getSaldo(){
        return saldo;
    }
    public void depositar(double valor){
        if(valor <= 0){
            System.out.println("Valor invalido, você deve depositar acima de R$0.00");
        }else{
            this.saldo += valor;
            System.out.println("Deposito efetuado na conta de: "+this.titular+"\n valor depositado: R$"+valor);
            Transacao t = new Transacao(TipoTransacao.DEPOSITO, valor, null, null);
            this.extrato.add(t);
        }

    }
    public void sacar(double valor){
        if(valor <= 0){
            System.out.println("Adicione um valor maior que R$0.00 para sacar!");
        } else if (this.saldo < valor) {
            System.out.println("Saldo insuficiente!");
        }else{
            this.saldo -= valor;
            System.out.println("Saque efetuado na conta de "+this.titular+ "\n no valor de R$"+valor);
            Transacao t = new Transacao(TipoTransacao.SAQUE, valor, null, null);
            this.extrato.add(t);
        }
    }
    public void transferir(ContaBancaria destino, double valor){
        if(valor <= 0){
            System.out.println("Não é possivel transferir esse valor! Digite um valor válido");
        } else if (this.saldo < valor) {
            System.out.println("Saldo insuficiente! Informe um valor menor ou faça um deposito antes.");
        }else{
                this.saldo -= valor;
                destino.saldo += valor;
            System.out.println("Transferencia de R$"+valor+" realizada para " + destino.getTitular() + " com sucesso!");
            Transacao t = new Transacao(TipoTransacao.TRANSFERENCIA, valor, this.titular, destino.titular);
            extrato.add(t);
            destino.extrato.add(t);
        }
    }
    public void exibirExtrato(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for(Transacao t: extrato){
            if(t.getTipo() == TipoTransacao.DEPOSITO){
                System.out.println("Deposito feito em: "+t.getData().format(formato)+ "\n Valor: R$"+t.getValor());
            } else if (t.getTipo() == TipoTransacao.SAQUE) {
                System.out.println("Saque feito em: "+t.getData().format(formato)+ "\n Valor: R$"+t.getValor());
            }else{
                if(this.titular.equals(t.getRemetente())){
                    System.out.println("Transferência realizada para: "+t.getDestinatario()+ "\n Valor: R$" +t.getValor()+ "\n Data: "+t.getData().format(formato));
                }else{
                    System.out.println("Transferência recebida de: "+t.getRemetente()+ "\n Valor: R$"+t.getValor()+ "\n Data: "+t.getData().format(formato));
                }

            }
        }
    }

}
