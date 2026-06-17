package com.fuctura.dao;

import com.fuctura.database.Conexao;
import com.fuctura.models.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    public int createAluno(String nome, int idade, String email){
        String sql = "INSERT INTO alunos (nome, idade, email) VAlUES (?,?,?) RETURNING id";
        try (
            Connection con = Conexao.obterConexao();
            PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1,nome);
                ps.setInt(2,idade);
                ps.setString(3,email);

                ResultSet rs = ps.executeQuery();


                if(rs.next()){
                    return rs.getInt("id");
                }

            } catch (SQLException e){
                System.out.print("Erro ao inserir os dados");

            }
        return -1;

    }

    public static List<Aluno> buscarAlunos(int max, int offset ){
        String sql = "SELECT nome, email, matricula FROM alunos ORDER BY nome ASC LIMIT ? OFFSET ?";
        try (
                Connection con = Conexao.obterConexao();
                PreparedStatement ps = con.prepareStatement(sql)) {
            if (max < 0){
                max = 0;
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
           ps.setInt(1,max);
           ps.setInt(2,offset);

            ResultSet rs = ps.executeQuery();

            List<Aluno> listaDeAlunos = new ArrayList<>();

            while(rs.next()){
                listaDeAlunos.add(
                        new Aluno(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getBoolean("matricula")
                        )
                );
            }
        return listaDeAlunos;


        } catch (SQLException e){
            System.out.print("Erro ao inserir os dados");

        }
        return new ArrayList<>();
    }

    
}
