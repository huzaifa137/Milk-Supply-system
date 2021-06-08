package supply;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class pay {

	static Connection con;
	static String url="jdbc:mysql://localhost:3306/supplied";
	static String user="root";
	static String pass="";
	static Scanner scan;
	static Statement st,st1;
	static ResultSet rs;
	
	public static  void combine() throws ClassNotFoundException, SQLException
	{
		scan = new Scanner(System.in);
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(url, user, pass);
	}
	
	public static void paying() throws ClassNotFoundException, SQLException
	{
		combine();
		
		System.out.println("Tap RFID card to purchase : ");
		int card_id= scan.nextInt();
		
		st=con.createStatement();
		rs = st.executeQuery("select * from sales where card_id='"+card_id+"'");
		rs.next();
		
		if(rs.getRow()>0)
		{
			
			Statement st1 = con.createStatement();
			ResultSet rs =st1.executeQuery("Select balance from sales where card_id='"+card_id+"'");
			rs.next();
			
			int amount = rs.getInt("balance");
			
			if(amount == 0 || amount < 0)
			{
				System.out.println("Insufficient funds !!! ");
			}
			else
			{
			int bal=20;
			
			PreparedStatement st2 = con.prepareStatement("Update sales set balance=((balance-?)) where card_id='"+card_id+"'");
			st2.setInt(1, bal);
			st2.executeUpdate();
		
			System.out.println("\nThe transaction has been performed successfully");
				}
			}
			else
			{
				System.out.println("\nInvalid RFID card");
			}
		
		}
	}



public class paymentsystem extends pay
{
	public static void main(String args[]) throws ClassNotFoundException, SQLException
	{
		paymentsystem.paying();
	}
	
	
}

