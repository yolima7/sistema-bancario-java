import java.time.LocalDateTime;

public class Transacao {
    private TipoTransacao tipo;
    private double valor;
    private LocalDateTime data;
    private String remetente;
    private String destinatario;

    public Transacao(TipoTransacao tipo, double valor,LocalDateTime data, String remetente, String destinatario) {
    this.tipo = tipo;
    this.valor = valor;
    this.data = LocalDateTime.now();
    this.remetente = remetente;
    this.destinatario = destinatario;
    this.data = data;
    }

    public TipoTransacao getTipo(){
        return this.tipo;
    }
    public double getValor(){
        return this.valor;
    }

    public LocalDateTime getData() {
        return this.data;
    }
    public String getRemetente(){
        return this.remetente;
    }
    public String getDestinatario(){
        return this.destinatario;
    }
}
