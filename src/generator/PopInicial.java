/* ───────── src/generator/PopInicial.java ───────── */
package generator;

import data.InicializadorDados;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import models.Individuo;
import models.Materia;
import models.Populacao;
import models.Professor;

public class PopInicial {

    private static final Random RND = new Random();

    private static final List<Professor> PROFESSORES =
            InicializadorDados.criarProfessores();
    private static final Map<String, List<Materia>> MATERIAS =
            InicializadorDados.criarMateriasPorPeriodo();

    /* --------- 1. Associação professor × matéria ------------------ */
    public static Map<Materia, Professor> associarProfessoresAMaterias() {

        Map<Materia, Professor> mapa = new HashMap<>();
        Map<String, List<Professor>> disponiveisPorPeriodo = new HashMap<>();

        MATERIAS.keySet().forEach(
                p -> disponiveisPorPeriodo.put(p, new ArrayList<>(PROFESSORES)));

        /* 1 matéria = 1 prof, sem repetir prof no mesmo período */
        MATERIAS.forEach((periodo, listaMat) -> {
            for (Materia mat : listaMat) {
                List<Professor> disp = disponiveisPorPeriodo.get(periodo);
                Professor escolhido = disp.remove(RND.nextInt(disp.size()));
                mapa.put(mat, escolhido);
            }
        });
        return mapa;
    }

    /* --------- 2. População: N indivíduos × 100 genes ------------- */
    public static Populacao gerarPopulacaoInicial(
            Map<Materia, Professor> associacoes, int tamanho) {

        /* gera tabela período → lista de 20 códigos (4 cópias × 5 matérias) */
        Map<String, List<String>> codigosPorPeriodo = new TreeMap<>();
        associacoes.forEach((mat, prof) -> {
            String codigo = prof.getCodigo() + mat.getCodigo();   // “ppmm”
            codigosPorPeriodo
                    .computeIfAbsent(mat.getPeriodo(), _ -> new ArrayList<>());
            for (int i = 0; i < 4; i++)                          // 4 cópias
                codigosPorPeriodo.get(mat.getPeriodo()).add(codigo);
        });

        Populacao pop = new Populacao(tamanho);

        for (int i = 0; i < tamanho; i++) {
            List<String> linha = new ArrayList<>(100);
            for (String periodo : codigosPorPeriodo.keySet()) {
                List<String> genes = new ArrayList<>(codigosPorPeriodo.get(periodo));
                Collections.shuffle(genes, RND);                  // embaralha 20
                linha.addAll(genes);
            }
            pop.add(new Individuo(linha.toArray(String[]::new))); // cria indivíduo
        }
        return pop;                                               //  ← agora Populacao
    }
}
