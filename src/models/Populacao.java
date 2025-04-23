/* ───────── src/models/Populacao.java ───────── */
package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Contêiner que armazena uma população de indivíduos do algoritmo genético.
 * <p>
 * ‣ Cada {@link Individuo} carrega 100 genes e a contagem de conflitos.<br>
 * ‣ A classe impõe um tamanho máximo (definido no construtor).<br>
 * ‣ Inclui utilitário {@code sortearDois()} para retornar dois indivíduos
 *   distintos escolhidos aleatoriamente (distribuição uniforme).
 */
public class Populacao {

    private final List<Individuo> individuos;
    private final int tamanho;

    /** gerador de números pseudo-aleatórios compartilhado pela instância */
    private final Random rnd = new Random();

    public Populacao(int tamanhoEsperado) {
        if (tamanhoEsperado <= 0)
            throw new IllegalArgumentException("Tamanho deve ser positivo.");
        this.tamanho = tamanhoEsperado;
        this.individuos = new ArrayList<>(tamanhoEsperado);
    }

    /* ------------------------------------------------------------  CRUD */

    /** Adiciona um indivíduo, respeitando o tamanho máximo. */
    public void add(Individuo ind) {
        if (ind == null) throw new NullPointerException("Indivíduo nulo");
        if (individuos.size() >= tamanho)
            throw new IllegalStateException("População já cheia (" + tamanho + ").");
        individuos.add(ind);
    }

    /** Obtém indivíduo por índice (0-based). */
    public Individuo get(int index) {
        return individuos.get(index);
    }

    /* ------------------------------------------------------  Seleção AG */

    /**
     * Sorteia dois indivíduos distintos de forma uniforme.
     *
     * @return array de tamanho 2 com os indivíduos já na ordem sorteada
     * @throws IllegalStateException se a população tiver menos de 2 indivíduos
     */
    public Individuo[] sortearDois() {
        if (individuos.size() < 2)
            throw new IllegalStateException("É necessário ter pelo menos 2 indivíduos.");

        int i1 = rnd.nextInt(individuos.size());
        int i2;
        do { i2 = rnd.nextInt(individuos.size()); } while (i2 == i1);

        return new Individuo[] { individuos.get(i1), individuos.get(i2) };
    }

    /* ---------------------------------------------------------- Getters */

    /** Lista mutável (em ordem) — altere com cuidado! */
    public List<Individuo> getIndividuos() { return individuos; }

    /** Tamanho máximo definido para a população. */
    public int getTamanho() { return tamanho; }

    /* ------------------------------------------------------ Utilidades */

    /** Devolve uma lista *imutável* com os indivíduos (para leitura segura). */
    public List<Individuo> snapshot() {
        return Collections.unmodifiableList(new ArrayList<>(individuos));
    }
}
