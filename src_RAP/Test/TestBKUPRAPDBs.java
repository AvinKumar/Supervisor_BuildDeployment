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
import environmentsetup.BKRAPDBs;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

/**
 *
 * @author Avin
 */

@Listeners({ATUReportsListener.class, ConfigurationListener.class,
    MethodListener.class})

public class TestBKUPRAPDBs 
{
BKRAPDBs bkrap = new BKRAPDBs();
static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TestBKUPRAPDBs.class.getName());
   

@Test
public void TestBKRAPDBs()
    {
        try
        {
       Assert.assertEquals(bkrap.bkpRAPDatabase(),0);
       log.info("Supervisor RAP Databases have been backed up...!!!");
       ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
       ATUReports.add("RAP DBs Backup",false);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            log.info("Problem in backing up Supervisor RAP Databases. Please Investigate...!!!");
            ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
            ATUReports.add("RAP DBs Backup", false);
            Assert.fail("");
        }
    }
}
        
        
        
 
