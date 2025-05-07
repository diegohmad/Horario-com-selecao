
import generator.Cruzador;
import generator.GeradorHTML;
import generator.PopInicial;
import java.util.Comparator;
import java.util.Map;
import models.Individuo;
import models.Materia;
import models.Populacao;
import models.Professor;

public class App {

    public static void main(String[] args) throws Exception {

        /* 1. Gera associações professor × matéria */
        Map<Materia, Professor> associacoes
                = PopInicial.associarProfessoresAMaterias();

        /* 2. População inicial (50 indivíduos × 100 genes) */
        Populacao populacao = PopInicial.gerarPopulacaoInicial(associacoes, 50);

        /* 3. Ordena por menos conflitos (só para exibir mais organizado) */
        populacao.getIndividuos()
                .sort(Comparator.comparingInt(Individuo::getConflitos));

        /* 4. Seleciona pais seguindo a nova regra:
              Pai 1 no top-50 %, Pai 2 qualquer um */
        Individuo[] pais = populacao.sortearDoisCompetitivo();

        /* 5. Gera dois filhos via crossover por períodos aleatórios */
        Individuo[] filhos = Cruzador.cruzarPorPeriodosAleatorios(pais[0], pais[1]);


        /* 6. Exporta HTML com quatro quadros (inclui filhos) */
        GeradorHTML.gerarArquivoCompleto(
                associacoes,
                populacao,
                pais, // tabela 3
                filhos, // tabela 4
                "horario.html"
        );

        System.out.println("Arquivo gerado: horario.html");
    }
}
