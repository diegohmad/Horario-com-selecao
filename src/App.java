import generator.AlgoritmoGenetico;
import generator.PopInicial;
import java.util.Map;
import models.Materia;
import models.Professor;

public class App {
    public static void main(String[] args) {
        try {
            Map<Materia, Professor> associacoes = PopInicial.associarProfessoresAMaterias();
            AlgoritmoGenetico.executar(associacoes, 50, 100, 0.05);
        } catch (Exception e) {
            e.printStackTrace(); // ou outra lógica de erro
        }
    }
}
