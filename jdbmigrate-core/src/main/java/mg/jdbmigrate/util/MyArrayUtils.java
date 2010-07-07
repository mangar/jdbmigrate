/**
 * 
 */
package mg.jdbmigrate.util;

/**
 * @author marcio.garcia
 *
 */
public class MyArrayUtils {

    public static String findParam(String[] param, String key) {
        return MyArrayUtils.findParam(param, key, "=");
    }

    public static String findParam(String[] param, String key, String splitToken) {
        String value = "";
        if (param != null && key != null) {
            for (String p : param) {
                if (p != null) {
                    if (p.startsWith(key + splitToken)) {
                        String completeKey = key + splitToken;
                        value = p.substring( p.indexOf(completeKey) + completeKey.length());
                    }
                }
            }
        }
        return value;
    }    
    

    
}
