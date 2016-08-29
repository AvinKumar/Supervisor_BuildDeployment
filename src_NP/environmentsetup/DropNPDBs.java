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


import java.sql.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.File;

 
public class DropNPDBs  {
 
    private static final String DRIVER_NAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
 
    static
    {
        try
        {
            Class.forName(DRIVER_NAME).newInstance();
            System.out.println("*** Driver loaded");
        }
        catch(Exception e)
        {
            System.out.println("*** Error : "+e.toString());
            System.out.println("*** ");
            System.out.println("*** Error : ");
            e.printStackTrace();
        }
 
    }
 
    private static final String URL = "jdbc:sqlserver://16.150.57.109:7777";
    private static final String USER = "sa";
    private static final String PASSWORD = "p@ssw0rd";
//    private static String INSTRUCTIONS = new String();
 
    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
 
//    public static void resetDatabase() throws SQLException
//    public static void main (String[] args) throws SQLException,ExceptionInInitializerError
    public int dropNPDatabase() throws SQLException,ExceptionInInitializerError {
        
        String s            = new String();
        StringBuffer sb = new StringBuffer();
 
        try
        {
          FileReader fr = new FileReader(new File("SQL Scripts/Drop_QASIB1_NP_SQLDBs.sql"));
        //  FileReader fr = new FileReader(new File("Delete/DeleteSQL3.sql"));
            // be sure to not have line starting with "/*" or any other non aplhabetical character
 
            BufferedReader br = new BufferedReader(fr);
       
            //read the SQL file line by line
            while((s = br.readLine()) != null)  
            {  
                // ignore comments beginning with #
                int indexOfCommentSign = s.indexOf('#');
                if(indexOfCommentSign != -1)
                {
                    if(s.startsWith("#"))
                    {
                        s = new String("");
                    }
                    else
                        s = new String(s.substring(0, indexOfCommentSign-1));
                }
                // ignore comments beginning with --
                indexOfCommentSign = s.indexOf("--");
                if(indexOfCommentSign != -1)
                {
                    if(s.startsWith("--"))
                    {
                        s = new String("");
                    }
                    else
                        s = new String(s.substring(0, indexOfCommentSign-1));
                }
                
                //  the + " " is necessary, because otherwise the content before and after a line break are concatenated
                // like e.g. a.xyz FROM becomes a.xyzFROM otherwise and can not be executed 
                if(s != null)
                    sb.append(s + " ");  
            }  
            br.close();
             
            // here is the splitter!!! I'm using ";" as a delimiter for each request 
             String[] splittedQueries = sb.toString().split(";");
             
            Connection c = DropNPDBs.getConnection();
            Statement st = c.createStatement();
 
            for(int i = 0; i<splittedQueries.length; i++)
            {
                // I'm Ensuring that there is no spaces before or after the request string in order to not execute empty statements
                if(!splittedQueries[i].trim().equals("") && !splittedQueries[i].trim().equals("\t")) 
                {
                    st.executeUpdate(splittedQueries[i]);
                    System.out.println(">>"+splittedQueries[i]);
                }
            }
   
        }
       
        catch(SQLException sqle)
        {
            System.out.println("*** Error : "+sqle.toString());
            System.out.println("*** ");
            System.out.println("*** Error : ");
            sqle.printStackTrace();
            System.out.println("################################################");
            System.out.println(sb.toString());
            return -1;
        }
        catch(Exception e)
        {
            System.out.println("*** Error : "+e.toString());
            System.out.println("*** ");
            System.out.println("*** Error : ");
            e.printStackTrace();
            System.out.println("################################################");
            System.out.println(sb.toString());
            return -1;
        }
    return 0;
    }
}