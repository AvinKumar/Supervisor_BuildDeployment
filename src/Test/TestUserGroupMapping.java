/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import Reporting.GetDate;
import static Test.TestUserGroupMapping.log;
import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import ldapsync.LDAPSync;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

/**
 *
 * @author Avin
 */

@Listeners({ATUReportsListener.class, ConfigurationListener.class,
    MethodListener.class})

public class TestUserGroupMapping     {
LDAPSync sync = new LDAPSync();
static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(TestUserGroupMapping.class.getName());
   

@Test
public void TestLDAPSync()
    {
        try
        {
       Assert.assertEquals(sync.LDIF(), 0);
       log.info("LDAP Sync completed Successfully...!!!");
       ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
       ATUReports.add("LDAP Sync", false);
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            log.info("Problem in LDAP sync. Please Investigate...!!!");
            ATUReports.setAuthorInfo("Supervisor Automation Team", GetDate.getdate(), "1.0");
            ATUReports.add("LDAP Sync", false);
            Assert.fail("");
        }
    }
}