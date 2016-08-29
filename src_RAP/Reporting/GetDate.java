package Reporting;


/**
 *
 * @author Avin
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDate {
    //public static String currentdate;
    public  static String getdate()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
       Date date = new Date();
         return dateFormat.format(date);
    }
    
}
