/**
 * 
 */
package mg.jdbmigrate.command.dbnew;

import mg.jdbmigrate.App;
import mg.jdbmigrate.util.MyArrayUtils;

import org.apache.commons.lang.StringUtils;

/**
 * @author marcio.garcia
 * 
 */
public class DbNewParams {

    private String output;
    private String name;

    DbNewParams() {

    }

    public static DbNewParams createFromParams(String... strings) {
        DbNewParams instance = new DbNewParams();
        instance.output = MyArrayUtils.findParam(strings, "output");

        if (StringUtils.isBlank(instance.output)) {
            instance.output = App.DEFAULT_BASE_DIR;
        }

        instance.name = MyArrayUtils.findParam(strings, "name");
        return instance;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
