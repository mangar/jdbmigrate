/**
 * 
 */
package mg.jdbmigrate.command.dbmigrate;

import java.util.List;

import mg.jdbmigrate.App;
import mg.jdbmigrate.sql.FileContent;
import mg.jdbmigrate.sql.ScriptFileHandler;
import mg.jdbmigrate.sql.helper.FileContentHelper;
import mg.jdbmigrate.util.MyArrayUtils;

import org.apache.commons.lang.StringUtils;

/**
 * @author marcio.garcia
 * 
 */
public class DbMigrateParams {

    private String toVersion;
    private String configFile;

    // TEST
    // REFACTORY
    public static DbMigrateParams createFromParams(String... strings) {

        DbMigrateParams instance = new DbMigrateParams();
        instance.toVersion = MyArrayUtils.findParam(strings, "to");

        instance.configFile = MyArrayUtils.findParam(strings, "config");

        if (StringUtils.isEmpty(instance.configFile)) {
            instance.configFile = App.DEFAULT_BASE_DIR + "/" + App.DEFAULT_PROPERTIES_FILE;
        }

        if (StringUtils.isBlank(instance.toVersion)) {
            List<FileContent> filesContent = FileContentHelper.createFromFilePath(instance.configFile);
            FileContent lastFile = ScriptFileHandler.getMoreRecentFile(filesContent);
            instance.toVersion = String.valueOf(lastFile.getVersion());
        }

        return instance;
    }

    public String getToVersion() {
        return toVersion;
    }

    public void setToVersion(String toVersion) {
        this.toVersion = toVersion;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

}
