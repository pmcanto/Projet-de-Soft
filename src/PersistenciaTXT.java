import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
            for (Produto p : cesta.getItens()) {
                pw.println("- " + p.getNome());
            }
            pw.println("=======================\n");
            
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }
}