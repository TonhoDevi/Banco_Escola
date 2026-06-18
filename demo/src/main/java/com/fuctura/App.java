package com.fuctura;

import com.fuctura.dao.AlunoDAO;
import com.fuctura.database.Conexao;
import com.fuctura.models.Aluno;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Digite o nome do aluno");
        String nome = scan.next();
        System.out.println("Digite a idade do aluno");
        int idade = scan.nextInt();
        System.out.println("Digite o email do aluno");
        String email = scan.next();



        int novaID = AlunoDAO.createAluno(nome,idade,email);
        System.out.println("Foi gerado novo aluno com id: "+ novaID);












       // Conexao.testarConexao();
        //List<Aluno> ListaAlunos = new ArrayList<>();
        //ListaAlunos = AlunoDAO.buscarAlunos(40,0);
        //System.out.println(ListaAlunos);

    }
}
