package supply;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class load {

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
	
	public static void search() throws SQLException, ClassNotFoundException
	{

		{
			
			combine();
			System.out.println("Enter the rollnumber : ");
			int rollnumb= scan.nextInt();
			
			st=con.createStatement();
			rs = st.executeQuery("select * from student_recharge where rollno='"+rollnumb+"'");
			rs.next();
			
			if(rs.getRow()>0)
			{
				System.out.println("\nStudent found in the system");	
				
				System.out.println("\nDo you wish to load money on rollnumber : "+rollnumb+"?"
						+ "\nPress 1 to load or 0 to cancel transaction ");
				
				int num =scan.nextInt();
				if(num==1)
				{
					
					System.out.println("\nEnter the amount you want to load");
					int recharge=scan.nextInt();
					
					PreparedStatement st1 = con.prepareStatement("Update student_recharge set balance=((balance+?)) where rollno='"+rollnumb+"'");
					st1.setInt(1, recharge);
					st1.executeUpdate();
					
					System.out.println("Account has been loaded with "+recharge+"/=");
				}
				else if(num==0)
				{
					System.out.println("\nThe transaction is cancelled");
				}
				
				else
				{
					System.out.println("\nInvalid selection, chose either 1 to continue or 0 to cancel transaction");
				}
			}
			else
			{	
				System.out.println("\nThe student not found in the system !!!");
			}
			
		}
	}	
}

	public class Credit_load extends load{
		
		public static void main(String args[]) throws ClassNotFoundException, SQLException
		{
			Credit_load.search();

		}

	}
		


