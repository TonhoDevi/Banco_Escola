package com.fuctura.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

public class DataBaseSeeder {

    // ─────────────────────────────────────────────
    // Ponto de entrada principal
    // ─────────────────────────────────────────────

    /**
     * Cria as tabelas (se não existirem) e popula com dados iniciais.
     * Seguro para chamar toda vez que a aplicação iniciar.
     */
    public static void inicializar() {
        System.out.println("\n=== INICIALIZANDO BANCO DE DADOS ===");
        criarTabelaCursos();
        criarTabelaAlunos();
        popularCursos();
        popularAlunos();
        System.out.println("=== BANCO DE DADOS PRONTO ===\n");
    }

    // ─────────────────────────────────────────────
    // Criação de tabelas
    // ─────────────────────────────────────────────

    private static void criarTabelaCursos() {
        String sql = """
                CREATE TABLE IF NOT EXISTS cursos (
                    id            SERIAL PRIMARY KEY,
                    nome          VARCHAR(100) NOT NULL,
                    descricao     TEXT,
                    carga_horaria INT NOT NULL DEFAULT 0
                )
                """;
        executarDDL(sql, "cursos");
    }

    private static void criarTabelaAlunos() {
        String sql = """
                CREATE TABLE IF NOT EXISTS alunos (
                    id        SERIAL PRIMARY KEY,
                    nome      VARCHAR(100) NOT NULL,
                    idade     INT NOT NULL,
                    email     VARCHAR(150) NOT NULL,
                    matricula BOOLEAN NOT NULL DEFAULT FALSE,
                    curso_id  INT,
                    CONSTRAINT fk_aluno_curso
                        FOREIGN KEY (curso_id)
                        REFERENCES cursos(id)
                        ON DELETE SET NULL
                )
                """;
        executarDDL(sql, "alunos");
    }

    private static void executarDDL(String sql, String nomeTabela) {
        try (Connection con = Conexao.obterConexao();
             Statement stmt = con.createStatement()) {

            stmt.execute(sql);
            System.out.println("[OK] Tabela '" + nomeTabela + "' verificada.");

        } catch (SQLException e) {
            System.out.println("[ERRO] Falha ao criar tabela '" + nomeTabela + "': " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    // Seed de cursos
    // ─────────────────────────────────────────────

    private static void popularCursos() {
        if (tabelaTemDados("cursos")) {
            System.out.println("[SKIP] Tabela 'cursos' já possui dados. Seed ignorado.");
            return;
        }

        String sql = "INSERT INTO cursos (nome, descricao, carga_horaria) VALUES (?, ?, ?)";

        Object[][] cursos = {
                { "Java 1",   "Fundamentos de Java: variáveis, tipos, controle de fluxo e arrays.",          40 },
                { "Java 2",   "Orientação a objetos: classes, herança, polimorfismo e interfaces.",           60 },
                { "Java 3",   "Java avançado: coleções, generics, lambdas e streams.",                       60 },
                { "Java 4",   "Java para back-end: JDBC, APIs REST com Spring Boot e testes unitários.",      80 },
                { "Python 1", "Fundamentos de Python: sintaxe, estruturas de dados e funções.",               40 },
                { "Python 2", "Python orientado a objetos: classes, herança e módulos.",                      60 },
                { "Python 3", "Python avançado: decorators, generators, context managers e async.",           60 },
                { "Python 4", "Python para dados: NumPy, Pandas, visualização e introdução ao ML.",           80 },
        };

        try (Connection con = Conexao.obterConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            for (Object[] curso : cursos) {
                stmt.setString(1, (String) curso[0]);
                stmt.setString(2, (String) curso[1]);
                stmt.setInt(3, (int) curso[2]);
                stmt.addBatch();
            }

            int[] resultado = stmt.executeBatch();
            System.out.println("[OK] " + resultado.length + " cursos inseridos.");

        } catch (SQLException e) {
            System.out.println("[ERRO] Falha ao inserir cursos: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    // Seed de alunos
    // ─────────────────────────────────────────────

    private static void popularAlunos() {
        if (tabelaTemDados("alunos")) {
            System.out.println("[SKIP] Tabela 'alunos' já possui dados. Seed ignorado.");
            return;
        }

        // Busca os IDs reais dos cursos recém-criados
        int[] cursoIds = buscarIdsDeCursos();
        if (cursoIds.length == 0) {
            System.out.println("[ERRO] Nenhum curso encontrado para vincular aos alunos.");
            return;
        }

        String sql = "INSERT INTO alunos (nome, idade, email, matricula, curso_id) VALUES (?, ?, ?, ?, ?)";

        String[] nomes = {
                "Ana Lima",       "Bruno Souza",    "Carla Mendes",   "Diego Rocha",
                "Elena Costa",    "Felipe Alves",   "Gabriela Nunes", "Henrique Dias",
                "Isabela Ferreira","João Martins",  "Karina Oliveira","Lucas Pereira",
                "Mariana Santos", "Nicolas Gomes",  "Olivia Ribeiro", "Paulo Nascimento",
                "Quincy Torres",  "Renata Azevedo", "Sandro Barbosa", "Tatiane Cardoso",
                "Ulisses Melo",   "Vanessa Freitas","Wagner Castro",  "Ximena Pinto",
                "Yasmin Correia", "Zeca Monteiro",  "Alice Ramos",    "Bernardo Cruz",
                "Camila Teixeira","Daniel Moraes"
        };

        Random random = new Random(42); // seed fixo para resultados reproduzíveis

        try (Connection con = Conexao.obterConexao();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            for (int i = 0; i < nomes.length; i++) {
                String nome  = nomes[i];
                int idade    = 17 + random.nextInt(14);           // 17 a 30 anos
                String email = gerarEmail(nome, i + 1);
                boolean matricula = random.nextBoolean();
                int cursoId  = cursoIds[random.nextInt(cursoIds.length)];

                stmt.setString(1, nome);
                stmt.setInt(2, idade);
                stmt.setString(3, email);
                stmt.setBoolean(4, matricula);
                stmt.setInt(5, cursoId);
                stmt.addBatch();
            }

            int[] resultado = stmt.executeBatch();
            System.out.println("[OK] " + resultado.length + " alunos inseridos.");

        } catch (SQLException e) {
            System.out.println("[ERRO] Falha ao inserir alunos: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    // Helpers
    // ─────────────────────────────────────────────

    /**
     * Verifica se a tabela já tem pelo menos uma linha.
     * Usado para evitar inserções duplicadas a cada inicialização.
     */
    private static boolean tabelaTemDados(String nomeTabela) {
        String sql = "SELECT EXISTS (SELECT 1 FROM " + nomeTabela + " LIMIT 1)";
        try (Connection con = Conexao.obterConexao();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getBoolean(1);
            }

        } catch (SQLException e) {
            System.out.println("[ERRO] Falha ao verificar tabela '" + nomeTabela + "': " + e.getMessage());
        }
        return false;
    }

    /**
     * Busca todos os IDs presentes na tabela cursos para vincular aos alunos.
     */
    private static int[] buscarIdsDeCursos() {
        String sql = "SELECT id FROM cursos ORDER BY id";
        try (Connection con = Conexao.obterConexao();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            java.util.List<Integer> ids = new java.util.ArrayList<>();
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
            return ids.stream().mapToInt(Integer::intValue).toArray();

        } catch (SQLException e) {
            System.out.println("[ERRO] Falha ao buscar IDs dos cursos: " + e.getMessage());
        }
        return new int[0];
    }

    /**
     * Gera um email no formato primeiro.ultimo{n}@fuctura.com
     * sem acentos e em letras minúsculas.
     */
    private static String gerarEmail(String nomeCompleto, int n) {
        String normalizado = nomeCompleto.toLowerCase()
                .replace("á","a").replace("à","a").replace("ã","a").replace("â","a")
                .replace("é","e").replace("ê","e")
                .replace("í","i")
                .replace("ó","o").replace("ô","o").replace("õ","o")
                .replace("ú","u").replace("ü","u")
                .replace("ç","c");

        String[] partes = normalizado.split(" ");
        String primeiro = partes[0];
        String ultimo   = partes.length > 1 ? partes[partes.length - 1] : String.valueOf(n);

        return primeiro + "." + ultimo + n + "@fuctura.com";
    }
}