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
import environmentsetup.RestoreRAPDBs;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

/**
 *
 * @author Avin
 */

@Listeners({ATUReportsListener.class, ConfigurationListener.class,
    MethodListener.class})

public class TestRestoringRAPDBs 
{
RestoreRAPDBs restore = new RestoreRAPDBs();
static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TestRestoringRAPDBs.class.getName());
   

@Test
public void RestoreRAPDBs()
    {
        try
        {
       Assert.assertEquals(restore.restoreRAPDatabase(),0);
       log.info("Supervisor RAP Databases have been restored successfully...!!!");
       ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
       ATUReports.add("Restored RAP DBs",false);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            log.info("Problem in restoring Supervisor RAP Databases. Please Investigate...!!!");
            ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
            ATUReports.add("Restored RAP DBs", false);
            Assert.fail("");
        }
    }
}
