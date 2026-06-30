package com.fuctura.dao;

import com.fuctura.database.Conexao;
import com.fuctura.models.Curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    /**
     * Insere um novo curso no banco e retorna o ID gerado.
     * Retorna -1 em caso de erro.
     */
    public static int createCurso(String nome, String descricao, int cargaHoraria) {
        String sql = "INSERT INTO cursos (nome, descricao, carga_horaria) VALUES (?, ?, ?) RETURNING id";
        try (
                Connection con = Conexao.obterConexao();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.setInt(3, cargaHoraria);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir curso: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Retorna uma lista paginada de cursos, ordenada por nome.
     */
    public static List<Curso> findAllCursos(int max, int offset) {
        String sql = "SELECT id, nome, descricao, carga_horaria FROM cursos ORDER BY nome ASC LIMIT ? OFFSET ?";
        try (
                Connection con = Conexao.obterConexao();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            if (max < 1)    max    = 1;
            if (max > 100)  max    = 100;
            if (offset < 0) offset = 0;

            stmt.setInt(1, max);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            List<Curso> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new Curso(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("carga_horaria")
                ));
            }
            return lista;

        } catch (SQLException e) {
            System.out.println("Erro ao buscar cursos: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Busca um curso específico pelo ID.
     * Retorna null se não encontrado.
     */
    public static Curso findCursoById(int id) {
        String sql = "SELECT id, nome, descricao, carga_horaria FROM cursos WHERE id = ?";
        try (
                Connection con = Conexao.obterConexao();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Curso(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("carga_horaria")
                );
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar curso por ID: " + e.getMessage());
        }
        return null;
    }

    /**
     * Atualiza os dados de um curso existente.
     * Retorna true se pelo menos uma linha foi afetada.
     */
    public static boolean updateCurso(Curso curso, int cursoId) {
        String sql = "UPDATE cursos SET nome = ?, descricao = ?, carga_horaria = ? WHERE id = ?";
        try (
                Connection con = Conexao.obterConexao();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getDescricao());
            stmt.setInt(3, curso.getCargaHoraria());
            stmt.setInt(4, cursoId);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar curso: " + e.getMessage());
        }
        return false;
    }

    /**
     * Deleta um curso pelo ID.
     * Atenção: alunos vinculados terão curso_id definido como NULL (ON DELETE SET NULL).
     * Retorna true se pelo menos uma linha foi afetada.
     */
    public static boolean deleteCursoById(int id) {
        String sql = "DELETE FROM cursos WHERE id = ?";
        try (
                Connection con = Conexao.obterConexao();
                PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao deletar curso: " + e.getMessage());
        }
        return false;
    }
}