package views;

import controllers.AssinaturaController;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import javax.swing.*;
import models.PlanoAssinatura;
import models.Produto;

/**
 * Camada de Fronteira (Boundary) - Interface Gráfica Swing
 * N2: Versão Final com Resumo em Fonte Padrão e Estética Windows
 */
public class InterfaceApp {

    private static AssinaturaController controller;
    private static PlanoAssinatura planoEscolhido;
    private static int limitePlano;

    public static void main(String[] args) {
        // Aplica o visual do sistema operacional (Windows/Linux/Mac)
        try { 
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
        } catch (Exception ex) {}

        controller = new AssinaturaController();
        Random random = new Random();

        // --- TELAS 1 E 2: LOGIN E TOKEN (4 DÍGITOS) ---
        String celular = "";
        while (true) {
            celular = JOptionPane.showInputDialog(null, 
                "Digite seu celular com DDD (11 dígitos):", 
                "Login - Serviço de Feira", JOptionPane.QUESTION_MESSAGE);
            
            if (celular == null) System.exit(0); 
            
            if (celular.replaceAll("[^0-9]", "").length() == 11) {
                break;
            }
            JOptionPane.showMessageDialog(null, "Número inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        String tokenGerado = String.format("%04d", random.nextInt(10000));
        JOptionPane.showMessageDialog(null, 
            "SMS enviado para " + celular + "\nTOKEN: [" + tokenGerado + "]", 
            "Simulação de Token", JOptionPane.INFORMATION_MESSAGE);

        String tokenDigitado = JOptionPane.showInputDialog(null, "Digite o Token de 4 dígitos:", "Validação", JOptionPane.QUESTION_MESSAGE);
        
        if (tokenDigitado != null && tokenDigitado.equals(tokenGerado)) {
            controller.realizarLogin(celular);
        } else {
            JOptionPane.showMessageDialog(null, "Token inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        // --- TELA 3: ESCOLHA DE PLANO ---
        String[] opcoesPlanos = {
            "Plano Essencial - R$ 39,90 (Até 5 itens)", 
            "Plano Família - R$ 69,90 (Até 10 itens)", 
            "Plano Premium - R$ 99,90 (Até 15 itens)"
        };

        String selecao = (String) JOptionPane.showInputDialog(null, 
                "Selecione o plano desejado:", "Planos", 
                JOptionPane.QUESTION_MESSAGE, null, opcoesPlanos, opcoesPlanos[0]);
        
        if (selecao == null) System.exit(0);

        if (selecao.contains("Essencial")) {
            planoEscolhido = new PlanoAssinatura("Plano Essencial", 39.90, 5);
            limitePlano = 5;
        } else if (selecao.contains("Família")) {
            planoEscolhido = new PlanoAssinatura("Plano Família", 69.90, 10);
            limitePlano = 10;
        } else {
            planoEscolhido = new PlanoAssinatura("Plano Premium", 99.90, 15);
            limitePlano = 15;
        }
        
        controller.registrarPlano(planoEscolhido);

        exibirJanelaProdutos();
    }

    private static void exibirJanelaProdutos() {
        JFrame frame = new JFrame("Montagem da Cesta");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 650);
        frame.setLayout(new BorderLayout(15, 15));

        JPanel panelProdutos = new JPanel();
        panelProdutos.setLayout(new BoxLayout(panelProdutos, BoxLayout.Y_AXIS));
        panelProdutos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(panelProdutos);

        Produto[] catalogo = {
            new Produto("Maçã", 3.0), new Produto("Banana", 2.5), new Produto("Uva", 4.0),
            new Produto("Laranja", 2.0), new Produto("Morango", 5.0), new Produto("Cenoura", 2.0),
            new Produto("Tomate", 2.5), new Produto("Abobrinha", 3.0), new Produto("Batata", 4.0),
            new Produto("Cebola", 2.0), new Produto("Alface", 3.0), new Produto("Espinafre", 3.5),
            new Produto("Couve", 2.5), new Produto("Rúcula", 3.0), new Produto("Brócolis", 4.0)
        };

        Map<Produto, JSpinner> seletores = new HashMap<>();

        for (Produto prod : catalogo) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setMaximumSize(new Dimension(450, 40));
            itemPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

            JLabel label = new JLabel(prod.getNome() + " - R$ " + String.format("%.2f", prod.getPreco()));
            // Usando fonte SansSerif (Normal)
            label.setFont(new Font("SansSerif", Font.PLAIN, 13));
            
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, limitePlano, 1));
            seletores.put(prod, spinner);

            JPanel pnlQtd = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pnlQtd.add(new JLabel("Qtd:"));
            pnlQtd.add(spinner);
            
            itemPanel.add(label, BorderLayout.CENTER);
            itemPanel.add(pnlQtd, BorderLayout.EAST);
            panelProdutos.add(itemPanel);
        }

        // BOTÃO PRETO
        JButton btnAvancar = new JButton("ADICIONAR ITENS E FINALIZAR");
        btnAvancar.setForeground(Color.BLACK); 
        btnAvancar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnAvancar.setPreferredSize(new Dimension(0, 50));
        btnAvancar.setFocusPainted(false);

        btnAvancar.addActionListener(e -> {
            int totalSelecionado = 0;
            for (JSpinner s : seletores.values()) totalSelecionado += (int) s.getValue();

            if (totalSelecionado > limitePlano) {
                JOptionPane.showMessageDialog(frame, "Limite excedido!", "Erro", JOptionPane.ERROR_MESSAGE);
            } else if (totalSelecionado == 0) {
                JOptionPane.showMessageDialog(frame, "Cesta vazia!");
            } else {
                boolean prosseguir = true;
                if (totalSelecionado < limitePlano) {
                    int confirm = JOptionPane.showConfirmDialog(frame, "Cesta incompleta. Deseja fechar assim mesmo?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (confirm != JOptionPane.YES_OPTION) prosseguir = false;
                }

                if (prosseguir) {
                    for (Map.Entry<Produto, JSpinner> entry : seletores.entrySet()) {
                        int qtd = (int) entry.getValue().getValue();
                        for (int i = 0; i < qtd; i++) controller.adicionarNaCesta(entry.getKey());
                    }
                    frame.dispose();
                    irParaPagamento();
                }
            }
        });

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(btnAvancar, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }

    private static void irParaPagamento() {
        // Agrupar produtos para o resumo
        Map<String, Integer> qtdPorItem = new TreeMap<>();
        Map<String, Double> precoUnPorItem = new HashMap<>();

        for (Produto p : controller.getItensCesta()) {
            qtdPorItem.put(p.getNome(), qtdPorItem.getOrDefault(p.getNome(), 0) + 1);
            precoUnPorItem.put(p.getNome(), p.getPreco());
        }

        // Criar painel de resumo com fonte normal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        JLabel title = new JLabel("Resumo da Assinatura");
        title.setFont(new Font("SansSerif", Font.BOLD, 15));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        double somaProdutos = 0;
        for (String nome : qtdPorItem.keySet()) {
            int qtd = qtdPorItem.get(nome);
            double un = precoUnPorItem.get(nome);
            double sub = qtd * un;
            somaProdutos += sub;
            
            JLabel itemLabel = new JLabel(String.format("• %s: %dx R$ %.2f = R$ %.2f", nome, qtd, un, sub));
            itemLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
            itemLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(itemLabel);
        }

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(new JSeparator());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JLabel totalItens = new JLabel("Soma dos itens: R$ " + String.format("%.2f", somaProdutos));
        totalItens.setFont(new Font("SansSerif", Font.ITALIC, 12));
        mainPanel.add(totalItens);

        JLabel totalPlano = new JLabel("Valor do Plano (" + planoEscolhido.getNomePlano() + "): R$ " + String.format("%.2f", planoEscolhido.getValor()));
        totalPlano.setFont(new Font("SansSerif", Font.BOLD, 14));
        mainPanel.add(totalPlano);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        JLabel cardLabel = new JLabel("Informe os 16 dígitos do seu cartão:");
        cardLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        mainPanel.add(cardLabel);

        // Pedir o cartão
        String cartao = "";
        while (true) {
            cartao = JOptionPane.showInputDialog(null, mainPanel, "Pagamento", JOptionPane.QUESTION_MESSAGE);
            
            if (cartao == null) System.exit(0);
            if (cartao.replaceAll("[^0-9]", "").length() == 16) break;
            
            JOptionPane.showMessageDialog(null, "O cartão deve conter 16 números!", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        String protocolo = controller.finalizarAssinatura(cartao);
        
        // Mensagem final
        String finalMsg = "Sucesso! Seu protocolo é: " + protocolo + "\n\nO comprovante foi salvo em 'assinaturas.txt'.";
        JOptionPane.showMessageDialog(null, finalMsg, "Assinatura Concluída", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}