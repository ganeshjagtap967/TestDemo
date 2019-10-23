package com.twtech;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class AlertSystem {

	static Connection conn = null;
	static Statement st1 = null;
	static Statement st2 = null;
	static Statement st3 = null;
	
	
	

	public static void GetConnection() 
	{
		try {
			String MM_dbConn_DRIVER = "org.gjt.mm.mysql.Driver";
			String MM_dbConn_USERNAME = "fleetview";
			String MM_dbConn_PASSWORD = "1@flv";//103.8.126.138
			String MM_dbConn= "jdbc:mysql://103.241.181.36:3306/db_gps";//202.65.131.44:3306
			Class.forName(MM_dbConn_DRIVER);
			conn = DriverManager.getConnection(MM_dbConn,MM_dbConn_USERNAME, MM_dbConn_PASSWORD);

			st1 =  conn.createStatement();
			st2 =  conn.createStatement();
			st3 =  conn.createStatement();
			
			System.out.print("Connection successful..." );
			} 
		catch (Exception e) {
			System.out.println("GetConnection Exception ---->" + e);
		}
	}

	public static void CloseConnection() {
		try {
			st1.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("CloseConnection Exception---->" + e);
		}
	}

	public static void main(String [] args) {
		
		try 
		{
			System.out.println("In main()");
			GetConnection();
			int i=0;

			String Username="",TypeofUser="",TypeValue="" ;
			
			String Security="select distinct(Username), TypeofUser, TypeValue from t_security where activestatus='Yes' order by UserID desc limit 200 ";
			//System.out.println("Security Query :"+Security);
			ResultSet rs1=st1.executeQuery(Security);
			while(rs1.next())
			{
				
				
				Username=rs1.getString("Username");
				//System.out.println("Username="+Username);
				TypeofUser=rs1.getString("TypeofUser");
				TypeValue=rs1.getString("TypeValue");

				String Userdetails="select * from t_userdetails where UserName='"+Username+"'";
				ResultSet rs2=st2.executeQuery(Userdetails);
				if(rs2.next()) {
					//System.out.println("Username:"+rs2.getString("UserName"));
				}
				else{
					i++;
					String Insert="insert into db_gps.t_userdetails (UserName,Transporter,UserType,Email) values ('"+Username+"','"+TypeValue+"','"+TypeofUser+"','"+Username+"')"; 
					//st3.executeUpdate(Insert);
					System.out.println("Insert Query: "+ Insert);

				}
			}
			System.out.println(i);
			

		} catch (Exception e) 
		{
			System.out.println("E-mail Sending Failed"+e);
			e.printStackTrace();
		}  

	}

}
