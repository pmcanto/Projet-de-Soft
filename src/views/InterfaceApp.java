package views;

import controllers.AssinaturaController;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import javax.swing.*;
import models.PlanoAssinatura;
import models.Produto;

public class InterfaceApp {

    private static AssinaturaController controller;
    private static PlanoAssinatura planoEscolhido;
    private static int limitePlano;
    private static String tokenGerado;
    private static String celularUsuario;

    private static JFrame frame;
    private static final int LARGURA = 360;
    private static final int ALTURA = 640;

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ex) {}

        controller = new AssinaturaController();
        
        frame = new JFrame("App Feira");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(LARGURA, ALTURA);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        exibirTelaLogin();
        frame.setVisible(true);
    }

    private static JButton criarBotaoPrincipal(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(Color.BLACK);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(LARGURA, 60));
        return btn;
    }

    private static JPanel criarHeader(String titulo, String subtitulo) {
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(new Color(245, 245, 245));
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 15, 0));

        JLabel lblTitulo = new JLabel(titulo, JLabel.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel(subtitulo, JLabel.CENTER);
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(Color.GRAY);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(lblTitulo);
        header.add(Box.createRigidArea(new Dimension(0, 5)));
        header.add(lblSub);
        return header;
    }

    private static void exibirTelaLogin() {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        frame.add(criarHeader("BEM-VINDO", "Identifique-se para começar"), BorderLayout.NORTH);

        JPanel corpo = new JPanel(new GridBagLayout());
        corpo.setBackground(Color.WHITE);
        
        JTextField txtCelular = new JTextField(12);
        txtCelular.setHorizontalAlignment(JTextField.CENTER);
        txtCelular.setFont(new Font("SansSerif", Font.BOLD, 20));
        txtCelular.setBorder(BorderFactory.createTitledBorder("Celular (com DDD)"));
        
        txtCelular.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) || txtCelular.getText().length() >= 11) e.consume();
            }
        });
        
        corpo.add(txtCelular);
        frame.add(corpo, BorderLayout.CENTER);

        JButton btnLogin = criarBotaoPrincipal("ENVIAR CÓDIGO");
        btnLogin.addActionListener(e -> {
            celularUsuario = txtCelular.getText().replaceAll("[^0-9]", "");
            if (celularUsuario.length() == 11) {
                tokenGerado = String.format("%04d", new Random().nextInt(10000));
                JOptionPane.showMessageDialog(frame, "SMS enviado para " + celularUsuario + "\nTOKEN: [" + tokenGerado + "]");
                exibirTelaSMS();
            } else {
                JOptionPane.showMessageDialog(frame, "Digite os 11 números do celular.");
            }
        });
        frame.add(btnLogin, BorderLayout.SOUTH);
        frame.revalidate();
        frame.repaint();
    }

    private static void exibirTelaSMS() {
        frame.getContentPane().removeAll();
        frame.add(criarHeader("VALIDAÇÃO", "Enviamos um código para seu celular"), BorderLayout.NORTH);

        JPanel corpo = new JPanel(new GridBagLayout());
        corpo.setBackground(Color.WHITE);
        JTextField txtToken = new JTextField(10);
        txtToken.setHorizontalAlignment(JTextField.CENTER);
        txtToken.setFont(new Font("SansSerif", Font.BOLD, 20));
        txtToken.setBorder(BorderFactory.createTitledBorder("Código de 4 dígitos"));
        
        txtToken.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) || txtToken.getText().length() >= 4) e.consume();
            }
        });

        corpo.add(txtToken);
        frame.add(corpo, BorderLayout.CENTER);

        JButton btnValidar = criarBotaoPrincipal("VALIDAR ACESSO");
        btnValidar.addActionListener(e -> {
            if (txtToken.getText().equals(tokenGerado)) {
                controller.realizarLogin(celularUsuario);
                exibirTelaPlanos();
            } else {
                JOptionPane.showMessageDialog(frame, "Token incorreto!");
            }
        });
        frame.add(btnValidar, BorderLayout.SOUTH);
        frame.revalidate();
        frame.repaint();
    }

    private static void exibirTelaPlanos() {
        frame.getContentPane().removeAll();
        frame.add(criarHeader("PLANOS", "Selecione sua assinatura semanal"), BorderLayout.NORTH);

        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
        corpo.setBackground(Color.WHITE);
        corpo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] opcoes = {"Plano Essencial - R$ 39,90 - 5 Produtos", "Plano Família - R$ 69,90 - 10 Produtos", "Plano Premium - R$ 99,90 - 15 Produtos"};
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String s : opcoes) model.addElement(s);
        JList<String> listaPlanos = new JList<>(model);
        listaPlanos.setSelectedIndex(0);
        listaPlanos.setFixedCellHeight(50);
        listaPlanos.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        corpo.add(listaPlanos);
        frame.add(corpo, BorderLayout.CENTER);

        JButton btnPlano = criarBotaoPrincipal("ESCOLHER PLANO");
        btnPlano.addActionListener(e -> {
            String selecao = listaPlanos.getSelectedValue();
            if (selecao.contains("Essencial")) { planoEscolhido = new PlanoAssinatura("Plano Essencial", 39.90, 5); limitePlano = 5; }
            else if (selecao.contains("Família")) { planoEscolhido = new PlanoAssinatura("Plano Família", 69.90, 10); limitePlano = 10; }
            else { planoEscolhido = new PlanoAssinatura("Plano Premium", 99.90, 15); limitePlano = 15; }
            
            controller.registrarPlano(planoEscolhido);
            exibirTelaProdutos();
        });
        frame.add(btnPlano, BorderLayout.SOUTH);
        frame.revalidate();
        frame.repaint();
    }

    private static void exibirTelaProdutos() {
        frame.getContentPane().removeAll();
        
        // --- CABEÇALHO COM CONTADOR DINÂMICO ---
        JPanel header = criarHeader("MONTE SUA CESTA", planoEscolhido.getNomePlano());
        JLabel lblContador = new JLabel("Itens: 0 / " + limitePlano, JLabel.CENTER);
        lblContador.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblContador.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.add(lblContador);
        frame.add(header, BorderLayout.NORTH);

        JPanel panelProdutos = new JPanel();
        panelProdutos.setLayout(new BoxLayout(panelProdutos, BoxLayout.Y_AXIS));
        panelProdutos.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(panelProdutos);
        scrollPane.setBorder(null);

        Produto[] catalogo = {
            new Produto("Maçã", 3.0), new Produto("Banana", 2.5), new Produto("Uva", 4.0),
            new Produto("Laranja", 2.0), new Produto("Morango", 5.0), new Produto("Cenoura", 2.0),
            new Produto("Tomate", 2.5), new Produto("Batata", 4.0), new Produto("Alface", 3.0),
            new Produto("Brócolis", 4.0), new Produto("Cebola", 2.0), new Produto("Couve", 2.5)
        };

        Map<Produto, JSpinner> seletores = new HashMap<>();

        for (Produto prod : catalogo) {
            JPanel item = new JPanel(new BorderLayout());
            item.setBackground(Color.WHITE);
            item.setMaximumSize(new Dimension(LARGURA, 60));
            item.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            JLabel name = new JLabel("<html>" + prod.getNome() + "<br><font color='gray'>R$ " + String.format("%.2f", prod.getPreco()) + "</font></html>");
            JSpinner spin = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1)); // Limite alto no spinner para permitir o aviso de erro
            
            // ATUALIZAÇÃO DO CONTADOR EM TEMPO REAL
            spin.addChangeListener(e -> {
                int total = 0;
                for (JSpinner s : seletores.values()) total += (int) s.getValue();
                lblContador.setText("Itens: " + total + " / " + limitePlano);
                lblContador.setForeground(total > limitePlano ? Color.RED : new Color(34, 139, 34)); // Verde se ok, vermelho se erro
            });

            seletores.put(prod, spin);
            item.add(name, BorderLayout.CENTER);
            item.add(spin, BorderLayout.EAST);
            panelProdutos.add(item);
            panelProdutos.add(new JSeparator());
        }

        JButton btnCesta = criarBotaoPrincipal("REVISAR E PAGAR");
        btnCesta.addActionListener(e -> {
            int total = 0;
            for (JSpinner s : seletores.values()) total += (int) s.getValue();

            if (total > limitePlano) JOptionPane.showMessageDialog(frame, "Limite excedido! Remova " + (total - limitePlano) + " item(ns).", "Atenção", JOptionPane.WARNING_MESSAGE);
            else if (total == 0) JOptionPane.showMessageDialog(frame, "Selecione ao menos um produto.");
            else {
                for (Map.Entry<Produto, JSpinner> entry : seletores.entrySet()) {
                    for (int i = 0; i < (int) entry.getValue().getValue(); i++) controller.adicionarNaCesta(entry.getKey());
                }
                exibirTelaPagamento();
            }
        });
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(btnCesta, BorderLayout.SOUTH);
        frame.revalidate();
        frame.repaint();
    }

    private static void exibirTelaPagamento() {
        frame.getContentPane().removeAll();
        frame.add(criarHeader("PAGAMENTO", "Confira seu resumo"), BorderLayout.NORTH);

        JPanel corpo = new JPanel();
        corpo.setLayout(new BoxLayout(corpo, BoxLayout.Y_AXIS));
        corpo.setBackground(Color.WHITE);
        corpo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Map<String, Integer> qtds = new TreeMap<>();
        Map<String, Double> precos = new HashMap<>();
        double totalProd = 0;

        for (Produto p : controller.getItensCesta()) {
            qtds.put(p.getNome(), qtds.getOrDefault(p.getNome(), 0) + 1);
            precos.put(p.getNome(), p.getPreco());
        }

        for (String n : qtds.keySet()) {
            int q = qtds.get(n); double un = precos.get(n); double sub = q * un;
            totalProd += sub;
            JLabel lbl = new JLabel("• " + n + ": " + q + "x R$" + un + " = R$" + sub);
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));
            corpo.add(lbl);
        }

        corpo.add(Box.createRigidArea(new Dimension(0, 15)));
        corpo.add(new JSeparator());
        JLabel lblTotal = new JLabel("VALOR DO PLANO: R$ " + String.format("%.2f", planoEscolhido.getValor()));
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 14));
        corpo.add(lblTotal);

        JScrollPane scrollResumo = new JScrollPane(corpo);
        scrollResumo.setBorder(null);
        frame.add(scrollResumo, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(245, 245, 245));
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        JPanel pnlCartao = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 15));
        pnlCartao.setBackground(new Color(245, 245, 245));
        
        JTextField[] camposCartao = new JTextField[4];
        for (int i = 0; i < 4; i++) {
            camposCartao[i] = new JTextField(5);
            camposCartao[i].setHorizontalAlignment(JTextField.CENTER);
            camposCartao[i].setFont(new Font("Monospaced", Font.BOLD, 14));
            final int index = i;
            camposCartao[i].addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    if (!Character.isDigit(e.getKeyChar()) || camposCartao[index].getText().length() >= 4) e.consume();
                }
                public void keyReleased(KeyEvent e) {
                    if (camposCartao[index].getText().length() == 4 && index < 3) camposCartao[index + 1].requestFocus();
                }
            });
            pnlCartao.add(camposCartao[i]);
            if (i < 3) pnlCartao.add(new JLabel("-"));
        }

        footer.add(pnlCartao, BorderLayout.CENTER);

        JButton btnFinal = criarBotaoPrincipal("CONFIRMAR ASSINATURA");
        btnFinal.addActionListener(e -> {
            String cartao = camposCartao[0].getText() + camposCartao[1].getText() + camposCartao[2].getText() + camposCartao[3].getText();
            if (cartao.length() == 16) {
                String protocolo = controller.finalizarAssinatura(cartao);
                JOptionPane.showMessageDialog(frame, "Sucesso!\nProtocolo: " + protocolo);
                System.exit(0);
            } else {
                JOptionPane.showMessageDialog(frame, "Cartão inválido!");
            }
        });
        footer.add(btnFinal, BorderLayout.SOUTH);
        frame.add(footer, BorderLayout.SOUTH);
        frame.revalidate();
        frame.repaint();
    }
}