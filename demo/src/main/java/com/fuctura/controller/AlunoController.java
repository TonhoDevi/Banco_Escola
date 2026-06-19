package com.fuctura.controller;

import com.fuctura.dao.AlunoDAO;
import com.fuctura.models.Aluno;

import java.util.Scanner;

public class AlunoController {


    public static void updateAluno(){
        Scanner scan = new Scanner(System.in);

        // ... (Sua rotina antiga de inserção de aluno se mantém aqui) ...

        System.out.println("\n--- INÍCIO DA ROTINA DE ATUALIZAÇÃO ---");

        // 1. Pergunta o ID do aluno
        System.out.println("Digite o ID do aluno que deseja atualizar:");
        int idProcurado = scan.nextInt();
        scan.nextLine(); // CRUCIAL: Limpa o buffer do teclado para o nextLine() funcionar adiante!

        // 2. Faz a pesquisa findAlunoById
        Aluno alunoExistente = AlunoDAO.findAlunoById(idProcurado);

        // 3. Se não existir, dá um return (para o método main, encerrando a execução)
        if (alunoExistente == null) {
            System.out.println("Aluno com ID " + idProcurado + " não foi encontrado!");
            return;
        }

        System.out.println("\nAluno encontrado! Se não quiser alterar o campo, apenas aperte [ENTER].");

        // 4. Ponto por ponto: mostra o atual e pergunta se quer trocar

        // --- Atualizando o NOME ---
        System.out.println("Nome atual: " + alunoExistente.getNome());
        System.out.print("Novo nome: ");
        String novoNome = scan.nextLine();
        if (!novoNome.trim().isEmpty()) {
            alunoExistente.setNome(novoNome);
        }

        // --- Atualizando a IDADE ---
        System.out.println("Idade atual: " + alunoExistente.getIdade());
        System.out.print("Nova idade: ");
        String novaIdadeStr = scan.nextLine();
        if (!novaIdadeStr.trim().isEmpty()) {
            try {
                int novaIdade = Integer.parseInt(novaIdadeStr);
                alunoExistente.setIdade(novaIdade);
            } catch (NumberFormatException e) {
                System.out.println("Idade inválida informada. Mantendo a idade antiga.");
            }
        }

        // --- Atualizando o EMAIL ---
        System.out.println("Email atual: " + alunoExistente.getEmail());
        System.out.print("Novo email: ");
        String novoEmail = scan.nextLine();
        if (!novoEmail.trim().isEmpty()) {
            alunoExistente.setEmail(novoEmail);
        }

        // --- Atualizando a MATRÍCULA ---
        System.out.println("Status da matrícula atual: " + (alunoExistente.isMatricula() ? "Ativa" : "Inativa"));
        System.out.print("Nova matrícula (digite 'true' para Ativa ou 'false' para Inativa): ");
        String novaMatriculaStr = scan.nextLine();
        if (!novaMatriculaStr.trim().isEmpty()) {
            boolean novaMatricula = Boolean.parseBoolean(novaMatriculaStr);
            alunoExistente.setMatricula(novaMatricula);
        }

        // 5. Envia para o updateAluno passando o objeto modificado e o ID separadamente
        System.out.println("\nEnviando atualizações para o banco de dados...");
        boolean sucesso = AlunoDAO.updateAluno(alunoExistente, idProcurado);

        if (sucesso) {
            System.out.println("Aluno atualizado com sucesso!");
        } else {
            System.out.println("Falha ao atualizar o aluno no banco de dados.");
        }
    }
}
