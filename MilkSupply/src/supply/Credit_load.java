package supply;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Credit_load {

	static Connection con;
	static String url="jdbc:mysql://localhost:3306/supplied";
	static String user="root";
	static String pass="";
	
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException
	{
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		con=DriverManager.getConnection(url, user, pass);
		System.out.println("Connection has been made successfully");
	}
	

}
