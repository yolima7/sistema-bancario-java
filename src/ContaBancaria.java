import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
            atualizarSaldoNoBanco();
            salvarTransacaoNoBanco(TipoTransacao.DEPOSITO, valor, null, null);
            System.out.println("Deposito efetuado na conta de: "+this.titular+"\n valor depositado: R$"+valor);
        }

    }
    public void sacar(double valor){
        if(valor <= 0){
            System.out.println("Adicione um valor maior que R$0.00 para sacar!");
        } else if (this.saldo < valor) {
            System.out.println("Saldo insuficiente!");
        }else{
            this.saldo -= valor;
            atualizarSaldoNoBanco();
            salvarTransacaoNoBanco(TipoTransacao.SAQUE, valor, null, null);
            System.out.println("Saque efetuado na conta de "+this.titular+ "\n no valor de R$"+valor);
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

                this.atualizarSaldoNoBanco();
                destino.atualizarSaldoNoBanco();

                salvarTransacaoNoBanco(TipoTransacao.TRANSFERENCIA, valor, this.titular, destino.titular);
                destino.salvarTransacaoNoBanco(TipoTransacao.TRANSFERENCIA, valor, this.titular, destino.titular);
                System.out.println("Transferencia de R$"+valor+" realizada para " + destino.getTitular() + " com sucesso!");
        }
    }
    public void exibirExtrato(){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        ArrayList<Transacao> extrato = carregarExtratoDoBanco();
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

    private void atualizarSaldoNoBanco(){
        String sql = "UPDATE conta SET saldo = ? where id = ?";
        try(Connection conn = ConexaoBanco.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setDouble(1, this.saldo);
            stmt.setInt(2, this.numeroConta);
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println("Erro ao atualizar saldo: "+e.getMessage());
        }
    }

    private void salvarTransacaoNoBanco(TipoTransacao tipo, double valor, String remetente, String destinatario){
        String sql = "INSERT INTO transacao (tipo, valor, data, remetente, destinatario, conta_id) VALUES (?, ?, ?, ?, ?,?)";
        try(Connection conn = ConexaoBanco.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, tipo.name());
            stmt.setDouble(2, valor);
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(4, remetente);
            stmt.setString(5, destinatario);
            stmt.setInt(6, this.numeroConta);
            stmt.executeUpdate();
        }catch(SQLException e){
            System.err.println("Erro ao registrar transacao: "+e.getMessage());
        }
    }

    private ArrayList<Transacao> carregarExtratoDoBanco(){
        ArrayList<Transacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM transacao WHERE conta_id = ? ORDER BY data ASC";
        try(Connection conn = ConexaoBanco.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)){
             stmt.setInt(1, this.numeroConta);
             ResultSet rs = stmt.executeQuery();
             while(rs.next()){
                 TipoTransacao tipo = TipoTransacao.valueOf(rs.getString("tipo"));
                 double valor = rs.getDouble("valor");
                 String remetente = rs.getString("remetente");
                 String destinatario = rs.getString("destinatario");
                 Timestamp ts = rs.getTimestamp("data");
                 LocalDateTime data = ts != null ? ts.toLocalDateTime() : LocalDateTime.now();
                 lista.add(new Transacao(tipo, valor, data, remetente, destinatario));
             }
        }catch(SQLException e){
            System.err.println("Erro ao carregar extrato: "+e.getMessage());
        }
        return lista;
    }

}
