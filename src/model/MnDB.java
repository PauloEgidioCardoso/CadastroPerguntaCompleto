package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MnDB {
    public static boolean inserir(Questao questao) {
	final String query1 = "INSERT INTO pergunta (conteudo_pergunta, dificuldade, resposta, dica, alternativa_1, alternativa_2, alternativa_3, alternativa_4)"
		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	final String query2 = "INSERT INTO pergunta_indicacaoDificuldade (codigo_pergunta, codigo_indicacao) "
		+ " VALUES(?, ?)";

	Connection conexao = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;
	try {
	    conexao = ConexaoPostgres.getConexao();

	    statement = conexao.prepareStatement(query1, Statement.RETURN_GENERATED_KEYS);
	    statement.setString(1, questao.getPergunta());
	    statement.setString(2, questao.getDescricao());
	    statement.setString(3, questao.getResposta());
	    statement.setString(4, questao.getDica());
	    statement.setString(5, questao.getAlternativa());
	    statement.setString(6, questao.getAlternativa2());
	    statement.setString(7, questao.getAlternativa3());
	    statement.setString(8, questao.getAlternativa4());
	    statement.execute();

	    resultSet = statement.getGeneratedKeys();
	    while (resultSet.next()) {
		questao.setId(resultSet.getInt(1));
	    }

	    statement.close();

	    statement = conexao.prepareStatement(query2);
	    statement.setInt(1, questao.getId());
	    for (IndicacaoDificuldade indicacao : questao.getIndicacao()) {
		statement.setInt(2, indicacao.getId());
		statement.addBatch();
	    }
	    statement.executeBatch();

	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	} finally {
	    {
		try {
		    if (resultSet != null) {
			resultSet.close();
		    }
		    if (statement != null) {
			statement.close();
		    }
		} catch (SQLException e) {
		    e.printStackTrace();
		    return false;
		}
	    }
	}
	return true;
    }

    public static boolean atualizar(Questao questao) {
	final String query1 = "UPDATE pergunta SET conteudo_pergunta = ?, dificuldade = ?, resposta = ?, dica = ?, alternativa_1 = ?, alternativa_2 = ?, alternativa_3 = ?, alternativa_4 = ?"
		+ " WHERE codigo_pergunta = ?";
	
	final String query2 = "DELETE FROM pergunta_indicacaoDificuldade WHERE codigo_pergunta =  ?";
	
	final String query3 = "INSERT INTO pergunta_indicacaoDificuldade (codigo_pergunta, codigo_indicacao) "
		+ " VALUES(?, ?)";

	Connection conexao = null;
	PreparedStatement statement = null;

	try {
	    conexao = ConexaoPostgres.getConexao();

	    statement = conexao.prepareStatement(query1);
	    statement.setString(1, questao.getPergunta());
	    statement.setString(2, questao.getDescricao());
	    statement.setString(3, questao.getResposta());
	    statement.setString(4, questao.getDica());
	    statement.setString(5, questao.getAlternativa());
	    statement.setString(6, questao.getAlternativa2());
	    statement.setString(7, questao.getAlternativa3());
	    statement.setString(8, questao.getAlternativa4());
	    statement.setInt(9, questao.getId());
	    statement.execute();
	    statement.close();
	    
	    statement = conexao.prepareStatement(query2);
	    statement.setInt(1, questao.getId());
	    statement.execute();
	    statement.close();
	    
	    statement = conexao.prepareStatement(query3);
	    statement.setInt(1, questao.getId());
	    for (IndicacaoDificuldade indicacao : questao.getIndicacao()) { // aqui erro
		statement.setInt(2, indicacao.getId());
		statement.addBatch();
	    }
	    statement.executeBatch();
	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	} finally {

	    try {
		if (statement != null) {
		    statement.close();
		}
	    } catch (SQLException e) {
		e.printStackTrace();
		return false;
	    }
	}

	return true;
    }

    public static boolean remover(Questao questao) {
	final String query1 = "DELETE FROM pergunta WHERE codigo_pergunta = ?";
	final String query2 = "DELETE FROM pergunta_indicacaoDificuldade WHERE codigo_pergunta = ?";
		
	Connection conexao = null;
	PreparedStatement statement = null;

	try {
	    conexao = ConexaoPostgres.getConexao();

	    statement = conexao.prepareStatement(query2);
	    statement.setInt(1, questao.getId());
	    statement.execute();
	    statement.close();
	    
	    statement = conexao.prepareStatement(query1);
	    statement.setInt(1, questao.getId());
	    statement.execute();

	} catch (SQLException e) {
	    e.printStackTrace();
	    return false;
	} finally {

	    try {
		if (statement != null) {
		    statement.close();
		}
	    } catch (SQLException e) {
		e.printStackTrace();
		return false;
	    }
	}
	return true;

    }

    public static List<Questao> listar() {
	List<Questao> questoes = new ArrayList<>();

	final String query = "SELECT * FROM pergunta ORDER BY codigo_pergunta";

	Connection conexao = null;
	Statement statement = null;
	ResultSet resultSet = null;

	try {
	    conexao = ConexaoPostgres.getConexao();
	    statement = conexao.createStatement();
	    resultSet = statement.executeQuery(query);

	    while (resultSet.next()) {
		Questao questao = new Questao();
		questao.setId(resultSet.getInt("codigo_pergunta"));
		questao.setPergunta(resultSet.getString("conteudo_pergunta"));
		questao.setResposta(resultSet.getString("resposta"));
		questao.setDica(resultSet.getString("dica"));
		questao.setAlternativa(resultSet.getString("alternativa_1"));
		questao.setAlternativa2(resultSet.getString("alternativa_2"));
		questao.setAlternativa3(resultSet.getString("alternativa_3"));
		questao.setAlternativa4(resultSet.getString("alternativa_4"));

		if (resultSet.getString("dificuldade").equals("Fácil")) {
		    questao.setDificuldade(Dificuldade.FACIL);
		} else if (resultSet.getString("dificuldade").equals("Médio")) {
		    questao.setDificuldade(Dificuldade.MEDIO);
		} else {
		    questao.setDificuldade(Dificuldade.DIFICIL);
		}

		List<IndicacaoDificuldade> indicacoes = listarSelecionados(questao.getId());
		questao.setIndicacao(indicacoes);
		
		questoes.add(questao);
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (resultSet != null) {
		    resultSet.close();
		}
		if (statement != null) {
		    statement.close();
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}

	return questoes;
    }

    public static List<IndicacaoDificuldade> listarIndicacao() {
	List<IndicacaoDificuldade> indicacoes = new ArrayList<>();

	final String query = "SELECT * FROM indicacaoDificuldade ORDER BY codigo_indicacao";

	Connection conexao = null;
	Statement statement = null;
	ResultSet resultSet = null;

	try {
	    conexao = ConexaoPostgres.getConexao();
	    statement = conexao.createStatement();
	    resultSet = statement.executeQuery(query);

	    while (resultSet.next()) {
		IndicacaoDificuldade indicacao = new IndicacaoDificuldade();
		indicacao.setId(resultSet.getInt("codigo_indicacao"));
		indicacao.setSerie(resultSet.getString("serie"));

		indicacoes.add(indicacao);
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (resultSet != null) {
		    resultSet.close();
		}
		if (statement != null) {
		    statement.close();
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}

	return indicacoes;
    }

    public static List<IndicacaoDificuldade> listarNaoSelecionados(int idQuestao) {
	List<IndicacaoDificuldade> indicacoes = new ArrayList<>();

	final String query = "SELECT i.codigo_indicacao, i.serie FROM indicacaodificuldade i WHERE i.codigo_indicacao "
		+ " NOT IN (SELECT pi.codigo_indicacao FROM pergunta_indicacaoDificuldade pi WHERE pi.codigo_pergunta = ?)";

	Connection conexao = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;

	try {
	    conexao = ConexaoPostgres.getConexao();
	    statement = conexao.prepareStatement(query);
	    statement.setInt(1, idQuestao);
	    resultSet = statement.executeQuery();

	    while (resultSet.next()) {
		IndicacaoDificuldade indicacao = new IndicacaoDificuldade();
		indicacao.setId(resultSet.getInt("codigo_indicacao"));
		indicacao.setSerie(resultSet.getString("serie"));

		indicacoes.add(indicacao);
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (resultSet != null) {
		    resultSet.close();
		}
		if (statement != null) {
		    statement.close();
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}

	return indicacoes;
    }
    
    public static List<IndicacaoDificuldade> listarSelecionados(int idQuestao) {
	List<IndicacaoDificuldade> indicacoes = new ArrayList<>();

	final String query = "SELECT i.codigo_indicacao, i.serie FROM indicacaodificuldade i JOIN pergunta_indicacaoDificuldade pi"
		+ " ON i.codigo_indicacao = pi.codigo_indicacao WHERE pi.codigo_pergunta = ?";

	Connection conexao = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;

	try {
	    conexao = ConexaoPostgres.getConexao();
	    statement = conexao.prepareStatement(query);
	    statement.setInt(1, idQuestao);
	    resultSet = statement.executeQuery();

	    while (resultSet.next()) {
		IndicacaoDificuldade indicacao = new IndicacaoDificuldade();
		indicacao.setId(resultSet.getInt("codigo_indicacao"));
		indicacao.setSerie(resultSet.getString("serie"));

		indicacoes.add(indicacao);
	    }

	} catch (SQLException e) {
	    e.printStackTrace();
	} finally {
	    try {
		if (resultSet != null) {
		    resultSet.close();
		}
		if (statement != null) {
		    statement.close();
		}
	    } catch (SQLException e) {
		e.printStackTrace();
	    }
	}

	return indicacoes;
    }
}
