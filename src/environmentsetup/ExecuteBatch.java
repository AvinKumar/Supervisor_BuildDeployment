/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package environmentsetup;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Avin
 */
public class ExecuteBatch {
    
 public static void main(String[] arg){
 
Runtime runtime = Runtime.getRuntime();
try {
    Process p1 = runtime.exec("cmd /c start E:\\Automation\\POCs_Avin\\memory1.bat");
//    Process p1 = runtime.exec("cmd /c memory1.bat" + "E:\\Automation\\POCs_Avin\\");
    InputStream is = p1.getInputStream();
    int i = 0;
    while( (i = is.read() ) != -1) {
        System.out.print((char)i);
    }
} catch(IOException ioException) {
    System.out.println(ioException.getMessage() );
}
    
}
}

