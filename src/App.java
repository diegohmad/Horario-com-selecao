/* ───────── src/App.java ───────── */
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

        Map<Materia, Professor> associacoes =
                PopInicial.associarProfessoresAMaterias();

        Populacao populacao = PopInicial.gerarPopulacaoInicial(associacoes, 50);

        /* Ordena por menos conflitos (opcional, só para exibir ordenado) */
        populacao.getIndividuos().sort(
                Comparator.comparingInt(Individuo::getConflitos));

        /* Seleciona dois pais aleatórios */
        Individuo[] pais = populacao.sortearDois();

        /* Gera dois filhos via crossover de 1 ponto */
        Individuo[] filhos = Cruzador.cruzarUmPonto(pais[0], pais[1]);

        /* Exporta HTML com 4 quadros */
        GeradorHTML.gerarArquivoCompleto(
                associacoes,
                populacao,
                pais,
                filhos,          // ← novos filhos
                "horario.html"
        );

        System.out.println("Arquivo gerado: horario.html");
    }
}
