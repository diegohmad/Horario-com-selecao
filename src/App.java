/* ───────── src/App.java ───────── */
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

        /* 1. Associação professor × matéria ------------------------ */
        Map<Materia, Professor> associacoes =
                PopInicial.associarProfessoresAMaterias();

        /* 2. População inicial (50 indivíduos × 100 genes) -------- */
        Populacao populacao =
                PopInicial.gerarPopulacaoInicial(associacoes, 50);

        /* 3. Ordena pelos indivíduos com MENOS conflitos ---------- */
        populacao.getIndividuos().sort(
                Comparator.comparingInt(Individuo::getConflitos));

        /* 4. Seleciona 2 indivíduos aleatórios -------------------- */
        Individuo[] selecionados = populacao.sortearDois();

        /* 5. Gera o HTML com 3 quadros ---------------------------- */
        GeradorHTML.gerarArquivoCompleto(
                associacoes,
                populacao,
                selecionados,
                "horario.html"
        );

        System.out.println("Arquivo gerado: horario.html");
    }
}
