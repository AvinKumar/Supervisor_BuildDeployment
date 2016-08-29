package DBUtils;

/**
 *
 * @author Avin
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.log4j.Logger;
import Log4j.PropConfigurator;

import ReadEnvPropertiesPkg.ReadEnvProperties;

public class openkwysConnection {
    

public Connection connection_kwys = null;
       public Statement statement_kwys;
      
        
        
        static Logger log = Logger.getLogger(openkwysConnection.class.getName());

       public void openDatabaseConnection()throws ClassNotFoundException
       {
           PropConfigurator.configure();
          //BasicConfigurator.configure();
           ReadEnvProperties rp=new ReadEnvPropertiesPkg.ReadEnvProperties();
           log.info( " opening Database Connection");
           Class.forName("org.sqlite.JDBC");
           try
             {
                  connection_kwys = DriverManager.getConnection("jdbc:sqlite:"+rp.getProperty("DB_Location"));
                  statement_kwys = connection_kwys.createStatement();
                  statement_kwys.setQueryTimeout(30);  // set timeout to 30 sec.
            }
            catch(SQLException e)
            {
                log.error(" Issue in Opening database Connection" );
                log.error(e.getMessage());
            }
  
       
       
       }
       
}