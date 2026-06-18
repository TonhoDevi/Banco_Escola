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
        String sql = "INSERT INTO alunos (nome, idade, email) VAlUES (?,?,?) RETURNING id";
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
                System.out.print("Erro ao inserir os dados");

            }
        return -1;

    }

    public static List<Aluno> buscarAlunos(int max, int offset ){
        String sql = "SELECT nome, idade, email, matricula FROM alunos ORDER BY nome ASC LIMIT ? OFFSET ?";
        try (
                Connection con = Conexao.obterConexao();
                PreparedStatement argumentoSQL = con.prepareStatement(sql)) {

            if (max < 1){
                max = 1;
            }
            if (max > 100){
                max = 100;
            }
            if (offset<0){
                offset = 0;
            }
            if (offset > 100){
                offset = 100;
            }
            argumentoSQL.setInt(1,max);
            argumentoSQL.setInt(2,offset);

            ResultSet tabelaResult = argumentoSQL.executeQuery();

            List<Aluno> listaDeAlunos = new ArrayList<>();

            while(tabelaResult.next()){
                listaDeAlunos.add(
                        new Aluno(
                                tabelaResult.getString("nome"),
                                tabelaResult.getInt("idade"),
                                tabelaResult.getString("email"),
                                tabelaResult.getBoolean("matricula")
                        )
                );
            }
        return listaDeAlunos;


        } catch (SQLException e){
            System.out.print("Erro ao inserir os dados");
            e.printStackTrace();

        }
        return new ArrayList<>();
    }
}
