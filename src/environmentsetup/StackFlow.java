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
/**
 * This class can be used to read sql files into an array of Strings, each 
 * representing a single query terminated by ";" 
 * Comments are filtered out. 
 */

 
 
import java.io.BufferedReader;  
 
import java.io.File;  
import java.io.FileReader; 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
 
/*
 * ATTENTION: SQL file must not contain column names, etc. including comment signs (#, --, /* etc.)
 *          like e.g. a.'#rows' etc. because every characters after # or -- in a line are filtered 
 *          out of the query string the same is true for every characters surrounded by /* and */
/**/
 
public class StackFlow
{ 
//    private static ArrayList<String> listOfQueries = null;
    
     
    /*
     * @param   path    Path to the SQL file
     * @return          List of query strings 
     */
 //   public ArrayList<String> createQueries(String path)  
    public static void main (String[] args) throws SQLException,ExceptionInInitializerError
    { 
        String queryLine =      new String();
        StringBuffer sBuffer =  new StringBuffer();
    //    listOfQueries =         new ArrayList<String>();
         
        try {  
            FileReader fr = new FileReader(new File("Delete/DeleteSQL.sql"));      
             BufferedReader br = new BufferedReader(fr);
       
            //read the SQL file line by line
            while((queryLine = br.readLine()) != null)  
            {  
                // ignore comments beginning with #
                int indexOfCommentSign = queryLine.indexOf('#');
                if(indexOfCommentSign != -1)
                {
                    if(queryLine.startsWith("#"))
                    {
                        queryLine = new String("");
                    }
                    else
                        queryLine = new String(queryLine.substring(0, indexOfCommentSign-1));
                }
                // ignore comments beginning with --
                indexOfCommentSign = queryLine.indexOf("--");
                if(indexOfCommentSign != -1)
                {
                    if(queryLine.startsWith("--"))
                    {
                        queryLine = new String("");
                    }
                    else
                        queryLine = new String(queryLine.substring(0, indexOfCommentSign-1));
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
                if(queryLine != null)
                    sBuffer.append(queryLine + " ");  
            }  
            br.close();
             
            // here is our splitter ! We use ";" as a delimiter for each request 
            String[] splittedQueries = sBuffer.toString().split(";");
             
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
            
            
        catch(Exception e)  
        {  
            System.out.println("*** Error : "+e.toString());  
            System.out.println("*** ");  
            System.out.println("*** Error : ");  
            e.printStackTrace();  
            System.out.println("################################################");  
            System.out.println(sBuffer.toString());  
        }
//        return listOfQueries;
    } 
    
//    public static void main (String [] args) throws SQLException{
//        String queries_01;
//String pathname_01;
// 
////... get queries from sql file .................
//        pathname_01 = "C:/Temp/sql_queries/test.sql";
//        queries_01 = sqlread.toString();
//             
//        ArrayList<ResultSet> RSList_01 = new ArrayList<ResultSet>();
//        for(String query: queries_01)
//        {
//            RSList_01.add(pps.DoExecuteQuery(query));
//        }
//         
//        try {
//            ResultSet tmpRS = RSList_01.get(0);
//            if(!tmpRS.isClosed())
//            {
//                if(tmpRS.next())
//                    System.out.println("Query 1: id = " + tmpRS.getInt("id"));
//            }
//             
//        } catch (SQLException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        
//    }
}