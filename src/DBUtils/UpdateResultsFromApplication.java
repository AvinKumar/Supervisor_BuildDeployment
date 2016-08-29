package DBUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import ReadEnvPropertiesPkg.ReadEnvProperties;

/**
 *
 * @author Avin
 */
public class UpdateResultsFromApplication {
    

    Connection connection = null;

    Statement statement = null;

    public void updateresults(String GID, String ExpectedResults, String ColumnName) throws Exception {
        ReadEnvProperties rp=new ReadEnvPropertiesPkg.ReadEnvProperties();	
    	Integer exp_results=0; 
        try {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + rp.getProperty("DB_Location"));
        
        statement = connection.createStatement();

             //update into DB
                    statement.executeUpdate("UPDATE DM_TESTCASE SET " + ColumnName + " = '" + ExpectedResults + "' WHERE GID='" + GID + "'");

                } catch (SQLException e) {
                    
                    System.out.println("*******" + e.getCause());
                    if (statement != null) {
                        statement.close();
                    }
                    if (connection != null) {
                        connection.close();
                    }

                }
            }
}