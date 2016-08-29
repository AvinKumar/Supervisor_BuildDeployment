/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package environmentsetup;

/**
 *
 * @author kumavin
 */


import java.sql.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
 
//import java.sql.SQLException;
//import java.sql.DriverManager;
//import java.sql.Connection;
//import java.sql.Statement;
//import java.sql.CallableStatement;
 
public class MyTest  {
 
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
    public static void main (String[] args) throws SQLException,ExceptionInInitializerError
    {
        String s            = new String();
        StringBuffer sb = new StringBuffer();
 
        try
        {
            FileReader fr = new FileReader(new File("Delete/DeleteSQL.sql"));
            // be sure to not have line starting with "--" or "/*" or any other non aplhabetical character
 
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
//                // ignore comments surrounded by /* */
//                indexOfCommentSign = queryLine.indexOf("/*");
//                if(indexOfCommentSign != -1)
//                {
//                    if(queryLine.startsWith("#"))
//                    {
//                        queryLine = new String("");
//                    }
//                    else
////                        queryLine = new String(queryLine.substring(0, indexOfCommentSign-1));
//                     
//                    sBuffer.append(queryLine + " "); 
//                    // ignore all characters within the comment
//                    do
//                    {
//                        queryLine = br.readLine();
//                    }
//                    while(queryLine != null && !queryLine.contains("*/"));
//                    indexOfCommentSign = queryLine.indexOf("*/");
//                    if(indexOfCommentSign != -1)
//                    {
//                        if(queryLine.endsWith("*/"))
//                        {
//                            queryLine = new String("");
//                        }
//                        else
//                            queryLine = new String(queryLine.substring(indexOfCommentSign+2, queryLine.length()-1));
//                    }
//                }
                 
                //  the + " " is necessary, because otherwise the content before and after a line break are concatenated
                // like e.g. a.xyz FROM becomes a.xyzFROM otherwise and can not be executed 
                if(s != null)
                    sb.append(s + " ");  
            }  
            br.close();
             
            // here is our splitter ! We use ";" as a delimiter for each request 
            String[] splittedQueries = sb.toString().split(";");
             
            // filter out empty statements
//            for(int i = 0; i<splittedQueries.length; i++)  
//            {
//                if(!splittedQueries[i].trim().equals("") && !splittedQueries[i].trim().equals("\t"))  
//                {
//                    listOfQueries.add(new String(splittedQueries[i]));
//                }
//            }
//        }  
            Connection c = SQLs.getConnection();
            Statement st = c.createStatement();
 //CallableStatement st = c.prepareCall(s);
            for(int i = 0; i<splittedQueries.length; i++)
            {
                // we ensure that there is no spaces before or after the request string
                // in order to not execute empty statements
                if(!splittedQueries[i].trim().equals("") && !splittedQueries[i].trim().equals("\t")) 
                {
                //    listOfQueries.add(new String(splittedQueries[i]));
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
        }
        catch(Exception e)
        {
            System.out.println("*** Error : "+e.toString());
            System.out.println("*** ");
            System.out.println("*** Error : ");
            e.printStackTrace();
            System.out.println("################################################");
            System.out.println(sb.toString());
        }
 
    }
    
//    public static void main(String[] args) throws IOException,ClassNotFoundException,SQLException,InterruptedException
////  public void compareMsgPush() throws IOException,ClassNotFoundException,SQLException,InterruptedException
//   {
//      PropConfigurator.configure();
//      SQLs e = new SQLs();
//    //  connServer.dbConnect("jdbc:sqlserver://16.150.57.109:7777", "sa","p@ssw0rd");
//   //   e.resetDatabase();
//
//   }
}
