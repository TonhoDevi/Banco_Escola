package com.fuctura.models;

public class Aluno {

    private int    id;
    private String nome;
    private String email;
    private boolean matricula;

    // Construtor completo (usado ao buscar do banco)
    public Aluno(int id, String nome, String email, boolean matricula) {
        this.id        = id;
        this.nome      = nome;
        this.email     = email;
        this.matricula = matricula;
    }

    // Construtor sem ID (usado ao inserir — banco gera o ID com SERIAL)
    public Aluno(String nome, String email, boolean matricula) {
        this(0, nome, email, matricula);
    }

    // Getters
    public int    getId()        { return id; }
    public String getNome()      { return nome; }
    public String getEmail()     { return email; }
    public boolean getMatricula() { return matricula; }

    // Setters
    public void setId(int id)            { this.id = id; }
    public void setNome(String nome)     { this.nome = nome; }
    public void setEmail(String email)   { this.email = email; }
    public void setMatricula(boolean mat) { this.matricula = mat; }

    @Override
    public String toString() {
        return String.format("Aluno{id=%d, nome='%s', email='%s', matricula=%s} \n",
            id, nome, email, matricula);
    }
}
