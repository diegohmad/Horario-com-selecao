package models;

import java.util.List;

public class Geracao {

    private final int numero;
    private final List<Individuo> populacao;
    private final Individuo[] paisSelecionados;
    private final Individuo[] filhosGerados;
    private final Individuo melhorIndividuo;
    private final double mediaConflitos;

    public Geracao(int numero,
            List<Individuo> populacao,
            Individuo[] paisSelecionados,
            Individuo[] filhosGerados) {

        this.numero = numero;
        this.populacao = List.copyOf(populacao);
        this.paisSelecionados = paisSelecionados.clone();
        this.filhosGerados = filhosGerados.clone();
        this.melhorIndividuo = encontrarMelhor(populacao);
        this.mediaConflitos = calcularMedia(populacao);
    }

    private Individuo encontrarMelhor(List<Individuo> lista) {
        return lista.stream()
                .min((a, b) -> Integer.compare(a.getConflitos(), b.getConflitos()))
                .orElseThrow();
    }

    private double calcularMedia(List<Individuo> lista) {
        return lista.stream().mapToInt(Individuo::getConflitos).average().orElse(0.0);
    }

    public int getNumero() {
        return numero;
    }

    public List<Individuo> getPopulacao() {
        return populacao;
    }

    public Individuo[] getPaisSelecionados() {
        return paisSelecionados.clone();
    }

    public Individuo[] getFilhosGerados() {
        return filhosGerados.clone();
    }

    public Individuo getMelhorIndividuo() {
        return melhorIndividuo;
    }

    public double getMediaConflitos() {
        return mediaConflitos;
    }
}