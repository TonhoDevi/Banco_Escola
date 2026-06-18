package com.fuctura.models;

public class Aluno {

    private int id;
    private String nome;
    private int idade;
    private String email;
    private boolean matricula;

    // Construtor completo (usado ao buscar do banco)
    public Aluno(int id, String nome, int idade, String email, boolean matricula) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.matricula = matricula;
    }

    // Construtor sem ID (usado ao inserir — banco gera o ID com SERIAL)
    public Aluno(String nome, int idade, String email, boolean matricula) {
        this(0, nome, idade, email, matricula);
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getEmail() {
        return email;
    }

    // Padrão Java para booleans utiliza "is" em vez de "get"
    public boolean isMatricula() {
        return matricula;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMatricula(boolean matricula) {
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        return String.format("Aluno{id=%d, nome='%s', idade=%d, email='%s', matricula=%s} \n",
                id, nome, idade, email, matricula);
    }
}