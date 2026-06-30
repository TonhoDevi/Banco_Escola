package com.fuctura.controller;

import com.fuctura.dao.AlunoDAO;
import com.fuctura.models.Aluno;

import java.util.List;
import java.util.Scanner;

public class AlunoController {

    private static final Scanner scan = new Scanner(System.in);

    // ─────────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────────

    /**
     * Coleta os dados do novo aluno via terminal e chama o DAO para persistir.
     */
    public static void createAluno() {
        System.out.println("\n--- CADASTRAR NOVO ALUNO ---");

        System.out.print("Nome do aluno: ");
        String nome = scan.nextLine().trim();

        int idade = lerInteiroPositivo("Idade: ");

        System.out.print("Email: ");
        String email = scan.nextLine().trim();

        int novoId = AlunoDAO.createAluno(nome, idade, email);

        if (novoId != -1) {
            System.out.println("Aluno cadastrado com sucesso! ID gerado: " + novoId);
        } else {
            System.out.println("Falha ao cadastrar o aluno.");
        }
    }

    // ─────────────────────────────────────────────
    // READ — listar todos
    // ─────────────────────────────────────────────

    /**
     * Lista alunos paginados. Exibe até 10 por vez a partir do offset informado.
     */
    public static void listAlunos() {
        System.out.println("\n--- LISTAR ALUNOS ---");

        int offset = lerInteiroNaoNegativo("A partir do registro (offset, ex: 0): ");

        List<Aluno> alunos = AlunoDAO.findAllAluno(10, offset);

        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno encontrado.");
            return;
        }

        System.out.println("\n" + alunos.size() + " aluno(s) encontrado(s):");
        alunos.forEach(System.out::println);
    }

    // ─────────────────────────────────────────────
    // READ — buscar por ID
    // ─────────────────────────────────────────────

    /**
     * Busca e exibe os dados de um aluno pelo ID informado.
     */
    public static void findAlunoById() {
        System.out.println("\n--- BUSCAR ALUNO POR ID ---");

        int id = lerInteiroPositivo("ID do aluno: ");

        Aluno aluno = AlunoDAO.findAlunoById(id);

        if (aluno == null) {
            System.out.println("Aluno com ID " + id + " não foi encontrado.");
            return;
        }

        System.out.println("\nAluno encontrado:");
        System.out.println(aluno);
    }

    // ─────────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────────

    /**
     * Busca um aluno pelo ID, exibe os valores atuais e permite atualizá-los
     * campo a campo — pressionar Enter mantém o valor existente.
     */
    public static void updateAluno() {
        System.out.println("\n--- ATUALIZAR ALUNO ---");

        int id = lerInteiroPositivo("ID do aluno que deseja atualizar: ");

        Aluno alunoExistente = AlunoDAO.findAlunoById(id);

        if (alunoExistente == null) {
            System.out.println("Aluno com ID " + id + " não foi encontrado.");
            return;
        }

        System.out.println("\nAluno encontrado! Pressione [ENTER] para manter o valor atual.");

        // --- Nome ---
        System.out.println("Nome atual: " + alunoExistente.getNome());
        System.out.print("Novo nome: ");
        String novoNome = scan.nextLine().trim();
        if (!novoNome.isEmpty()) {
            alunoExistente.setNome(novoNome);
        }

        // --- Idade ---
        System.out.println("Idade atual: " + alunoExistente.getIdade());
        System.out.print("Nova idade: ");
        String novaIdadeStr = scan.nextLine().trim();
        if (!novaIdadeStr.isEmpty()) {
            try {
                int novaIdade = Integer.parseInt(novaIdadeStr);
                if (novaIdade > 0) {
                    alunoExistente.setIdade(novaIdade);
                } else {
                    System.out.println("Idade deve ser positiva. Mantendo o valor atual.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Mantendo a idade atual.");
            }
        }

        // --- Email ---
        System.out.println("Email atual: " + alunoExistente.getEmail());
        System.out.print("Novo email: ");
        String novoEmail = scan.nextLine().trim();
        if (!novoEmail.isEmpty()) {
            alunoExistente.setEmail(novoEmail);
        }

        // --- Matrícula ---
        System.out.println("Matrícula atual: " + (alunoExistente.isMatricula() ? "Ativa" : "Inativa"));
        System.out.print("Nova matrícula (true = Ativa / false = Inativa): ");
        String novaMatriculaStr = scan.nextLine().trim();
        if (!novaMatriculaStr.isEmpty()) {
            if (novaMatriculaStr.equalsIgnoreCase("true") || novaMatriculaStr.equalsIgnoreCase("false")) {
                alunoExistente.setMatricula(Boolean.parseBoolean(novaMatriculaStr));
            } else {
                System.out.println("Valor inválido. Mantendo o status atual da matrícula.");
            }
        }

        boolean sucesso = AlunoDAO.updateAluno(alunoExistente, id);

        if (sucesso) {
            System.out.println("Aluno atualizado com sucesso!");
        } else {
            System.out.println("Falha ao atualizar o aluno.");
        }
    }

    // ─────────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────────

    /**
     * Exibe os dados do aluno e pede confirmação antes de deletar.
     */
    public static void deleteAluno() {
        System.out.println("\n--- DELETAR ALUNO ---");

        int id = lerInteiroPositivo("ID do aluno que deseja deletar: ");

        Aluno aluno = AlunoDAO.findAlunoById(id);

        if (aluno == null) {
            System.out.println("Aluno com ID " + id + " não foi encontrado.");
            return;
        }

        System.out.println("\nAluno encontrado:");
        System.out.println(aluno);
        System.out.print("Confirma a exclusão? (sim/não): ");
        String confirmacao = scan.nextLine().trim().toLowerCase();

        if (!confirmacao.equals("sim")) {
            System.out.println("Exclusão cancelada.");
            return;
        }

        boolean sucesso = AlunoDAO.deleteAlunoById(id);

        if (sucesso) {
            System.out.println("Aluno deletado com sucesso!");
        } else {
            System.out.println("Falha ao deletar o aluno.");
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