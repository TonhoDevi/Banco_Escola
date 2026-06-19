package com.fuctura.dao;

import com.fuctura.database.Conexao;
import com.fuctura.models.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    public static int createAluno(String nome, int idade, String email){
        String sql = "INSERT INTO alunos (nome, idade, email) VALUES (?,?,?) RETURNING id";
        try (
                Connection con = Conexao.obterConexao();
                PreparedStatement argumentoSQL = con.prepareStatement(sql)) {

            argumentoSQL.setString(1,nome);
            argumentoSQL.setInt(2,idade);
            argumentoSQL.setString(3,email);

            ResultSet tabelaResult = argumentoSQL.executeQuery();

            if(tabelaResult.next()){
                return tabelaResult.getInt("id");
            }

        } catch (SQLException e){
            System.out.print("Erro ao inserir os dados: " + e.getMessage());
        }
        return -1;
    }

    public static List<Aluno> findAllAluno(int max, int offset ){
        String sql = "SELECT id, nome, idade, email, matricula FROM alunos ORDER BY nome ASC LIMIT ? OFFSET ?";
        try (
                Connection con = Conexao.obterConexao();
                PreparedStatement argumentoSQL = con.prepareStatement(sql)) {

            if (max < 1) max = 1;
            if (max > 100) max = 100;
            if (offset < 0) offset = 0;
            if (offset > 100) offset = 100;

            argumentoSQL.setInt(1,max);
            argumentoSQL.setInt(2,offset);

            ResultSet tabelaResult = argumentoSQL.executeQuery();

            List<Aluno> listaDeAlunos = new ArrayList<>();

            while(tabelaResult.next()){
                // Ajuste aqui caso seu construtor de Aluno também receba o ID
                listaDeAlunos.add(
                        new Aluno(
                                tabelaResult.getInt("id"),
                                tabelaResult.getString("nome"),
                                tabelaResult.getInt("idade"),
                                tabelaResult.getString("email"),
                                tabelaResult.getBoolean("matricula")
                        )
                );
            }
            return listaDeAlunos;

        } catch (SQLException e){
            System.out.print("Erro ao buscar os dados: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Atualiza os dados de um aluno recebendo apenas o objeto Aluno como parâmetro.
     */
    public static boolean updateAluno(Aluno aluno, int alunoID) {
        String sql = "UPDATE alunos SET nome = ?, idade = ?, email = ?, matricula = ? WHERE id = ?";
        try (
                Connection con = Conexao.obterConexao();
                PreparedStatement argumentoSQL = con.prepareStatement(sql)) {

            argumentoSQL.setString(1, aluno.getNome());
            argumentoSQL.setInt(2, aluno.getIdade());
            argumentoSQL.setString(3, aluno.getEmail());
            argumentoSQL.setBoolean(4, aluno.isMatricula()); // ou getMatricula() dependendo do seu modelo
            argumentoSQL.setInt(5, alunoID);

            argumentoSQL.executeQuery();
            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o aluno: " + e.getMessage());
        }
        return false;
    }

    /**
     * Deleta um aluno do banco de dados utilizando o ID.
     */
    public static boolean deleteAlunoById(int id) {
        String sql = "DELETE FROM alunos WHERE id = ?";
        try (
                Connection con = Conexao.obterConexao();
                PreparedStatement argumentoSQL = con.prepareStatement(sql)) {

            argumentoSQL.setInt(1, id);

            ResultSet tabelaResult = argumentoSQL.executeQuery();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar o aluno: " + e.getMessage());
        }
        return false;
    }

    /**
     * Busca um aluno específico pelo seu ID.
     */
    public static Aluno findAlunoById(int id) {
        String sql = "SELECT id, nome, idade, email, matricula FROM alunos WHERE id = ?";
        try (
                Connection con = Conexao.obterConexao();
                PreparedStatement argumentoSQL = con.prepareStatement(sql)) {

            argumentoSQL.setInt(1, id);
            ResultSet tabelaResult = argumentoSQL.executeQuery();

            if (tabelaResult.next()) {
                return new Aluno(
                        tabelaResult.getInt("id"),
                        tabelaResult.getString("nome"),
                        tabelaResult.getInt("idade"),
                        tabelaResult.getString("email"),
                        tabelaResult.getBoolean("matricula")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar aluno por ID: " + e.getMessage());
        }
        return null; // Retorna null caso não encontre o aluno
    }
}