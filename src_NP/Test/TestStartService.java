/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Reporting.GetDate;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import environmentsetup.StartAppServices;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

/**
 *
 * @author Avin
 */

@Listeners({ATUReportsListener.class, ConfigurationListener.class,
    MethodListener.class})

public class TestStartService 
{
StartAppServices start = new StartAppServices();
static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TestStopService.class.getName());
   

@Test
public void TestStartAppServices()
    {
        try
        {
       Assert.assertEquals(start.startApp(),0);
       log.info("Started Supervisor services...!!!");
       ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
       ATUReports.add("Start Supervisor Services",false);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            log.info("Problem in starting Supervisor services. Please Investigate...!!!");
            ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
            ATUReports.add("Start Supervisor Services", false);
            Assert.fail("");
        }
    }
}
        
        
        
 
