/**
 * 
 */
package mg.jdbmigrate.ant;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author marcio.garcia
 * 
 */
public class JDBMigrateNewTask extends Task {

    private String toVersion;

    public void execute() throws BuildException {
        System.out.println("To Version: " + this.toVersion);
    }

    public void setToVersion(String toVersion) {
        this.toVersion = toVersion;
    }

}
