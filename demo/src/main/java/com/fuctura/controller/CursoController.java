package com.fuctura.controller;

import com.fuctura.dao.CursoDAO;
import com.fuctura.models.Curso;

import java.util.List;
import java.util.Scanner;

public class CursoController {

    private static final Scanner scan = new Scanner(System.in);

    // ─────────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────────

    /**
     * Coleta os dados do novo curso via terminal e chama o DAO para persistir.
     */
    public static void createCurso() {
        System.out.println("\n--- CADASTRAR NOVO CURSO ---");

        System.out.print("Nome do curso: ");
        String nome = scan.nextLine().trim();

        System.out.print("Descrição: ");
        String descricao = scan.nextLine().trim();

        int cargaHoraria = lerInteiroPositivo("Carga horária (horas): ");

        int novoId = CursoDAO.createCurso(nome, descricao, cargaHoraria);

        if (novoId != -1) {
            System.out.println("Curso cadastrado com sucesso! ID gerado: " + novoId);
        } else {
            System.out.println("Falha ao cadastrar o curso.");
        }
    }

    // ─────────────────────────────────────────────
    // READ — listar todos
    // ─────────────────────────────────────────────

    /**
     * Lista cursos paginados. Exibe até 10 por vez a partir do offset informado.
     */
    public static void listCursos() {
        System.out.println("\n--- LISTAR CURSOS ---");

        int offset = lerInteiroNaoNegativo("A partir do registro (offset, ex: 0): ");

        List<Curso> cursos = CursoDAO.findAllCursos(10, offset);

        if (cursos.isEmpty()) {
            System.out.println("Nenhum curso encontrado.");
            return;
        }

        System.out.println("\n" + cursos.size() + " curso(s) encontrado(s):");
        cursos.forEach(System.out::println);
    }

    // ─────────────────────────────────────────────
    // READ — buscar por ID
    // ─────────────────────────────────────────────

    /**
     * Busca e exibe os dados de um curso pelo ID informado.
     */
    public static void findCursoById() {
        System.out.println("\n--- BUSCAR CURSO POR ID ---");

        int id = lerInteiroPositivo("ID do curso: ");

        Curso curso = CursoDAO.findCursoById(id);

        if (curso == null) {
            System.out.println("Curso com ID " + id + " não foi encontrado.");
            return;
        }

        System.out.println("\nCurso encontrado:");
        System.out.println(curso);
    }

    // ─────────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────────

    /**
     * Busca um curso pelo ID, exibe os valores atuais e permite atualizá-los
     * campo a campo — pressionar Enter mantém o valor existente.
     */
    public static void updateCurso() {
        System.out.println("\n--- ATUALIZAR CURSO ---");

        int id = lerInteiroPositivo("ID do curso que deseja atualizar: ");

        Curso cursoExistente = CursoDAO.findCursoById(id);

        if (cursoExistente == null) {
            System.out.println("Curso com ID " + id + " não foi encontrado.");
            return;
        }

        System.out.println("\nCurso encontrado! Pressione [ENTER] para manter o valor atual.");

        // --- Nome ---
        System.out.println("Nome atual: " + cursoExistente.getNome());
        System.out.print("Novo nome: ");
        String novoNome = scan.nextLine().trim();
        if (!novoNome.isEmpty()) {
            cursoExistente.setNome(novoNome);
        }

        // --- Descrição ---
        System.out.println("Descrição atual: " + cursoExistente.getDescricao());
        System.out.print("Nova descrição: ");
        String novaDescricao = scan.nextLine().trim();
        if (!novaDescricao.isEmpty()) {
            cursoExistente.setDescricao(novaDescricao);
        }

        // --- Carga horária ---
        System.out.println("Carga horária atual: " + cursoExistente.getCargaHoraria() + "h");
        System.out.print("Nova carga horária: ");
        String novaCargaStr = scan.nextLine().trim();
        if (!novaCargaStr.isEmpty()) {
            try {
                int novaCarga = Integer.parseInt(novaCargaStr);
                if (novaCarga > 0) {
                    cursoExistente.setCargaHoraria(novaCarga);
                } else {
                    System.out.println("Carga horária deve ser positiva. Mantendo o valor atual.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Mantendo a carga horária atual.");
            }
        }

        boolean sucesso = CursoDAO.updateCurso(cursoExistente, id);

        if (sucesso) {
            System.out.println("Curso atualizado com sucesso!");
        } else {
            System.out.println("Falha ao atualizar o curso.");
        }
    }

    // ─────────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────────

    /**
     * Exibe os dados do curso e pede confirmação antes de deletar.
     * Alunos vinculados terão curso_id definido como NULL automaticamente pelo banco.
     */
    public static void deleteCurso() {
        System.out.println("\n--- DELETAR CURSO ---");

        int id = lerInteiroPositivo("ID do curso que deseja deletar: ");

        Curso curso = CursoDAO.findCursoById(id);

        if (curso == null) {
            System.out.println("Curso com ID " + id + " não foi encontrado.");
            return;
        }

        System.out.println("\nCurso encontrado:");
        System.out.println(curso);
        System.out.println("ATENÇÃO: alunos matriculados neste curso terão o vínculo removido.");
        System.out.print("Confirma a exclusão? (sim/não): ");
        String confirmacao = scan.nextLine().trim().toLowerCase();

        if (!confirmacao.equals("sim")) {
            System.out.println("Exclusão cancelada.");
            return;
        }

        boolean sucesso = CursoDAO.deleteCursoById(id);

        if (sucesso) {
            System.out.println("Curso deletado com sucesso!");
        } else {
            System.out.println("Falha ao deletar o curso.");
        }
    }

    // ─────────────────────────────────────────────
    // Helpers privados de leitura segura
    // ─────────────────────────────────────────────

    /**
     * Lê um inteiro > 0 do terminal, repetindo enquanto o valor for inválido.
     */
    private static int lerInteiroPositivo(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scan.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor > 0) return valor;
                System.out.println("O valor deve ser maior que zero. Tente novamente.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    /**
     * Lê um inteiro >= 0 do terminal, repetindo enquanto o valor for inválido.
     */
    private static int lerInteiroNaoNegativo(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scan.nextLine().trim();
            try {
                int valor = Integer.parseInt(entrada);
                if (valor >= 0) return valor;
                System.out.println("O valor não pode ser negativo. Tente novamente.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite um número inteiro.");
            }
        }
    }
}