package repositories;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import models.Assinante;
import models.CestaSemanal;
import models.Produto;

public class PersistenciaTXT {
    private static final String CAMINHO_ARQUIVO = "assinaturas.txt";

    public static void salvarAssinatura(Assinante assinante, CestaSemanal cesta, String protocolo) {
        try (FileWriter fw = new FileWriter(CAMINHO_ARQUIVO, true);
             PrintWriter pw = new PrintWriter(fw)) {
            
            pw.println("=== NOVA ASSINATURA ===");
            pw.println("Protocolo: " + protocolo);
            pw.println("Celular: " + assinante.getCelular());
            pw.println("Plano: " + assinante.getPlano().getNomePlano());
            pw.println("Itens Selecionados (" + cesta.getItens().size() + "):");
            
            // Lógica para agrupar produtos e calcular subtotais
            Map<String, Integer> quantidades = new TreeMap<>();
            Map<String, Double> precos = new HashMap<>();
            double totalProdutos = 0.0;
            
            for (Produto p : cesta.getItens()) {
                quantidades.put(p.getNome(), quantidades.getOrDefault(p.getNome(), 0) + 1);
                precos.put(p.getNome(), p.getPreco());
            }
            
            // Gravando cada item detalhado
            for (String nome : quantidades.keySet()) {
                int qtd = quantidades.get(nome);
                double precoUn = precos.get(nome);
                double subtotal = qtd * precoUn;
                totalProdutos += subtotal;
                
                // Exemplo: - Maçã: 2x R$ 3.00 = R$ 6.00
                pw.printf("- %s: %dx R$ %.2f = R$ %.2f\n", nome, qtd, precoUn, subtotal);
            }
            
            pw.println("-----------------------");
            pw.printf("VALOR TOTAL DOS PRODUTOS: R$ %.2f\n", totalProdutos);
            pw.printf("VALOR COBRADO DO PLANO: R$ %.2f\n", assinante.getPlano().getValor());
            pw.println("=======================\n");
            
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }
}