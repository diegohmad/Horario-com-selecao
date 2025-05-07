/* ───────── src/generator/Cruzador.java ───────── */
package generator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import models.Individuo;

/**
 * Utilitário de cruzamento (crossover) entre indivíduos.
 * Inclui agora o método {@code cruzarPorPeriodosAleatorios}, que
 * escolhe de 1 a 4 “cortes” somente nos limites de período
 * (20, 40, 60, 80) e alterna os blocos entre Pai A e Pai B.
 */
public final class Cruzador {

    private static final Random RND = new Random();

    private Cruzador() { }

    /* ---------------------------------------------------------------
     * 1-ponto genérico (se ainda quiser usar)
     * ------------------------------------------------------------- */
    public static Individuo[] cruzarUmPonto(Individuo paiA, Individuo paiB) {
        int ponto = RND.nextInt(99) + 1;                       // 1–99
        return cruzarComCortes(paiA, paiB, new int[]{ponto});
    }

    /* ---------------------------------------------------------------
     * NOVO: cortes aleatórios em limites de período (multiplo de 20)
     * ------------------------------------------------------------- */
    public static Individuo[] cruzarPorPeriodosAleatorios(Individuo paiA,
                                                          Individuo paiB) {

        /* Possíveis pontos de corte: 20, 40, 60, 80 */
        int[] limites = {20, 40, 60, 80};

        /* Sorteia quantos cortes (1-4) */
        int nCortes = 1 + RND.nextInt(limites.length);

        /* Embaralha, pega os n primeiros e ordena */
        List<Integer> lista = new ArrayList<>();
        for(int l : limites) lista.add(l);
        Collections.shuffle(lista, RND);
        int[] cortes = new int[nCortes];
        for(int i=0;i<nCortes;i++) cortes[i] = lista.get(i);
        java.util.Arrays.sort(cortes);

        return cruzarComCortes(paiA, paiB, cortes);
    }

    /* ---------------------------------------------------------------
     * Implementação comum – alterna blocos entre cortes
     * ------------------------------------------------------------- */
    private static Individuo[] cruzarComCortes(Individuo paiA, Individuo paiB,
                                               int[] cortes) {

        String[] gA = paiA.getGenes();
        String[] gB = paiB.getGenes();
        String[] f1 = new String[100];
        String[] f2 = new String[100];

        int srcStart = 0;
        boolean pegaA = true;                       // alterna entre pais
        for(int corte : cortes){
            int len = corte - srcStart;
            System.arraycopy(pegaA ? gA : gB, srcStart, f1, srcStart, len);
            System.arraycopy(pegaA ? gB : gA, srcStart, f2, srcStart, len);
            pegaA = !pegaA;
            srcStart = corte;
        }
        /* Copia o trecho final (após o último corte) */
        int len = 100 - srcStart;
        System.arraycopy(pegaA ? gA : gB, srcStart, f1, srcStart, len);
        System.arraycopy(pegaA ? gB : gA, srcStart, f2, srcStart, len);

        return new Individuo[]{ new Individuo(f1), new Individuo(f2) };
    }
}
