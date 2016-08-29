/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import comparepush.CompareMsgPushCount;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
//import org.testng.annotations.AfterMethod;
//import org.testng.annotations.AfterSuite;
//import org.testng.annotations.AfterTest;
//import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.BeforeSuite;
//import org.testng.annotations.Test;
//import org.testng.annotations.BeforeTest;



/**
 *
 * @author Avin
 */
@Listeners({ATUReportsListener.class, ConfigurationListener.class,
    MethodListener.class})

public class TestMessagePush 
{
    CompareMsgPushCount csql = new CompareMsgPushCount();
    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TestMessagePush.class.getName());

    
//    @BeforeTest
//    public void testdbconnect()
//    {
//        try
//        {
//            csql.dbConnect();
//        }    
//        
//        catch (Exception e)
//        {
//            System.out.println(e.getMessage());
//            log.info("Error in connecting to DB server");
//        }
//    }
    
    @Test
    public void testcompareMsgPushmethod()
    {
        try
        {
       csql.dbConnect();     
       csql.compareMsgPush();
        }
        catch (Exception e)
        {
            System.out.println("-- Some problem occurred while comparing Message Push count--------");
            System.out.println(e.getMessage());
            log.info("Problem in executing compareMsgPush");
        }
    }
    
}
