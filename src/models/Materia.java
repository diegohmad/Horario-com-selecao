package models;

public class Materia {
    private String nome;
    private String codigo;
    private String periodo;

    public Materia(String nome, String codigo, String periodo) {
        this.nome = nome;
        this.codigo = formatarCodigo(codigo);
        this.periodo = periodo;
    }

    private String formatarCodigo(String codigo) {
        return String.format("%02d", Integer.parseInt(codigo));
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getPeriodo() {
        return periodo;
    }
}
