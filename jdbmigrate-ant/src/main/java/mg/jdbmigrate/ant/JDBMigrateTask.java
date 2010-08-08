/**
 * 
 */
package mg.jdbmigrate.ant;

import mg.jdbmigrate.App;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * @author marcio.garcia
 * 
 */
public class JDBMigrateTask extends Task {

    private String to;
    private String config;

    // TEST
    public void execute() throws BuildException {
        System.out.println("To Version: " + this.to);
        System.out.println("Config: " + this.config);

        String[] params = this.createParameters();
        
        for (String arg : params) {
            System.out.println("Param: " + arg);
        }

        
        App app = new App(params);
        app.run();

    }

    // TEST
    String[] createParameters() {
        String[] params = new String[] { "db:migrate", getTo(), getConfig() };
        return params;
    }

    // TEST
    String getTo() {
        String to = "";
        if (!this.isEmpty(this.to)) {
            to = "to=" + this.to;
        }
        return to;
    }

    // TEST
    String getConfig() {
        String config = "";
        if (!this.isEmpty(this.config)) {
            config = "config=" + this.config;
        }
        return config;
    }

    Boolean isEmpty(String value) {
        Boolean is = Boolean.TRUE;
        is = (value == null || value.trim().length() == 0);
        return is;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setConfig(String config) {
        this.config = config;
    }

}
