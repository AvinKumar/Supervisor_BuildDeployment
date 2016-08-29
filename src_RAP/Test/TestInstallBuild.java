/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Reporting.GetDate;
import static Test.TestBuildCopy.log;
import static Test.TestInstallBuild.log;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import environmentsetup.Installapp;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 *
 * @author Avin
 */

@Listeners({ATUReportsListener.class, ConfigurationListener.class,
    MethodListener.class})

public class TestInstallBuild 
    {
Installapp inst = new Installapp();
static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TestInstallBuild.class.getName());
   

@Test
public void TestInstallapp()
    {
        try
        {
       Assert.assertEquals(inst.installation(), 0);
    //   log.info("Installation Task Completed...!!!");
       ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
       ATUReports.add("RAP Build Installation", false);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            log.info("Installation Failed. Please investigate...!!!");
            ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
            ATUReports.add("RAP Build Installation", false);
            Assert.fail("");
        }
    }
}