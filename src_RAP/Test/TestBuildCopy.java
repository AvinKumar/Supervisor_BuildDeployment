/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Reporting.GetDate;
import static Test.TestBuildCopy.log;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
//import static environmentsetup.CompareMsgPushCount.ActualResults;
//import static environmentsetup.CompareMsgPushCount.ExpectedResults;
import environmentsetup.CopyBuild;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 *
 * @author Avin
 */

@Listeners({ATUReportsListener.class, ConfigurationListener.class,
    MethodListener.class})

public class TestBuildCopy 
{
CopyBuild cb = new CopyBuild();
static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TestBuildCopy.class.getName());
   

@Test
public void TestCopyBuild()
    {
        try
        {
       Assert.assertEquals(cb.BuildCopy(), 0);
       log.info("Successfully copied latest build folder...!!!");
       ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
       ATUReports.add("RAP Build Copy", false);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            log.info("Problem in copying the latest build. Please Investigate...!!! ");
            ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
            ATUReports.add("RAP Build Copy", false);
            Assert.fail("");
        }
    }
}