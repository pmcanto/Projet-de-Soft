public class AssinaturaController {
    private Assinante clienteLogado;
    private CestaSemanal cestaAtual;
    private Pagamento processadorPagamento;

    public AssinaturaController() {
        this.processadorPagamento = new Pagamento();
    }

    public boolean autenticarCelular(String celular, String tokenRecebido) {
        if (tokenRecebido.equals("123456")) {
            this.clienteLogado = new Assinante(celular);
            return true;
        }
        return false;
    }

    public void registrarPlano(PlanoAssinatura plano) {
        this.clienteLogado.selecionarPlano(plano);
        this.cestaAtual = new CestaSemanal(clienteLogado);
    }

    public String adicionarNaCesta(Produto produto) {
        boolean sucesso = cestaAtual.adicionarProduto(produto);
        if (sucesso) {
            return produto.getNome() + " adicionado com sucesso!";
        } else {
            return "Erro: Limite do plano (" + clienteLogado.getPlano().getNomePlano() + ") atingido.";
        }
    }

    public String finalizarAssinatura(String dadosCartao) {
        String protocolo = processadorPagamento.processarPagamento(dadosCartao);
        PersistenciaTXT.salvarAssinatura(clienteLogado, cestaAtual, protocolo);
        return protocolo;
    }
}