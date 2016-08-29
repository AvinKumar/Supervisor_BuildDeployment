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
import environmentsetup.DropRAPDBs;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

/**
 *
 * @author Avin
 */

@Listeners({ATUReportsListener.class, ConfigurationListener.class,
    MethodListener.class})

public class TestDeleteRAPDBs 
{
DropRAPDBs drop = new DropRAPDBs();
static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TestDeleteRAPDBs.class.getName());
   

@Test
public void TestDropRAPDBs()
    {
        try
        {
       Assert.assertEquals(drop.dropRAPDatabase(),0);
       log.info("Supervisor RAP Databases have been deleted...!!!");
       ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
       ATUReports.add("Drop RAP DBs",false);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            log.info("Error deleting Supervisor RAP Databases. Please Investigate...!!!");
            ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
            ATUReports.add("Drop RAP DBs", false);
            Assert.fail("");
        }
    }
}

