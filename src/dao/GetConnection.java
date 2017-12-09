package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConnection {

	Connection con = null;
	public Connection getConnection()
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3307/stock","root","Vishal@123");  
			return con;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}  
		return con;
	}
}
