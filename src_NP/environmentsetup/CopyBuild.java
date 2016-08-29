package environmentsetup;

/**
 *
 * @author Avin
 */
import Log4j.PropConfigurator;
import ReadEnvPropertiesPkg.ReadEnvProperties;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import java.io.*;

public class CopyBuild {
     
   public static ReadEnvProperties rp = new ReadEnvProperties();
   //static String khserver = rp.getProperty("kh.server");
    
   public int BuildCopy (){
//  public static void main(String[] arg){
      PropConfigurator.configure();
      
    try{
      JSch jsch=new JSch();  
 
      String host=null;
//      if(arg.length>0){
//        host=arg[0];
//      }
//      else{
    //    host="username@ipaddress"; // enter username and ipaddress for machine you need to connect
    //      host="prashantm@16.89.90.17";
          host=rp.getProperty("kh.username") + "@" + rp.getProperty("kh.server");
          
      
      String user=host.substring(0, host.indexOf('@'));
      host=host.substring(host.indexOf('@')+1);
 
      Session session=jsch.getSession(user, host, 22);
     
      // username and password will be given via UserInfo interface.
      UserInfo ui=new MyUserInfo();
      session.setUserInfo(ui);
      session.connect();
      
      System.out.println("Shell Scripts will be executed from 'prashantm@KAHUNA:/avin/avin_shellscripts/' location");

       
//Following is the actual command. 
//  String command= "cd avin; ./BuildCopy2QASIB1.sh;"; 
String command= rp.getProperty("bld.cmd1") + "&&" + rp.getProperty("bld.cmd2");
   

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
        passwd=rp.getProperty("kh.password");
        return true;
    }
    public void showMessage(String message){
    
    }
  
    }
  }