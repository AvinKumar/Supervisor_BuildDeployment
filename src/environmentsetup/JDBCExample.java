package environmentsetup;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Avin
 */
//STEP 1. Import required packages
import java.sql.*;


public class JDBCExample {
   // JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  
   static final String DB_URL = "jdbc:sqlserver://16.150.57.109:7777";

   //  Database credentials
   static final String USER = "sa";
   static final String PASS = "p@ssw0rd";
   
   public static void main(String[] args) {
   Connection conn = null;
   CallableStatement stmt = null;
   try{
     
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(DB_URL,USER,PASS);

      //Execute a query
      System.out.println("Creating statement...");
      String sql = "DECLARE @dbname1 VARCHAR(50)\n" +
"DECLARE @dbname2 VARCHAR(50)\n" +
"DECLARE @FileName1 NVARCHAR(255)\n" +
"DECLARE @FileName2 NVARCHAR(255)\n" +
"\n" +
"\n" +
"\n" +
"SET @dbname1 = 'avin1'\n" +
"SET @FileName1 = N'C:\\Program Files\\Microsoft SQL Server\\MSSQL10_50.MSSQLSERVER\\MSSQL\\Backup\\avin1.bak' \n" +
"\n" +
"\n" +
"SET @dbname2 = 'avin2'\n" +
"SET @FileName2 = N'C:\\Program Files\\Microsoft SQL Server\\MSSQL10_50.MSSQLSERVER\\MSSQL\\Backup\\avin2.bak' \n" +
"\n" +
"\n" +
"Print 'Restoring database \"' + @dbname1 + '\"'\n" +
"RESTORE DATABASE @dbname1\n" +
"FROM DISK = @FileName1\n" +
"WITH FILE = 1,  \n" +
"\n" +
"NOUNLOAD, REPLACE,  \n" +
"STATS = 10\n" +
"Print '*********************************'\n" +
"Print ''\n" +
"\n" +
"\n" +
"Print 'Restoring database \"' + @dbname2 + '\"'\n" +
"RESTORE DATABASE @dbname2\n" +
"FROM DISK = @FileName2\n" +
"WITH FILE = 1,\n" +
"NOUNLOAD, REPLACE,  \n" +
"STATS = 10\n" +
"Print '*********************************'\n" +
"Print ''";
      stmt = conn.prepareCall(sql);
      System.out.println("Executing stored procedure..." );
       stmt.executeUpdate();
//      stmt.execute();
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
      se2.printStackTrace();}
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
