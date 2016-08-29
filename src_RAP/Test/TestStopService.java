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
import environmentsetup.StopAppServices;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

/**
 *
 * @author Avin
 */

@Listeners({ATUReportsListener.class, ConfigurationListener.class,
    MethodListener.class})

public class TestStopService 
{
StopAppServices stop = new StopAppServices();
static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TestStopService.class.getName());
   

@Test
public void TestStopAppServices()
    {
        try
        {
       Assert.assertEquals(stop.stopApp(), 0);
       log.info("Stopped Supervisor services...!!!");
       ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
       ATUReports.add("Stop Supervisor Services", false);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            log.info("Problem in stopping Supervisor services. Please Investigate...!!!");
            ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
            ATUReports.add("Stop Supervisor Services", false);
            Assert.fail("");
        }
    }
}
        
        
        
 
