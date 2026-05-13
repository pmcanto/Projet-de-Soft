package controllers;

import models.Assinante;
import models.CestaSemanal;
import models.Pagamento;
import models.PlanoAssinatura;
import models.Produto;
import repositories.PersistenciaTXT;

import java.util.List;

public class AssinaturaController {
    private Assinante clienteLogado;
    private CestaSemanal cestaAtual;
    private Pagamento processadorPagamento;

    public AssinaturaController() {
        this.processadorPagamento = new Pagamento();
    }

    // Passo 1 e 2: Autenticação (A validação do token agora ocorre na View)
    public void realizarLogin(String celular) {
        this.clienteLogado = new Assinante(celular);
    }

    // Passo 3: Escolher Plano
    public void registrarPlano(PlanoAssinatura plano) {
        this.clienteLogado.selecionarPlano(plano);
        this.cestaAtual = new CestaSemanal(clienteLogado);
    }

    // Passos 4 a 6: Seleção de Itens (Delega validação de limite para a Entidade)
    public String adicionarNaCesta(Produto produto) {
        boolean sucesso = cestaAtual.adicionarProduto(produto);
        if (sucesso) {
            return produto.getNome() + " adicionado com sucesso!";
        } else {
            return "Erro: Limite do plano atingido.";
        }
    }

    // Método auxiliar para a Tela 7 (Revisar Cesta) da View recuperar os dados
    public List<Produto> getItensCesta() {
        if (cestaAtual != null) {
            return cestaAtual.getItens();
        }
        return null;
    }

    // Passo 8: Pagamento e Confirmação
    public String finalizarAssinatura(String dadosCartao) {
        // Executa o processamento do pagamento mapeado no diagrama [cite: 429]
        String protocolo = processadorPagamento.processarPagamento(dadosCartao);
        
        // Dispara a persistência dos dados
        PersistenciaTXT.salvarAssinatura(clienteLogado, cestaAtual, protocolo);
        
        return protocolo;
    }
}