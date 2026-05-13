package models;

import java.util.ArrayList;
import java.util.List;

public class CestaSemanal {
    private List<Produto> itens = new ArrayList<>();
    private Assinante assinante;

    public CestaSemanal(Assinante assinante) {
        this.assinante = assinante;
    }

    public boolean adicionarProduto(Produto p) {
        if (assinante.getPlano().validarLimite(itens.size())) {
            itens.add(p);
            return true;
        }
        return false;
    }
    
    public List<Produto> getItens() { return itens; }
}