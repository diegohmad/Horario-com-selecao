package models;

public class Professor {
    private String nome;
    private String codigo;

    public Professor(String nome, String codigo) {
        this.nome = nome;
        this.codigo = formatarCodigo(codigo);
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
}
