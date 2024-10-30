package project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection{
	
	public static Connection conn = null;
	
	public static void dbconn() throws ClassNotFoundException, SQLException {
		
			Class.forName("com.mysql.cj.jdbc.Driver");
	 
	 //연결하기
			conn = DriverManager.getConnection(
				"jdbc:mysql://222.119.100.89:3382/chatdb",
				"chat153",
				"153123"
				);
		
			//System.out.println("insert 연결 성공");
	}
}
