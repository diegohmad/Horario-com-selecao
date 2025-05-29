package generator;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import models.Individuo;
import models.Materia;
import models.Populacao;
import models.Professor;

public class GeradorHTML {

    public static void gerarArquivoCompleto(
            Map<Materia, Professor> associacoes,
            Populacao populacao,
            Individuo[] selecionados,
            Individuo[] filhos,
            String arquivo) throws IOException {
        gerarArquivoCompleto(associacoes, populacao, selecionados, filhos, arquivo, -1);
    }

    public static void gerarArquivoCompleto(
            Map<Materia, Professor> associacoes,
            Populacao populacao,
            Individuo[] selecionados,
            Individuo[] filhos,
            String arquivo,
            int geracaoFinal) throws IOException {

        StringBuilder h = new StringBuilder();
        h.append("""
            <!DOCTYPE html><html lang='pt-BR'><head><meta charset='UTF-8'>
            <title>AG – Associação e População</title>
            <style>
              table{border-collapse:collapse;margin-bottom:40px;}
              th,td{border:1px solid #888;padding:4px 6px;text-align:center;}
              th{background:#f2f2f2;}
              .conf{background-color:#ffcccc;}
            </style></head><body>
            """);

        // 1. Associação Professor × Matéria
        h.append("<h2>Associação Professor × Matéria</h2><table>")
                .append("<tr><th>Período</th><th>Matéria</th><th>Cód.Mat.</th>")
                .append("<th>Professor</th><th>Cód.Prof</th><th>Comb.</th></tr>");

        associacoes.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getKey().getCodigo()))
                .forEach(e -> {
                    Materia m = e.getKey();
                    Professor p = e.getValue();
                    h.append("<tr><td>").append(m.getPeriodo()).append("</td>")
                            .append("<td>").append(m.getNome()).append("</td>")
                            .append("<td>").append(m.getCodigo()).append("</td>")
                            .append("<td>").append(p.getNome()).append("</td>")
                            .append("<td>").append(p.getCodigo()).append("</td>")
                            .append("<td>").append(p.getCodigo()).append(m.getCodigo())
                            .append("</td></tr>");
                });
        h.append("</table>");

        // Helpers para imprimir
        java.util.function.BiConsumer<Individuo[], String> imprimeArr
                = (arr, tit) -> {
                    if (arr != null && arr.length > 0) {
                        imprimeTabela(h, List.of(arr), tit);
                    }
                };

        java.util.function.BiConsumer<List<Individuo>, String> imprimeList
                = (lst, tit) -> {
                    if (lst != null && !lst.isEmpty()) {
                        imprimeTabela(h, lst, tit);
                    }
                };

        // 2. População final
        imprimeList.accept(populacao.getIndividuos(),
                "População (" + populacao.getTamanho() + " × 100)");

        // 3. Tabela do melhor ou pais
        if (selecionados != null && selecionados.length > 0) {
            String titulo = (selecionados.length == 1)
                    ? "Melhor Indivíduo" + (geracaoFinal > 0 ? " (Geração " + geracaoFinal + ")" : "")
                    : "Pais Selecionados (Geração " + geracaoFinal + ")";
            imprimeArr.accept(selecionados, titulo);
        }

        // 4. Filhos (se existirem)
        if (filhos != null && filhos.length > 0) {
            String tituloFilhos = (geracaoFinal > 0)
                    ? "Filhos Gerados (Geração " + geracaoFinal + ")"
                    : "Melhor resultado da última geração";
            imprimeArr.accept(filhos, tituloFilhos);
        }

        h.append("</body></html>");

        try (BufferedWriter w = new BufferedWriter(new FileWriter(arquivo))) {
            w.write(h.toString());
        }

        File f = new File(arquivo);
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(f.toURI());
        } else {
            System.out.println("Abra manualmente: " + f.getAbsolutePath());
        }
    }

    private static void imprimeTabela(StringBuilder h,
            java.util.Collection<Individuo> col,
            String titulo) {

        h.append("<h2>").append(titulo).append("</h2><table><tr>");
        for (int i = 1; i <= 100; i++) {
            h.append("<th>").append(i).append("</th>");
        }
        h.append("<th>Conflitos</th></tr>");

        for (Individuo ind : col) {
            String[] genes = ind.getGenes();
            boolean[] confl = new boolean[100];

            for (int slot = 0; slot < 20; slot++) {
                Map<String, Integer> first = new HashMap<>();
                for (int per = 0; per < 5; per++) {
                    int colIdx = per * 20 + slot;
                    String prof = genes[colIdx].substring(0, 2);
                    Integer prev = first.putIfAbsent(prof, colIdx);
                    if (prev != null) {
                        confl[prev] = confl[colIdx] = true;
                    }
                }
            }

            h.append("<tr>");
            for (int c = 0; c < 100; c++) {
                if (confl[c]) {
                    h.append("<td class='conf'>").append(genes[c]).append("</td>");
                } else {
                    h.append("<td>").append(genes[c]).append("</td>");
                }
            }
            h.append("<td>").append(ind.getConflitos()).append("</td></tr>");
        }

        h.append("</table>");
    }
}
