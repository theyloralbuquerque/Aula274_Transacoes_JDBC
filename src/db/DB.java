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

	private static Connection conn = null; // Cria��o do atributo conn do tipo Connection.
	
	public static Connection getConnection() { // M�todo para conectar ao banco de dados.
		if (conn == null) { 
			try {
				Properties props = loadProperties(); // Pega as propriedades de conex�o. Chama o m�todo loadProperties() e armazena o retorno em props.
				String url = props.getProperty("dburl"); // A vari�vel url recebe o nome da propriedade que � a url do banco.
				conn = DriverManager.getConnection(url, props); 
				// .get.Connection() � respons�vel por estabelecer uma conex�o com o banco de dados, passando a url e o objeto props que tem o username e password do banco.
			}
			catch (SQLException e) { // Trata uma poss�vel exce��o lan�ada por DriverManager.getConnection().
				throw new DbException(e.getMessage()); // Lan�a uma exe��o personalizada DbException.
			}
		}
		return conn;
	}
	
	public static void closeConnection() { // M�todo respons�vel por fechar uma conex�o com o banco de dados.
		if (conn != null) {
			try {
				conn.close(); // .close() chamado a partir de um tipo Connection fecha uma conex�o com o BD.
			} catch (SQLException e) { // Trata uma poss�vel exce��o lan�ada por conn.close().
				throw new DbException(e.getMessage()); // Lan�a uma exe��o personalizada DbException.
			}
		}
	}
	
	private static Properties loadProperties() { // M�todo que l� o arquivo db.properties e retorna um tipo Properties.
		try (FileInputStream fs = new FileInputStream("db.properties")) { // new FileInputStream() l� dados brutos de um fluxo de entrada.
			Properties props = new Properties(); // Instancia��o do objeto props do tipo Properties. 
			props.load(fs); // .load() l� os dados do objeto fs e guarda os dentros dentro de props.
			return props;
		}
		catch (IOException e) { // Trata uma poss�vel exce��o lan�ada por load() ou FileInputStream.
			throw new DbException(e.getMessage()); // Lan�a uma exe��o personalizada DbException.
		}
	}
	
	public static void closeStatement(Statement st) { // M�todo respons�vel por fechar o Statement.
		if (st != null) {
			try {
				st.close(); // .close() chamado a partir de um tipo Statement fecha o Statement.
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) { // M�todo respons�vel por fechar o ResultSet.
		if (rs != null) {
			try {
				rs.close(); // .close() chamado a partir de um tipo ResultSet fecha o ResultSet.
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}
}