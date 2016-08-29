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
import environmentsetup.BKNPDBs;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

/**
 *
 * @author Avin
 */

@Listeners({ATUReportsListener.class, ConfigurationListener.class,
    MethodListener.class})

public class TestBKUPNPDBs 
{
BKNPDBs bknp = new BKNPDBs();
static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TestBKUPNPDBs.class.getName());
   

@Test
public void TestBKNPDBs()
    {
        try
        {
       Assert.assertEquals(bknp.bkpNPDatabase(),0);
       log.info("Supervisor Nightly Push Databases have been backed up...!!!");
       ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
       ATUReports.add("NP DBs Backup",false);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            log.info("Problem in backing up Supervisor Nightly Push Databases. Please Investigate...!!!");
            ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
            ATUReports.add("NP DBs Backup", false);
            Assert.fail("");
        }
    }
}
