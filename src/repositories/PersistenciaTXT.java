package repositories;

import models.Assinante;
import models.CestaSemanal;
import models.Produto;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
            
            Map<String, Integer> quantidades = new TreeMap<>();
            Map<String, Double> precos = new HashMap<>();
            double totalProdutos = 0.0;
            
            for (Produto p : cesta.getItens()) {
                quantidades.put(p.getNome(), quantidades.getOrDefault(p.getNome(), 0) + 1);
                precos.put(p.getNome(), p.getPreco());
            }
            
            for (String nome : quantidades.keySet()) {
                int qtd = quantidades.get(nome);
                double precoUn = precos.get(nome);
                double subtotal = qtd * precoUn;
                totalProdutos += subtotal;
                pw.printf("- %s: %dx R$ %.2f = R$ %.2f\n", nome, qtd, precoUn, subtotal);
            }
            
            pw.println("-----------------------");
            pw.printf("SOMA DOS ITENS: R$ %.2f\n", totalProdutos);
            pw.printf("VALOR DO PLANO: R$ %.2f\n", assinante.getPlano().getValor());
            pw.println("=======================\n");
            
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }
}