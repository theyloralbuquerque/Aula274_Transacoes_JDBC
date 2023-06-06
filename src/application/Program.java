package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import db.DB;
import db.DbException;

public class Program {

	public static void main(String[] args) {

		Connection conn = null;				// Cria��o da vari�vel conn do tipo Connection.
		Statement st = null;				// Cria��o da vari�vel st do tipo PreparedStatement.
		
		try {
			conn = DB.getConnection();		// .getConnection() conecta com o BD.
			
			conn.setAutoCommit(false);      // Define o setAutoCommit como false, ou seja, transa��es n�o ser�o confirmadas automaticamente.
			
			st = conn.createStatement();    // createStatement() instancia um objeto do tipo Statement a partir de um objeto Connection.

			int rows1 = st.executeUpdate("UPDATE seller SET BaseSalary = 2090 WHERE DepartmentId = 1");

			// Simula��o da exce��o.
			// int x = 1;
			// if (x < 2) {
			//	throw new SQLException("Fake error");
			//}

			int rows2 = st.executeUpdate("UPDATE seller SET BaseSalary = 3090 WHERE DepartmentId = 2");
			
			conn.commit();                  // Confirma a transa��o.

			System.out.println("rows1 = " + rows1);
			System.out.println("rows2 = " + rows2);
		}
		catch (SQLException e) {
			try {
				conn.rollback();            // .rollback() volta a transa��o para o estado enterior caso ela tenha parado pelo meio.
				throw new DbException("Transaction rolled back! Caused by: " + e.getMessage());
			} 
			catch (SQLException e1) {
				throw new DbException("Error trying to rollback! Caused by: " + e1.getMessage());
			}
		} 
		finally {
			DB.closeStatement(st);
			DB.closeConnection();
		}
	}
}