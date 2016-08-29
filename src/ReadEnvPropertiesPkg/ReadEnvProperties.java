package ReadEnvPropertiesPkg;


/**
 *
 * @author Avin
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

public class ReadEnvProperties {

    public final Properties configProp = new Properties();
    static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ReadEnvProperties.class.getName());
    public ReadEnvProperties() {

       
      try {      //Private constructor to restrict new instances
      FileInputStream in = new FileInputStream("conf/config.properties");
    //  System.out.println("Read all properties from file");
      
    	
          configProp.load(in);
          in.close();
      } catch (IOException e) {
		System.out.println("Error in loading properties" + e.getMessage());
       // log.error(e.getMessage());
          
      }
    }

    //Bill Pugh Solution for singleton pattern
    public static class LazyHolder {

        private static final ReadEnvProperties INSTANCE = new ReadEnvProperties();
    }

    public static ReadEnvProperties getInstance() {
        return LazyHolder.INSTANCE;
    }

    public String getProperty(String key) {
        return configProp.getProperty(key);
    }

    public Set<String> getAllPropertyNames() {
        return configProp.stringPropertyNames();
    }

    public boolean containsKey(String key) {
        return configProp.containsKey(key);
    }

}
