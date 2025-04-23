/* ───────── src/generator/GeradorHTML.java ───────── */
package generator;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import models.Individuo;
import models.Materia;
import models.Populacao;
import models.Professor;

public class GeradorHTML {

    /* ---------- GERA UM ÚNICO HTML COM 3 TABELAS ---------- */
    public static void gerarArquivoCompleto(
            Map<Materia, Professor> associacoes,
            Populacao populacao,
            Individuo[] selecionados,          // ← novos “pais”
            String arquivo) throws IOException {

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

        /* ---------- 1. TABELA ASSOCIAÇÃO ---------- */
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

        /* ---------- helper para imprimir qualquer lista de indivíduos ---- */
        java.util.function.BiConsumer<java.util.Collection<Individuo>,String>
            imprimeTabela = (colecao,titulo) -> {
                h.append("<h2>").append(titulo).append("</h2><table><tr>");
                for (int i=1;i<=100;i++) h.append("<th>").append(i).append("</th>");
                h.append("<th>Conflitos</th></tr>");

                for (Individuo ind : colecao) {
                    String[] genes = ind.getGenes();

                    /* ----- máscara de conflito ----- */
                    boolean[] confl = new boolean[100];
                    for (int slot=0;slot<20;slot++){
                        Map<String,Integer> first = new HashMap<>();
                        for (int per=0;per<5;per++){
                            int col = per*20+slot;
                            String prof = genes[col].substring(0,2);
                            Integer prev = first.putIfAbsent(prof,col);
                            if(prev!=null){ confl[prev]=confl[col]=true; }
                        }
                    }

                    h.append("<tr>");
                    for (int c=0;c<100;c++){
                        if(confl[c]) h.append("<td class='conf'>")
                                      .append(genes[c]).append("</td>");
                        else         h.append("<td>")
                                      .append(genes[c]).append("</td>");
                    }
                    h.append("<td>").append(ind.getConflitos()).append("</td></tr>");
                }
                h.append("</table>");
        };

        /* ---------- 2. POPULAÇÃO COMPLETA ---------- */
        imprimeTabela.accept(populacao.getIndividuos(),
                             "População Inicial ("+populacao.getTamanho()+" × 100)");

        /* ---------- 3. INDIVÍDUOS SELECIONADOS ---------- */
        imprimeTabela.accept(java.util.List.of(selecionados),
                             "Selecionados Aleatoriamente (2 indivíduos)");

        /* ---------- grava arquivo e abre ---------- */
        try (BufferedWriter w = new BufferedWriter(new FileWriter(arquivo))) {
            w.write(h.toString());
        }
        File f = new File(arquivo);
        if (Desktop.isDesktopSupported())
            Desktop.getDesktop().browse(f.toURI());
        else
            System.out.println("Abra manualmente: " + f.getAbsolutePath());
    }
}
