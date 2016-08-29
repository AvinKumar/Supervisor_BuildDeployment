/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package environmentsetup;

/**
 *
 * @author Avin
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import static DBUtils.DatabaseUtils.statement;
import atu.testng.reports.ATUReports;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import Log4j.PropConfigurator;
import Reporting.GetDate;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.logging.Logger;
import org.testng.Assert;
import org.testng.annotations.Listeners;

//@Listeners({ATUReportsListener.class, ConfigurationListener.class,
//    MethodListener.class})

public class Mytest2
{
  static Logger log = Logger.getLogger(Mytest2.class.getName());
  public String Results=null;  
   public void dbConnect()
   {
       
       
       String db_connect_string="jdbc:sqlserver://16.150.57.109:7777";
       String db_userid="sa";
       String db_password="p@ssw0rd";
       PropConfigurator.configure();
       try {
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         Connection conn = DriverManager.getConnection(db_connect_string,
                  db_userid, db_password);
         System.out.println("Connected to MSSQLServer ");
         statement = conn.createStatement();
         //String queryString = "select * from sysobjects where type='u'";
         String queryString = "DECLARE @dbname1 VARCHAR(50)\n" +
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
         statement.execute(queryString);
//         String queryString = "select count (*) from [QASIB1].[dbo].[Singlecast]";
//         ResultSet rs = statement.executeQuery(queryString);
//         while (rs.next()) {
//            Results = (rs.getString(1));
//            System.out.println("OP:>>> "+ Results);
//            }
       } 
       catch (SQLException se) {
         se.printStackTrace();
      }
      
       catch (Exception e) {
         e.printStackTrace();
      }
      
   }
   
   
   
  public static void main(String[] args) throws IOException,ClassNotFoundException,SQLException,InterruptedException
   {

      Mytest2 connServer = new Mytest2();
      connServer.dbConnect();

      
      
   }
}

