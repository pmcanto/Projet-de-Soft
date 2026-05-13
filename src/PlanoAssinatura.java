public class PlanoAssinatura {
    private String nomePlano;
    private double valor;
    private int limite;

    public PlanoAssinatura(String nomePlano, double valor, int limite) {
        this.nomePlano = nomePlano;
        this.valor = valor;
        this.limite = limite;
    }

    public boolean validarLimite(int quantidadeAtual) {
        return quantidadeAtual < limite;
    }
    
    public String getNomePlano() { return nomePlano; }
    public double getValor() { return valor; }
}