package com.fuctura;

import com.fuctura.dao.AlunoDAO;
import com.fuctura.database.Conexao;
import com.fuctura.database.DataBaseSeeder;
import com.fuctura.models.Aluno;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        // Testar a conexão com o banco de dados
        if (!Conexao.testarConexao()) {
            System.out.println("Falha na conexão com o banco de dados. Encerrando o programa.");
            return;
        }
        // Inicializar banco
        DataBaseSeeder.inicializar();


    }
}
