/**
 * 
 */
package mg.jdbmigrate.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author marcio.garcia
 * 
 */
public class PropertiesLoad {

    // TEST
    public static Properties loadDBConfiguration(String filePath) {
        Properties props = new Properties();
        try {
            props = PropertiesLoad.load(filePath);
        } catch (IOException ioe) {
            throw new RuntimeException("Problems to load dbconfiguration properties file: " + filePath);
        }
        return props;
    }

    public static Properties load(String filePath) throws IOException {
        Properties props = new Properties();
        props.load(new FileInputStream(filePath));
        return props;
    }

}
