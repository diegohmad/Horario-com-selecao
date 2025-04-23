/* ───────── src/generator/OrdenadorResultados.java ───────── */
package generator;

import generator.AvaliadorAG.IndividuoFitness;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Reordena a população avaliando o número de conflitos.
 * Mantém cada indivíduo intacto: só muda a posição
 * da linha na tabela.
 */
public final class OrdenadorResultados {

    private OrdenadorResultados() { }      // utilitário, não instanciável

    public static List<String[][]> ordenarPorMenosConflitos(
            List<String[][]> populacao) {

        /* Avalia e já devolve em ordem decrescente de fitness
           (fitness = –conflitos → quanto maior, menos conflitos). */
        List<IndividuoFitness> avaliados = AvaliadorAG.avaliar(populacao);

        /* Converte de volta para a lista de indivíduos,
           preservando a ordem já classificada. */
        return avaliados.stream()
                        .map(indFit -> indFit.individuo)
                        .collect(Collectors.toList());
    }
}
