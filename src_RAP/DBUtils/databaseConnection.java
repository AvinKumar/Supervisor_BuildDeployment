/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DBUtils;

import Log4j.PropConfigurator;
import ReadEnvPropertiesPkg.ReadEnvProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Avin
 */
public class databaseConnection {
    public Connection conn=null;
    public Statement statement=null;
   //  static Logger log = Logger.getLogger(databaseConnection.class.getName());

    public void dbConnect() throws  Exception
    {
        PropConfigurator.configure();
        ReadEnvProperties rp = new ReadEnvProperties();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection("jdbc:sqlserver:"+rp.getProperty("db.location"), rp.getProperty("db.sqlusername"), rp.getProperty("db.sqlpassword"));
         
            System.out.print("Database Connection Successfull" +"\n"); 
            statement = conn.createStatement();
            statement.setQueryTimeout(30);
                    
            } catch (SQLException e) {
            System.out.print(" Error in Connecting Database" + e.getMessage());
             }
   }


 
  public void   CloseConnection()
    {
        
        PropConfigurator.configure();
        
      try {

            if (statement != null) { statement.close(); }
            if(conn != null) conn.close();
         }
      catch (Exception e)
      {
          System.out.println ("Error in closing Connection and statement\" + e.getMessage()");
        }
   }



//   public static void main(String[] args)  throws Exception
//   {
//      PropConfigurator.configure();
//      databaseConnection connServer = new databaseConnection();
//      connServer.dbConnect();
//      connServer.CloseConnection();
//   }

}
