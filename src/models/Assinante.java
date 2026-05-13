package models;

public class Assinante {
    private String celular;
    private PlanoAssinatura plano;

    public Assinante(String celular) {
        this.celular = celular;
    }

    public void selecionarPlano(PlanoAssinatura plano) {
        this.plano = plano;
    }
    
    public String getCelular() { return celular; }
    public PlanoAssinatura getPlano() { return plano; }
}