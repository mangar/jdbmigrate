/**
 * 
 */
package mg.jdbmigrate.command.dbmigrate;

import mg.jdbmigrate.App;
import mg.jdbmigrate.util.MyArrayUtils;

import org.apache.commons.lang.StringUtils;

/**
 * @author marcio.garcia
 * 
 */
public class DbMigrateParams {

    private String toVersion;

    // TEST
    public static DbMigrateParams createFromParams(String... strings) {
        DbMigrateParams instance = new DbMigrateParams();
        instance.toVersion = MyArrayUtils.findParam(strings, "to");

        if (StringUtils.isBlank(instance.toVersion)) {
            instance.toVersion = App.DEFAULT_LAST_VERSION.toString();
        }

        return instance;
    }

    public String getToVersion() {
        return toVersion;
    }

    public void setToVersion(String toVersion) {
        this.toVersion = toVersion;
    }

}
