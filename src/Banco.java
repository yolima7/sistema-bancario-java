import java.util.ArrayList;
public class Banco {
  private ArrayList<ContaBancaria> contas;
    public Banco(){
        contas = new ArrayList<>();
    }
    private int contador = 1;
    public ContaBancaria cadastrarConta(String titular){
        ContaBancaria novaConta = new ContaBancaria(titular, this.contador, 0);
        contas.add(novaConta);
        this.contador++;
        return novaConta;

    }
    public ContaBancaria buscarConta(int numeroConta){
        for(ContaBancaria conta : contas){
            if(conta.getNumeroConta() == numeroConta){
                return conta;
            }
        }
        return null;
    }

    public ContaBancaria removerConta(int numeroConta){
        ContaBancaria contaEncontrada = this.buscarConta(numeroConta);
        if(contaEncontrada == null){
            return null;
        }else{
            contas.remove(contaEncontrada);
        }return contaEncontrada;
    }

}
