package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {

	private static Connection conn = null; // Criação do atributo conn do tipo Connection.
	
	public static Connection getConnection() { // Método para conectar ao banco de dados.
		if (conn == null) { 
			try {
				Properties props = loadProperties(); // Pega as propriedades de conexão. Chama o método loadProperties() e armazena o retorno em props.
				String url = props.getProperty("dburl"); // A variável url recebe o nome da propriedade que é a url do banco.
				conn = DriverManager.getConnection(url, props); 
				// .get.Connection() é responsável por estabelecer uma conexão com o banco de dados, passando a url e o objeto props que tem o username e password do banco.
			}
			catch (SQLException e) { // Trata uma possível exceção lançada por DriverManager.getConnection().
				throw new DbException(e.getMessage()); // Lança uma exeção personalizada DbException.
			}
		}
		return conn;
	}
	
	public static void closeConnection() { // Método responsável por fechar uma conexão com o banco de dados.
		if (conn != null) {
			try {
				conn.close(); // .close() chamado a partir de um tipo Connection fecha uma conexão com o BD.
			} catch (SQLException e) { // Trata uma possível exceção lançada por conn.close().
				throw new DbException(e.getMessage()); // Lança uma exeção personalizada DbException.
			}
		}
	}
	
	private static Properties loadProperties() { // Método que lê o arquivo db.properties e retorna um tipo Properties.
		try (FileInputStream fs = new FileInputStream("db.properties")) { // new FileInputStream() lê dados brutos de um fluxo de entrada.
			Properties props = new Properties(); // Instanciação do objeto props do tipo Properties. 
			props.load(fs); // .load() lê os dados do objeto fs e guarda os dentros dentro de props.
			return props;
		}
		catch (IOException e) { // Trata uma possível exceção lançada por load() ou FileInputStream.
			throw new DbException(e.getMessage()); // Lança uma exeção personalizada DbException.
		}
	}
	
	public static void closeStatement(Statement st) { // Método responsável por fechar o Statement.
		if (st != null) {
			try {
				st.close(); // .close() chamado a partir de um tipo Statement fecha o Statement.
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) { // Método responsável por fechar o ResultSet.
		if (rs != null) {
			try {
				rs.close(); // .close() chamado a partir de um tipo ResultSet fecha o ResultSet.
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}