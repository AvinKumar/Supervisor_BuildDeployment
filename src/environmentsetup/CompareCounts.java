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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import Log4j.PropConfigurator;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;


public class CompareCounts
{
    public static String ActualResults;
    public static String ExpectedResults;
   static Logger log = Logger.getLogger(CompareCounts.class.getName());
    
   public void dbConnect()
   {
       ExpectedResults="144";
       System.out.println("Message Push Count to be expected:>>> "+ ExpectedResults);
       String db_connect_string="jdbc:sqlserver://16.150.57.109:7777";
       String db_userid="sa";
       String db_password="p@ssw0rd";
       PropConfigurator.configure();
       try {
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
         Connection conn = DriverManager.getConnection(db_connect_string,
                  db_userid, db_password);
         System.out.println("Connected to MSSQLServer ");
         Statement statement = conn.createStatement();
         //String queryString = "select * from sysobjects where type='u'";
         String queryString = "select count (*) from [QASIB1].[dbo].[Singlecast]";
         ResultSet rs = statement.executeQuery(queryString);
         while (rs.next()) {
            ActualResults = (rs.getString(1));
            System.out.println("Message Push Count obtained:>>> "+ ActualResults);
            }
       } catch (Exception e) {
         e.printStackTrace();
      }
      
   }
   
   
   
  public static void main(String[] args) throws IOException,ClassNotFoundException,SQLException,InterruptedException

   {
      PropConfigurator.configure();
      CompareCounts connServer = new CompareCounts();
      connServer.dbConnect();
      log.info("Message Push Count to be expected:>>> "+ ExpectedResults);
      
      if (ActualResults.equals(ExpectedResults)) {
                 System.out.println("Actual and Expected Message Push counts are matching.");             
             }
      
      else {
                 System.out.println("Mismatch in Message Push counts. Please Investigate.");

            }
   }
}

    