import java.util.UUID;

public class Pagamento {
    private double valorTotal;
    private String status;

    public String processarPagamento(String dadosCartao) {
        // Simulação de aprovação
        this.status = "Aprovado";
        return gerarProtocolo();
    }

    public String gerarProtocolo() {
        return "PROT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    public String getStatus() { return status; }
}