package data;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import models.Materia;
import models.Professor;

public class InicializadorDados {

    public static List<Professor> criarProfessores() {
        return Arrays.asList(
            new Professor("Prof. Ana", "01"),
            new Professor("Prof. Bruno", "02"),
            new Professor("Prof. Carla", "03"),
            new Professor("Prof. David", "04"),
            new Professor("Prof. Elisa", "05"),
            new Professor("Prof. Fernando", "06"),
            new Professor("Prof. Gabriela", "07"),
            new Professor("Prof. Helena", "08"),
            new Professor("Prof. Igor", "09"),
            new Professor("Prof. Joana", "10")
        );
    }

    public static Map<String, List<Materia>> criarMateriasPorPeriodo() {
        Map<String, List<Materia>> materiasPorPeriodo = new LinkedHashMap<>();

        materiasPorPeriodo.put("1", Arrays.asList(
            new Materia("Programação Web", "01", "1"),
            new Materia("Banco de Dados I", "02", "1"),
            new Materia("Redes de Computadores I", "03", "1"),
            new Materia("Sistemas Operacionais", "04", "1"),
            new Materia("Lógica de Programação", "05", "1")
        ));

        materiasPorPeriodo.put("2", Arrays.asList(
            new Materia("Desenvolvimento Front-end", "06", "2"),
            new Materia("Banco de Dados II", "07", "2"),
            new Materia("Redes de Computadores II", "08", "2"),
            new Materia("Segurança da Informação", "09", "2"),
            new Materia("Estruturas de Dados", "10", "2")
        ));

        materiasPorPeriodo.put("3", Arrays.asList(
            new Materia("Desenvolvimento Back-end", "11", "3"),
            new Materia("Interação Humano-Computador", "12", "3"),
            new Materia("Gerência de Projetos", "13", "3"),
            new Materia("Testes de Software", "14", "3"),
            new Materia("Engenharia de Software", "15", "3")
        ));

        materiasPorPeriodo.put("4", Arrays.asList(
            new Materia("Mobile Development", "16", "4"),
            new Materia("Cloud Computing", "17", "4"),
            new Materia("Data Science", "18", "4"),
            new Materia("Internet das Coisas", "19", "4"),
            new Materia("Empreendedorismo Digital", "20", "4")
        ));

        materiasPorPeriodo.put("5", Arrays.asList(
            new Materia("Projeto de Conclusão de Curso", "21", "5"),
            new Materia("Práticas de Desenvolvimento Ágil", "22", "5"),
            new Materia("Usabilidade Web", "23", "5"),
            new Materia("Big Data", "24", "5"),
            new Materia("Tecnologias Emergentes", "25", "5")
        ));

        return materiasPorPeriodo;
    }
}
