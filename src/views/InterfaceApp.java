package views;

import controllers.AssinaturaController;
import models.PlanoAssinatura;
import models.Produto;

import java.util.Scanner;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public class InterfaceApp {
    
    private static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        AssinaturaController controller = new AssinaturaController();

        // --- TELA 1: CELULAR ---
        limparTela();
        System.out.println("--- BEM VINDO AO SERVIÇO DE FEIRA ---");
        
        String celular = "";
        while (true) {
            System.out.print("1. Digite seu celular [Tela 1] (ex: (11) 92766-7764): ");
            celular = scanner.nextLine();
            if (celular.replaceAll("[^0-9]", "").length() == 11) break;
            System.out.println("[AVISO] Formato inválido! Tente novamente.\n");
        }

        // --- TELA 2: TOKEN DINÂMICO ---
        while (true) {
            String tokenGerado = String.format("%06d", random.nextInt(1000000));
            System.out.println("\n----------------------------------------");
            System.out.println("SMS ENVIADO PARA " + celular + ": [" + tokenGerado + "]");
            System.out.println("----------------------------------------");
            
            System.out.print("2. Digite o código recebido [Tela 2]: ");
            String tokenDigitado = scanner.nextLine();

            if (tokenDigitado.equals(tokenGerado)) {
                controller.realizarLogin(celular);
                break;
            } else {
                System.out.println("\n❌ [ERRO] Token incorreto! Reinviando...");
                try { Thread.sleep(1500); } catch (InterruptedException ex) {}
                limparTela();
                System.out.println("--- BEM VINDO AO SERVIÇO DE FEIRA ---");
            }
        }

        // --- TELA 3: ESCOLHA DE PLANO ---
        limparTela();
        System.out.println("--- ESCOLHA SEU PLANO [Tela 3] ---");
        System.out.println("1. Plano Essencial - R$ 39,90/semana (Até 5 itens)");
        System.out.println("2. Plano Família   - R$ 69,90/semana (Até 10 itens)");
        System.out.println("3. Plano Premium   - R$ 99,90/semana (Até 15 itens)");
        System.out.print("\nSelecione uma opção (1, 2 ou 3): ");
        
        PlanoAssinatura planoEscolhido = null;
        int limitePlano = 0; 
        
        while (planoEscolhido == null) {
            String opcao = scanner.nextLine();
            switch (opcao) {
                case "1":
                    planoEscolhido = new PlanoAssinatura("Plano Essencial", 39.90, 5);
                    limitePlano = 5;
                    break;
                case "2":
                    planoEscolhido = new PlanoAssinatura("Plano Família", 69.90, 10);
                    limitePlano = 10;
                    break;
                case "3":
                    planoEscolhido = new PlanoAssinatura("Plano Premium", 99.90, 15);
                    limitePlano = 15;
                    break;
                default:
                    System.out.print("[AVISO] Opção inválida. Escolha 1, 2 ou 3: ");
            }
        }
        controller.registrarPlano(planoEscolhido);

        // --- CATÁLOGO DE PRODUTOS ---
        Produto[] catalogo = {
            new Produto("Maçã", 3.00), new Produto("Banana", 2.50), new Produto("Uva", 4.00), new Produto("Laranja", 2.00), new Produto("Morango", 5.00),
            new Produto("Cenoura", 2.00), new Produto("Tomate", 2.50), new Produto("Abobrinha", 3.00), new Produto("Batata", 4.00), new Produto("Cebola", 2.00),
            new Produto("Alface", 3.00), new Produto("Espinafre", 3.50), new Produto("Couve", 2.50), new Produto("Rúcula", 3.00), new Produto("Brócolis", 4.00)
        };

        // --- TELAS 4, 5 e 6: SELEÇÃO DE PRODUTOS ---
        int itensNaCesta = 0;
        boolean finalizouCesta = false;

        while (itensNaCesta < limitePlano && !finalizouCesta) {
            limparTela();
            System.out.println("--- SELEÇÃO DE PRODUTOS [Telas 4-6] ---");
            System.out.println("Plano atual: " + planoEscolhido.getNomePlano());
            System.out.println("Itens na cesta: " + itensNaCesta + " de " + limitePlano);
            System.out.println("---------------------------------------");

            // Exibição formatada por categorias
            System.out.println("[ FRUTAS ]");
            for (int i = 0; i < 5; i++) {
                System.out.printf("  %2d. %-12s - R$ %.2f\n", (i + 1), catalogo[i].getNome(), catalogo[i].getPreco());
            }

            System.out.println("\n[ LEGUMES ]");
            for (int i = 5; i < 10; i++) {
                System.out.printf("  %2d. %-12s - R$ %.2f\n", (i + 1), catalogo[i].getNome(), catalogo[i].getPreco());
            }

            System.out.println("\n[ VERDURAS ]");
            for (int i = 10; i < 15; i++) {
                System.out.printf("  %2d. %-12s - R$ %.2f\n", (i + 1), catalogo[i].getNome(), catalogo[i].getPreco());
            }

            System.out.println("\n 0. Ir direto para o Pagamento");
            System.out.println("---------------------------------------");
            System.out.print("Digite o código do produto (0-15): ");
            
            try {
                int escolha = Integer.parseInt(scanner.nextLine());
                
                if (escolha == 0) {
                    finalizouCesta = true;
                } else if (escolha >= 1 && escolha <= 15) {
                    Produto produtoSelecionado = catalogo[escolha - 1];
                    System.out.print("Quantidade de " + produtoSelecionado.getNome() + ": ");
                    int quantidade = Integer.parseInt(scanner.nextLine());
                    
                    if (quantidade > 0) {
                        int espacoLivre = limitePlano - itensNaCesta;
                        
                        // Check preditivo de limite
                        if (quantidade > espacoLivre) {
                            System.out.print("\n[AVISO] Você pediu " + quantidade + "x, mas há espaço para mais " + espacoLivre + " item(ns). Adicionar " + espacoLivre + "x " + produtoSelecionado.getNome() + "? (S/N): ");
                            String resposta = scanner.nextLine().toUpperCase();
                            if (resposta.equals("S")) {
                                quantidade = espacoLivre;
                            } else {
                                System.out.println("Adição cancelada.");
                                quantidade = 0;
                                try { Thread.sleep(1500); } catch (InterruptedException ex) {}
                            }
                        }

                        int adicionados = 0;
                        for (int q = 0; q < quantidade; q++) {
                            if (controller.adicionarNaCesta(produtoSelecionado).contains("sucesso")) {
                                itensNaCesta++;
                                adicionados++;
                            }
                        }
                        
                        if (adicionados > 0) {
                            System.out.println("\n✅ " + adicionados + "x " + produtoSelecionado.getNome() + " adicionado(s)!");
                            
                            if (itensNaCesta < limitePlano) {
                                System.out.print("Ainda há espaço na cesta. Deseja ir para o pagamento agora? (S/N): ");
                                String resposta = scanner.nextLine().toUpperCase();
                                if (resposta.equals("S")) {
                                    finalizouCesta = true;
                                }
                            } else {
                                System.out.println("\nLimite do plano atingido! Redirecionando para revisão...");
                                try { Thread.sleep(2000); } catch (InterruptedException ex) {}
                                finalizouCesta = true;
                            }
                        }
                    } else {
                        System.out.println("[AVISO] A quantidade deve ser maior que zero.");
                        try { Thread.sleep(1500); } catch (InterruptedException ex) {}
                    }
                } else {
                    System.out.println("[AVISO] Produto inexistente. Escolha entre 1 e 15.");
                    try { Thread.sleep(1500); } catch (InterruptedException ex) {}
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERRO] Digite apenas números válidos.");
                try { Thread.sleep(1500); } catch (InterruptedException ex) {}
            }
        }

        // --- TELA 7: REVISAR CESTA ---
        limparTela();
        System.out.println("--- REVISAR CESTA [Tela 7] ---");
        
        Map<String, Integer> contagemItens = new HashMap<>();
        Map<String, Double> precosItens = new HashMap<>();
        
        if (controller.getItensCesta() != null) {
            for (Produto p : controller.getItensCesta()) {
                contagemItens.put(p.getNome(), contagemItens.getOrDefault(p.getNome(), 0) + 1);
                precosItens.put(p.getNome(), p.getPreco());
            }
        }

        double totalEstimadoProdutos = 0.0;
        System.out.printf("%-15s %-15s %-10s\n", "Item", "Valor Un.", "Qtd");
        System.out.println("---------------------------------------------");
        
        for (String nome : contagemItens.keySet()) {
            int qtd = contagemItens.get(nome);
            double precoUn = precosItens.get(nome);
            double subtotal = qtd * precoUn;
            totalEstimadoProdutos += subtotal;
            
            System.out.printf("%-15s R$ %-12.2f %-10d\n", nome, precoUn, qtd);
        }
        
        System.out.println("---------------------------------------------");
        System.out.printf("Total Estimado dos Produtos: R$ %.2f\n", totalEstimadoProdutos);
        
        System.out.print("\n[Pressione ENTER para ir ao pagamento...]");
        scanner.nextLine();

        // --- TELA 8: PAGAMENTO ---
        limparTela();
        System.out.println("--- PAGAMENTO E FINALIZAÇÃO [Tela 8] ---");
        System.out.println("Plano Contratado: " + planoEscolhido.getNomePlano());
        System.out.println("Valor da Assinatura: R$ " + String.format("%.2f", planoEscolhido.getValor()));
        
        String cartao = "";
        while (true) {
            System.out.print("\n5. Informe o número do Cartão (16 dígitos): ");
            cartao = scanner.nextLine();
            if (cartao.replaceAll("[^0-9]", "").length() == 16) break;
            System.out.println("[AVISO] O cartão deve conter 16 números.");
        }

        String protocolo = controller.finalizarAssinatura(cartao);
        
        // --- CONFIRMAÇÃO FINAL ---
        limparTela();
        System.out.println("========================================");
        System.out.println("       ASSINATURA CONCLUÍDA             ");
        System.out.println("========================================");
        System.out.println("\n✅ Pagamento Aprovado com sucesso!");
        System.out.println("Protocolo de confirmação: " + protocolo);
        System.out.println("Plano: " + planoEscolhido.getNomePlano());
        System.out.println("\nOs detalhes foram salvos em 'assinaturas.txt'.");
        System.out.println("----------------------------------------");
        
        System.out.println("\n[Pressione ENTER para fechar o programa...]");
        scanner.nextLine(); 
        
        scanner.close();
        System.out.println("Encerrando sistema...");
    }
}