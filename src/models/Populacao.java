package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Contêiner da população no AG.
 * – Cada {@link Individuo} inclui 100 genes + conflitos já calculados. <br>
 * – Mantém tamanho máximo definido no construtor. <br>
 * – Oferece três formas de seleção: uniforme (sortearDois),
 *   competitiva (sortearDoisCompetitivo) e snapshot de leitura segura.
 */
public class Populacao {

    private final List<Individuo> individuos;
    private final int tamanho;
    private final Random rnd = new Random();

    public Populacao(int tamanhoEsperado) {
        if (tamanhoEsperado <= 0)
            throw new IllegalArgumentException("Tamanho deve ser positivo");
        this.tamanho = tamanhoEsperado;
        this.individuos = new ArrayList<>(tamanhoEsperado);
    }

    /* ------------------------------- CRUD --------------------------- */

    public void add(Individuo ind) {
        if (ind == null) throw new NullPointerException("Indivíduo nulo");
        if (individuos.size() >= tamanho)
            throw new IllegalStateException("População já cheia (" + tamanho + ")");
        individuos.add(ind);
    }

    public Individuo get(int index) { return individuos.get(index); }

    public List<Individuo> getIndividuos() { return individuos; }

    public int getTamanho() { return tamanho; }

    /** Lista imutável para leitura segura. */
    public List<Individuo> snapshot() {
        return Collections.unmodifiableList(new ArrayList<>(individuos));
    }

    /* ------------------------- Seleção – uniforme ------------------ */

    /** Sorteia dois indivíduos distintos uniformemente em toda população. */
    public Individuo[] sortearDois() {
        if (individuos.size() < 2)
            throw new IllegalStateException("Precisa de pelo menos 2 indivíduos");
        int i1 = rnd.nextInt(individuos.size());
        int i2;
        do { i2 = rnd.nextInt(individuos.size()); } while (i2 == i1);
        return new Individuo[] { individuos.get(i1), individuos.get(i2) };
    }

    /* ---------------- Seleção – Pai1 no topo 50 %, Pai2 aleatório -- */

    /**
     * Pai 1 é sorteado apenas entre os 50 % com MENOS conflitos;
     * Pai 2 é sorteado uniformemente em toda a população.
     * Garante que os dois pais sejam diferentes.
     */
    public Individuo[] sortearDoisCompetitivo() {
        if (individuos.size() < 2)
            throw new IllegalStateException("População precisa ter ≥ 2 indivíduos");

        // lista ordenada por conflitos ascendentes
        List<Individuo> ordenada = new ArrayList<>(individuos);
        ordenada.sort(Comparator.comparingInt(Individuo::getConflitos));

        int metade = ordenada.size() / 2;          // 50 %
        Individuo pai1 = ordenada.get(rnd.nextInt(metade));

        Individuo pai2;
        do { pai2 = individuos.get(rnd.nextInt(individuos.size())); }
        while (pai2 == pai1);

        return new Individuo[] { pai1, pai2 };
    }
}
