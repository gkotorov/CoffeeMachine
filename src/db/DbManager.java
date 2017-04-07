package db;

import java.sql.*;

public class DbManager {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost:3306/coffee_machine";

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "ss11";

   static Connection conn = null;
   static Statement stmt = null;
   
   static ResultSet rs = null;
   public static void init() {
   try{
      Class.forName("com.mysql.jdbc.Driver");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      stmt = conn.createStatement();
   }catch(SQLException se){
	      se.printStackTrace();
	   }catch(Exception e){
	      e.printStackTrace();
	   }
   }
   public static ResultSet getResultSet(String sqlQuery){
      try {
		rs = stmt.executeQuery(sqlQuery);
	} catch (SQLException e) {
		e.printStackTrace();
	}
      return rs;
   }
   
   public static void update(String sqlQuery){
	      try {
			stmt.executeUpdate(sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	   }
   
   public static void close() {
//    //STEP 5: Extract data from result set
//      try {
//		while(rs.next()){
//		     //Retrieve by column name
//		     int id  = rs.getInt("id");
//		     String first = rs.getString("name");
//		     int ing = rs.getInt("ingr");
//
//		     //Display values
//		     System.out.print("ID: " + id);
//		     System.out.print(", First: " + first);
//		     System.out.println(", Last: " + ing);
//		  }
//	} catch (SQLException e1) {
//		e1.printStackTrace();
//	}
      //STEP 6: Clean-up environment
   try{
      rs.close();
      stmt.close();
      conn.close();
   }catch(SQLException se){
      se.printStackTrace();
   }catch(Exception e){
      e.printStackTrace();
   }finally{
      try{
         if(stmt!=null)
            stmt.close();
      }catch(SQLException se2){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
   System.out.println("Goodbye!");
}
}