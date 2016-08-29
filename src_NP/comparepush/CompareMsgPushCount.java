/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparepush;

/**
 *
 * @author Avin
 */
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
import java.sql.SQLException;
import java.util.logging.Logger;
import org.testng.Assert;
import org.testng.annotations.Listeners;
//import ReadEnvPropertiesPkg.ReadEnvProperties;

@Listeners({ATUReportsListener.class, ConfigurationListener.class,
    MethodListener.class})

public class CompareMsgPushCount
{
    public static String ActualResults;
    public static String ExpectedResults;
   static Logger log = Logger.getLogger(CompareMsgPushCount.class.getName());
    
   public void dbConnect()
   {
       ExpectedResults="151";
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
   
   
   
//  public static void main(String[] args)
  public void compareMsgPush() throws IOException,ClassNotFoundException,SQLException,InterruptedException
   {
//      PropConfigurator.configure();
//      CompareMsgPushCount connServer = new CompareMsgPushCount();
//      connServer.dbConnect("jdbc:sqlserver://16.150.57.109:7777", "sa",
//               "p@ssw0rd");
//      connServer.dbConnect();
//        ExpectedResults="958";
//      log.info("Message Push Count to be expected:>>> "+ ExpectedResults);
      
      if (ActualResults.equals(ExpectedResults)) {
                 System.out.println("Actual and Expected Message Push counts are matching.");
                 ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
                 ATUReports.add("Message Push Count" , String.valueOf(ExpectedResults), String.valueOf(ActualResults), false);
                 
             }
             else {
                 System.out.println("Mismatch in Message Push counts. Please Investigate.");
                 ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
                 ATUReports.add("Message Push Count" , String.valueOf(ExpectedResults), String.valueOf(ActualResults), false);                
                 Assert.fail("Actual Results::::" + ActualResults +"\t"+"Expected Results::::" + ExpectedResults);
            }
   }
}
