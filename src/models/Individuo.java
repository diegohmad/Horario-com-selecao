/* ───────── src/models/Individuo.java ───────── */
package models;

public class Individuo {

    /**
     * 100 genes, cada gene = “códProf + códMat”
     */
    private final String[] genes;

    /**
     * conflitos calculados na criação
     */
    private final int conflitos;

    public Individuo(String[] genes) {
        if (genes.length != 100) {
            throw new IllegalArgumentException("Precisam ser 100 genes.");
        }
        this.genes = genes.clone();
        this.conflitos = calcularConflitos();
    }

    /* ---------- dentro da classe Individuo ---------- */
    private int calcularConflitos() {
        final int PERIODOS = 5, SLOTS = 20;
        int conflitos = 0;

        for (int slot = 0; slot < SLOTS; slot++) {
            java.util.Map<String, Integer> contagem = new java.util.HashMap<>();

            /* conta quantas vezes cada professor aparece nesse slot */
            for (int per = 0; per < PERIODOS; per++) {
                String prof = genes[per * SLOTS + slot].substring(0, 2);
                contagem.merge(prof, 1, Integer::sum);
            }

            /* se um professor aparece ≥ 2 vezes,
           todos os registros dele viram conflito (ex. 3 duplicatas = 3 erros) */
            for (int qnt : contagem.values()) {
                if (qnt >= 2) {
                    conflitos += qnt;
                }
            }
        }
        return conflitos;
    }


    /* ---------- getters ---------- */
    public String[] getGenes() {
        return genes.clone();
    }

    public int getConflitos() {
        return conflitos;
    }
}
