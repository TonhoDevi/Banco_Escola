package com.fuctura;

import com.fuctura.dao.AlunoDAO;
import com.fuctura.database.Conexao;
import com.fuctura.models.Aluno;

import java.util.ArrayList;
import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
        Conexao.testarConexao();
        List<Aluno> ListaAlunos = new ArrayList<>();
        ListaAlunos = AlunoDAO.buscarAlunos(4,0);
        System.out.println(ListaAlunos);

    }
}
