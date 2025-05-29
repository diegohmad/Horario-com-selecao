package generator;

import java.util.Random;
import models.Individuo;

public class Mutador {

    private static final Random RND = new Random();

    public static Individuo aplicar(Individuo ind, double taxa) {
        String[] genes = ind.getGenes(); // faz clone interno
        final int TAM_PERIODO = 20;

        for (int p = 0; p < 5; p++) {
            if (RND.nextDouble() < taxa) {
                int base = p * TAM_PERIODO;
                int i = base + RND.nextInt(TAM_PERIODO);
                int j;
                do {
                    j = base + RND.nextInt(TAM_PERIODO);
                } while (j == i);

                String tmp = genes[i];
                genes[i] = genes[j];
                genes[j] = tmp;
            }
        }

        return new Individuo(genes); // recalcula conflitos
    }
}
