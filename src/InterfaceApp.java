import java.util.Scanner;

public class InterfaceApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AssinaturaController controller = new AssinaturaController();

        System.out.println("--- BEM VINDO AO SERVIÇO DE FEIRA ---");

        System.out.print("1. Digite seu celular: ");
        String celular = scanner.nextLine();
        System.out.print("2. Digite o Token SMS (Dica: 123456): ");
        String token = scanner.nextLine();

        if (!controller.autenticarCelular(celular, token)) {
            System.out.println("Autenticação falhou. Encerrando.");
            return;
        }

        PlanoAssinatura planoEssencial = new PlanoAssinatura("Plano Essencial", 39.90, 5);
        System.out.println("\n3. Plano Selecionado Automaticamente: Essencial (Máx 5 itens)");
        controller.registrarPlano(planoEssencial);

        Produto maca = new Produto("Maçã", 3.00);
        Produto banana = new Produto("Banana", 2.50);
        
        System.out.println("\n4. Adicionando itens na cesta...");
        System.out.println(controller.adicionarNaCesta(maca));
        System.out.println(controller.adicionarNaCesta(banana));
        System.out.println(controller.adicionarNaCesta(maca));

        System.out.print("\n5. Insira os dados do cartão de crédito: ");
        String cartao = scanner.nextLine();

        String protocolo = controller.finalizarAssinatura(cartao);
        System.out.println("\n✅ PAGAMENTO APROVADO!");
        System.out.println("Protocolo gerado: " + protocolo);
        System.out.println("Detalhes salvos em 'assinaturas.txt'.");
        
        scanner.close();
    }
}