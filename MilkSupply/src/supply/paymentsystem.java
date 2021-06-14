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
	static Statement st,st1,st5,st6;
	static ResultSet rs,rs1,rs2;
	
	public static  void combine() throws ClassNotFoundException, SQLException
	{
		scan = new Scanner(System.in);
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(url, user, pass);
	}
	
	public static void paying() throws ClassNotFoundException, SQLException
	{
		combine();
		
		int nxt=1;
		while(nxt==1)
		{
			System.out.println("Tap RFID card to purchase : ");
			int card_id= scan.nextInt();
			int def=0;
			
			st=con.createStatement();
			rs = st.executeQuery("select * from student_registration where card_id='"+card_id+"'");
			rs.next();
			
			if(rs.getRow()>0)
			{
				long millis=System.currentTimeMillis();  
		        java.sql.Date date=new java.sql.Date(millis);  
		        
		        rs1 = st.executeQuery("select * from sales where card_id='"+card_id+"'");
				rs1.next();
				
				if(rs1.getRow()>0)
				{
		        
				Statement st1 = con.createStatement();
				ResultSet rs =st1.executeQuery("Select balance from sales where card_id='"+card_id+"'");
				rs.next();
				
				int amount = rs.getInt("balance");
				
				if(amount == 0 || amount < 20)
				{
					System.out.println("\nInsufficient funds !!! ");
				}
				else
				{
				int bal=20;
				
				PreparedStatement st2 = con.prepareStatement("Update sales set balance=((balance-?)),Date='"+date+"',Amount_per_sale='"+bal+"' where card_id='"+card_id+"'");
				st2.setInt(1, bal);
				st2.executeUpdate();
			
				PreparedStatement st3 = con.prepareStatement("Update student_recharge set balance=((balance-?)) where card_id='"+card_id+"'");
				st3.setInt(1, bal);
				st3.executeUpdate();
				
				System.out.println("\nThe transaction has been performed successfully\n");
				}
			}
				
				else
				{
					Statement st5 = con.createStatement();
					ResultSet rs =st5.executeQuery("Select rollno from student_registration where card_id='"+card_id+"'");
					rs.next();
					int roll = rs.getInt("rollno");
					
					Statement st6 = con.createStatement();
					rs2=st6.executeQuery("Select Balance from student_recharge where card_id='"+card_id+"'");
					rs2.next();
					int balcheck = rs2.getInt("Balance");
					
					Statement st4 = con.createStatement();
					st4.executeUpdate("insert into sales (card_id,rollno,Balance,Amount_per_sale,Date) values ('"+card_id+"','"+roll+"','"+balcheck+"','"+def+"','"+date+"')");
					
					System.out.println("\nThis was the first transaction , tap again to purchase");
				}
			}
				
				else
				{
					System.out.println("\nInvalid RFID card");
				}
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

