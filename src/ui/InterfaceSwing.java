package ui;

import generator.AlgoritmoGenetico;
import generator.GeradorHTML;
import generator.PopInicial;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import models.Geracao;
import models.Individuo;
import models.Materia;
import models.Populacao;
import models.Professor;

public class InterfaceSwing extends JFrame {

    private final JTextArea resultadoArea = new JTextArea(5, 40);
    private final JButton gerarBotao = new JButton("Executar AG e Ver Horário");
    private final JButton abrirHTMLBotao = new JButton("Abrir HTML Final");
    private final JButton verGeracaoBotao = new JButton("Ver Geração Selecionada");
    private final JComboBox<Integer> comboGeracoes = new JComboBox<>();
    private Map<Materia, Professor> associacoes;

    public InterfaceSwing() {
        super("Gerador de Horários com AG");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        resultadoArea.setEditable(false);
        resultadoArea.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JPanel botoes = new JPanel();
        botoes.add(gerarBotao);
        botoes.add(abrirHTMLBotao);
        botoes.add(comboGeracoes);
        botoes.add(verGeracaoBotao);

        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        gerarBotao.addActionListener(this::executarAG);
        abrirHTMLBotao.addActionListener(e -> abrirHTML("horario_final.html"));
        verGeracaoBotao.addActionListener(e -> verGeracaoSelecionada());

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void executarAG(ActionEvent e) {
        try {
            associacoes = PopInicial.associarProfessoresAMaterias();
            Individuo melhor = AlgoritmoGenetico.executar(associacoes, 50, 100, 0.02);

            resultadoArea.setText("Melhor horário gerado com " + melhor.getConflitos() + " conflitos.\n\n");
            resultadoArea.append("Arquivo: horario_final.html");

            comboGeracoes.removeAllItems();
            for (Geracao g : AlgoritmoGenetico.getHistorico()) {
                comboGeracoes.addItem(g.getNumero());
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void abrirHTML(String nomeArquivo) {
        try {
            File f = new File(nomeArquivo);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(f.toURI());
            } else {
                JOptionPane.showMessageDialog(this, "Abra manualmente: " + f.getAbsolutePath());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao abrir HTML: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void verGeracaoSelecionada() {
        Integer numero = (Integer) comboGeracoes.getSelectedItem();
        if (numero == null || associacoes == null) {
            JOptionPane.showMessageDialog(this, "Nenhuma geração selecionada ou AG ainda não executado.");
            return;
        }

        List<Geracao> historico = AlgoritmoGenetico.getHistorico();
        Geracao geracao = historico.stream()
                .filter(g -> g.getNumero() == numero)
                .findFirst()
                .orElse(null);

        if (geracao != null) {
            try {
                Populacao pop = new Populacao(geracao.getPopulacao().size());
                for (Individuo ind : geracao.getPopulacao()) {
                    pop.add(ind);
                }

                String nomeArquivo = "geracao_" + geracao.getNumero() + ".html";
                GeradorHTML.gerarArquivoCompleto(
                        associacoes,
                        pop,
                        geracao.getPaisSelecionados(),
                        geracao.getFilhosGerados(),
                        nomeArquivo,
                        geracao.getNumero()
                );
                abrirHTML(nomeArquivo);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao gerar HTML da geração: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfaceSwing::new);
    }
}
