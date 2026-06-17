package com.fuctura.database;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String HOST = "localhost";
    private static final int PORTA = 5432;
    private static final String BANCO = "postgres";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "123456";

    private static final String URL = "jdbc:postgresql://" + HOST + ":" + PORTA + "/" + BANCO;


    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL,USUARIO,SENHA);
    }



    public static boolean testarConexao(){
        try(Connection con = obterConexao()){
            System.out.println("Conexão feita com sucesso!");
            return true;
        } catch (SQLException e){
            System.out.println("Erro na Conexão!!");
            return false;
        }
    }


}
