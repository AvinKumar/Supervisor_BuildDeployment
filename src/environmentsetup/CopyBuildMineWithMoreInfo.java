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
//import java.io.InputStream;
import java.io.*;

public class CopyBuildMineWithMoreInfo {
     
   public static ReadEnvProperties rp = new ReadEnvProperties();
   //static String khserver = rp.getProperty("kh.server");
     
  public static void main(String[] arg){
      PropConfigurator.configure();
      
    try{
      JSch jsch=new JSch();  
 
      String host=null;
      if(arg.length>0){
        host=arg[0];
      }
      else{
    //    host="username@ipaddress"; // enter username and ipaddress for machine you need to connect
    //      host="prashantm@16.89.90.17";
          host=rp.getProperty("kh.username") + "@" + rp.getProperty("kh.server");
          
      }
      String user=host.substring(0, host.indexOf('@'));
      host=host.substring(host.indexOf('@')+1);
 
      Session session=jsch.getSession(user, host, 22);
     
      // username and password will be given via UserInfo interface.
      UserInfo ui=new MyUserInfo();
      session.setUserInfo(ui);
      session.connect();

//-------------------------------------------------------------------------
// My Notes and rough work 
 //String command=  "ls -ltr"; // enter any command you need to execute
       
 //for i in $((ls -d */) | grep successfulbuild | head -1); do echo ${i%%/}; cd ${i%%/}; done //script to navigate to the latest specified directory.
 
//scp -r *.gz root@16.150.57.108:/clatest //script to copy a specific file extension file.

  
//My String
 //String command=  "cd /store/builds/NIGHTLY_UPLOADS && ls -ltr && cd successfulbuild9.2-04-Apr-2016-06-53 && cd linux && ls -ltr && scp supervisor-full-9.2-04-Apr-2016-06-53.tar.gz root@16.150.57.108:/clatest"; // enter any command you need to execute
//-------------------------------------------------------------------
 
//To copy specific file from specific folder 
 //String command=  "cd /store/builds/NIGHTLY_UPLOADS && for i in $((ls -d */) | grep successfulbuild9.2 | head -1); do echo ${i%%/}; cd ${i%%/}; done && cd linux && ls -ltr && scp -rv *.gz root@16.150.57.108:/clatest"; 
//String command=  "cd /store/builds/NIGHTLY_UPLOADS && for i in $((ls -d */) | grep successfulbuild9.2 | head -1); do echo ${i%%/}; scp -r ${i%%/} root@16.150.57.108:/avinBuild92; done";  

//------------------------------------------------------------------------------------------
//Following is the Test command for OASIB1_KS
//String command=  "cd /store/builds/NIGHTLY_UPLOADS && for i in $((ls -dr */) | grep successfulbuild9.2 | head -1); do echo ${i%%/}; cd ${i%%/}; done && cd linux && ls -ltr && scp -r *.tar root@16.150.57.108:/avin/Build92/"; 

//Following is the Test command for OASIB2_KS
//String command=  "cd /store/builds/NIGHTLY_UPLOADS && for i in $((ls -dr */) | grep successfulbuild9.2 | head -1); do echo ${i%%/}; cd ${i%%/}; done && cd linux && ls -ltr && scp -r *.tar root@16.150.57.113:/avin/builds_92/"; 

//Following is the Test command to find the latest build from Kahuna.
//String command=  "cd /store/builds/NIGHTLY_UPLOADS && for i in $((ls -dr */) | grep successfulbuild9.2 | head -1); do echo ${i%%/}; done";
//-------------------------------------------------------------------------------------------
      
      
      
      
//Following is the actual command. 
//  String command= "cd avin; ./BuildCopy2QASIB2.sh;"; 
String command= rp.getProperty("bld.cmd1") + "&&" + rp.getProperty("bld.cmd2");
   

//-------------------------------------------------------------------
//rsync -r -v --progress -e ssh root@16.150.57.108:/clatest  OR rsync -av --include *.gz root@16.150.57.108:/clatest  //This is not required currently
//      String[] command = new String[2];
//            
//            command[0] = "cd /install/datasets";
//            command[1] = "ls -ltr";

 //--------------------------------------------------------------------     
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
        try{Thread.sleep(1000);}catch(Exception ee){}
      }
      channel.disconnect();
      session.disconnect();
    }
    catch(Exception e){
      System.out.println(e);
    }
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