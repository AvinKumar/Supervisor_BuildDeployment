package environmentsetup;

/**
 *
 * @author Avin
 */

import ReadEnvPropertiesPkg.ReadEnvProperties;
import com.jcraft.jsch.*;
import java.io.*;
import org.apache.log4j.Logger;

public class StartAppServices {
     
   public static ReadEnvProperties rp = new ReadEnvProperties();
   static Logger log = Logger.getLogger(StartAppServices.class.getName());

   //static String khserver = rp.getProperty("kh.server");
     
//  public static void main(String[] arg){
    public int startApp()
    {
    
    try{
      JSch jsch=new JSch();  
      String host=null;
//      if(arg.length>0){
//        host=arg[0];
//      }
//      else{
    //    host="username@ipaddress"; // enter username and ipaddress for machine you need to connect
    //      host="prashantm@16.89.90.17";
          host=rp.getProperty("ks.username") + "@" + rp.getProperty("ks.server");
          
//      }
      String user=host.substring(0, host.indexOf('@'));
      host=host.substring(host.indexOf('@')+1);
 
      Session session=jsch.getSession(user, host, 22);
     
      // username and password will be given via UserInfo interface.
      UserInfo ui=new MyUserInfo();
      session.setUserInfo(ui);
      session.connect();
      


//Add Stop(Services_Stop.sh) and Start(Services_Start.sh) shell scripts to the below command appropriately before executing this file.
String command= "cd /avin/avin_shellscripts; pwd; ./Services_Start.sh;";


      Channel channel=session.openChannel("exec");
      ((ChannelExec)channel).setCommand(command);
 
      
      channel.setInputStream(null);
 
      ((ChannelExec)channel).setErrStream(System.err);
 
      InputStream in=channel.getInputStream();
 
      channel.connect();
 
      byte[] tmp=new byte[1024];
      while(true){
        while(in.available()>0){
          int i=in.read(tmp, 0, 1024);
          if(i<0)break;
          System.out.print(new String(tmp, 0, i));
        }
        if(channel.isClosed()){
          System.out.println("exit-status: "+channel.getExitStatus());
          break;
        }
        try
        {
            Thread.sleep(1000);
        }
        catch(Exception ee)
        {
            System.out.println(ee);
        }
      }
      channel.disconnect();
      session.disconnect();
    }
    catch(Exception e)
    {
      System.out.println(e);
      return -1;
    }
    return 0;
  }
 
  public static class MyUserInfo implements UserInfo{
    public String getPassword(){ return passwd; }
    public boolean promptYesNo(String str){
        str = "Yes";
        return true;}
   
    String passwd;
 
    public String getPassphrase(){ return null; }
    public boolean promptPassphrase(String message){ return true; }
    public boolean promptPassword(String message){
    //    passwd="password"; // enter the password for the machine you want to connect.
        passwd=rp.getProperty("ks.password");
        return true;
    }
    public void showMessage(String message){
    
    }
  
    }
  }