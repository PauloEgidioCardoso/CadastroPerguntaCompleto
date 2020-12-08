package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoPostgres {
    private static final String URL = "jdbc:postgresql://localhost:5432/bancoDadosPi";
    private static final String USUARIO = "postgres";
    private static final String SENHA = "32823282";

    private static Connection conexao;

    private ConexaoPostgres() {

    }

    public static Connection getConexao() throws SQLException {
	if (conexao == null) {
	    conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
	}
	return conexao;
    }
} // fim da classe ConexaoPostgres
