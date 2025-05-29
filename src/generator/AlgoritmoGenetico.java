package generator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import models.Geracao;
import models.Individuo;
import models.Materia;
import models.Populacao;
import models.Professor;

/**
 * Classe utilitária que executa o algoritmo genético completo.
 */
public class AlgoritmoGenetico {

    private static final List<Geracao> historico = new ArrayList<>();

    public static List<Geracao> getHistorico() {
        return List.copyOf(historico);
    }

    public static Individuo executar(Map<Materia, Professor> associacoes,
            int tamanhoPopulacao,
            int maxGeracoes,
            double taxaMutacao) throws Exception {

        // 1. Gera população inicial
        Populacao populacao = PopInicial.gerarPopulacaoInicial(associacoes, tamanhoPopulacao);

        // 2. Ordena por conflitos
        List<Individuo> individuos = populacao.getIndividuos();
        individuos.sort(Comparator.comparingInt(Individuo::getConflitos));

        // 3. Loop principal do AG
        Individuo melhorGlobal = individuos.get(0);

        for (int geracao = 1; geracao <= maxGeracoes; geracao++) {
            List<Individuo> novaGeracao = new ArrayList<>();
            List<Individuo> paisDaGeracao = new ArrayList<>();
            List<Individuo> filhosDaGeracao = new ArrayList<>();

            while (novaGeracao.size() < tamanhoPopulacao) {
                Individuo[] pais = populacao.sortearDoisCompetitivo();
                Individuo[] filhos = Cruzador.cruzarPorPeriodosAleatorios(pais[0], pais[1]);

                for (Individuo filho : filhos) {
                    Individuo mutado = Mutador.aplicar(filho, taxaMutacao);
                    novaGeracao.add(mutado);
                    paisDaGeracao.add(pais[0]);
                    filhosDaGeracao.add(mutado);

                    if (mutado.getConflitos() < melhorGlobal.getConflitos()) {
                        melhorGlobal = mutado;
                    }

                    if (novaGeracao.size() == tamanhoPopulacao) {
                        break;
                    }
                }
            }

            // Atualiza a população
            individuos.clear();
            individuos.addAll(novaGeracao);
            individuos.sort(Comparator.comparingInt(Individuo::getConflitos));

            // Salva a geração no histórico
            historico.add(new Geracao(geracao, novaGeracao,
                    paisDaGeracao.toArray(new Individuo[0]),
                    filhosDaGeracao.toArray(new Individuo[0])));
        }

        // 4. Gera relatório final
        GeradorHTML.gerarArquivoCompleto(
                associacoes,
                populacao,
                new Individuo[]{},
                new Individuo[]{melhorGlobal},
                "horario_final.html"
        );

        return melhorGlobal;
    }
}
