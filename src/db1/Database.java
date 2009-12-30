package db1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	static Connection con;
	static Statement statement;
	
	static{
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String url = "jdbc:odbc:STAFF";
			con = DriverManager.getConnection(url);
			statement = con.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		return con;
	}

	public static Statement getStatement(){
		return statement;
	}
}