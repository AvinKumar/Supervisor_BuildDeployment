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
import environmentsetup.RestoreNPDBs;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

/**
 *
 * @author Avin
 */

@Listeners({ATUReportsListener.class, ConfigurationListener.class,
    MethodListener.class})

public class TestRestoringNPDBs 
{
RestoreNPDBs restore = new RestoreNPDBs();
static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TestRestoringNPDBs.class.getName());
   

@Test
public void RestoreNPDBs()
    {
        try
        {
       Assert.assertEquals(restore.restoreNPDatabase(),0);
       log.info("Supervisor Nightly Push Databases have been restored successfully...!!!");
       ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
       ATUReports.add("Restored NP DBs",false);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            log.info("Problem in restoring Supervisor Nightly Push Databases. Please Investigate...!!!");
            ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
            ATUReports.add("Restored NP DBs", false);
            Assert.fail("");
        }
    }
}
