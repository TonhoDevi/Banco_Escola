package com.fuctura;

import com.fuctura.controller.AlunoController;
import com.fuctura.controller.CursoController;
import com.fuctura.database.Conexao;
import com.fuctura.database.DataBaseSeeder;

import java.util.Scanner;

public class App {

    private static final Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {

        //Teste de Conexão
        if (!Conexao.testarConexao()) {
            return;
        }
        // Garante que as tabelas existem e estão populadas antes de começar
        DataBaseSeeder.inicializar();

        boolean continuar = true;

        while (continuar) {
            exibirMenuPrincipal();
            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    menuAlunos();
                    break;
                case 2:
                    menuCursos();
                    break;
                case 0:
                    System.out.println("\nEncerrando o programa. Até logo!");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }

        scan.close();
    }

    // ─────────────────────────────────────────────
    // Menu principal
    // ─────────────────────────────────────────────

    private static void exibirMenuPrincipal() {
        System.out.println("\n========================================");
        System.out.println("           SISTEMA FUCTURA");
        System.out.println("========================================");
        System.out.println("1 - Gerenciar Alunos");
        System.out.println("2 - Gerenciar Cursos");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    // ─────────────────────────────────────────────
    // Submenu de Alunos
    // ─────────────────────────────────────────────

    private static void menuAlunos() {
        boolean voltar = false;

        while (!voltar) {
            System.out.println("\n--- MENU ALUNOS ---");
            System.out.println("1 - Cadastrar aluno");
            System.out.println("2 - Listar alunos");
            System.out.println("3 - Buscar aluno por ID");
            System.out.println("4 - Atualizar aluno");
            System.out.println("5 - Deletar aluno");
            System.out.println("6 - Matricular aluno em curso");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    AlunoController.createAluno();
                    break;
                case 2:
                    AlunoController.listAlunos();
                    break;
                case 3:
                    AlunoController.findAlunoById();
                    break;
                case 4:
                    AlunoController.updateAluno();
                    break;
                case 5:
                    AlunoController.deleteAluno();
                    break;
                case 6:
                    AlunoController.matricularAlunoEmCurso();
                    break;
                case 0:
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    // ─────────────────────────────────────────────
    // Submenu de Cursos
    // ─────────────────────────────────────────────

    private static void menuCursos() {
        boolean voltar = false;

        while (!voltar) {
            System.out.println("\n--- MENU CURSOS ---");
            System.out.println("1 - Cadastrar curso");
            System.out.println("2 - Listar cursos");
            System.out.println("3 - Buscar curso por ID");
            System.out.println("4 - Atualizar curso");
            System.out.println("5 - Deletar curso");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            int opcao = lerOpcao();

            switch (opcao) {
                case 1:
                    CursoController.createCurso();
                    break;
                case 2:
                    CursoController.listCursos();
                    break;
                case 3:
                    CursoController.findCursoById();
                    break;
                case 4:
                    CursoController.updateCurso();
                    break;
                case 5:
                    CursoController.deleteCurso();
                    break;
                case 0:
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        }
    }

    // ─────────────────────────────────────────────
    // Helper de leitura segura
    // ─────────────────────────────────────────────

    /**
     * Lê uma opção de menu do terminal. Retorna -1 se a entrada
     * não for um número válido, o que cai no "default" dos switches.
     */
    private static int lerOpcao() {
        String entrada = scan.nextLine().trim();
        try {
            return Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}