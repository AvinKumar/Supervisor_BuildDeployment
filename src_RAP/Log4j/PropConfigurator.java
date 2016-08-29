package Log4j;

/**
 *
 * @author Avin
 */


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class PropConfigurator {
    static Logger log = Logger.getLogger(PropConfigurator.class.getName());
    public static void configure() {
        PropertyConfigurator.configure("conf/log4j.properties");
        
    }
}
