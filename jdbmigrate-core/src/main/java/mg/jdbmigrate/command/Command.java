/**
 * 
 */
package mg.jdbmigrate.command;

import java.util.Map;

/**
 * @author marcio.garcia
 * 
 */
public interface Command {

    public Map<String, Object> run();

}
