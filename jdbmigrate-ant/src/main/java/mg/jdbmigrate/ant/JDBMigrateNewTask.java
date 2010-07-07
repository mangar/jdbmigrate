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

	private String configFile;
	private String output;

	public void execute() throws BuildException {
		System.out.println("Config: " + this.configFile);
		System.out.println("Output: " + this.output);
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public void setOutput(String output) {
		this.output = output;
	}

}
