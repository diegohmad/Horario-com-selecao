/* ───────── src/generator/Cruzador.java ───────── */
package generator;

import java.util.Random;
import models.Individuo;

/**
 * Utilitário para realizar cruzamentos (crossover) entre indivíduos.
 * Implementa crossover de 1 ponto: sorteia-se uma posição de corte
 * entre 1 e 99; tudo antes desse ponto vem do Pai A e o restante do Pai B,
 * e vice-versa para o segundo filho.
 */
public final class Cruzador {

    private static final Random RND = new Random();

    private Cruzador() { }              // utilitário, não instanciável

    /**
     * Executa crossover de 1 ponto em dois pais.
     *
     * @param paiA primeiro indivíduo
     * @param paiB segundo indivíduo
     * @return array com dois novos filhos
     */
    public static Individuo[] cruzarUmPonto(Individuo paiA, Individuo paiB) {

        String[] gA = paiA.getGenes();        // cópias já protegidas na classe
        String[] gB = paiB.getGenes();

        int ponto = RND.nextInt(99) + 1;      // 1 .. 99  (evita 0 e 100)

        String[] filho1 = new String[100];
        String[] filho2 = new String[100];

        System.arraycopy(gA, 0, filho1, 0, ponto);
        System.arraycopy(gB, ponto, filho1, ponto, 100 - ponto);

        System.arraycopy(gB, 0, filho2, 0, ponto);
        System.arraycopy(gA, ponto, filho2, ponto, 100 - ponto);

        return new Individuo[] {
            new Individuo(filho1),
            new Individuo(filho2)
        };
    }
}
