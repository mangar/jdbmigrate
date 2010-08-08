/**
 * 
 */
package mg.jdbmigrate.command.dbmigrate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import mg.jdbmigrate.access.DBConnection;
import mg.jdbmigrate.access.VersionTableHandler;
import mg.jdbmigrate.command.Command;
import mg.jdbmigrate.sql.FileContent;
import mg.jdbmigrate.sql.ScriptFileHandler;
import mg.jdbmigrate.sql.helper.FileContentHelper;

/**
 * @author marcio.garcia
 * 
 */
public class DbMigrate implements Command {
    private static Logger logger = Logger.getLogger(DbMigrate.class.getName());

    DBConnection dbConnection;
    VersionTableHandler versionTableHandler;
    ExecuteMigrate executeMigrate;

    DbMigrateParams params;

    public DbMigrate(String... params) {
        this.params = DbMigrateParams.createFromParams(params);

        this.dbConnection = DBConnection.createDefault(this.params.getConfigFile());
        this.versionTableHandler = VersionTableHandler.create(this.dbConnection);
        this.executeMigrate = ExecuteMigrate.create(this.dbConnection);
    }

    /*
     * (non-Javadoc)
     * 
     * @see mg.dbmigrate.command.Command#run()
     */
    @Override
    public Map<String, Object> run() {
        logger.info("running db:migrate");
        Map<String, Object> result = this.dbmigrate(Integer.valueOf(this.params.getToVersion()));
        return result;
    }

    // REFACTORY too long... more than one thing
    public Map<String, Object> dbmigrate(Integer toVersion) {
        logger.info("Migrating to version: " + toVersion);

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("toVersion", toVersion);

        Integer actualDBVersion = versionTableHandler.getVersion();
        result.put("actualDBVersion", actualDBVersion);
        logger.info("Actual DB Version: " + actualDBVersion);

        if (actualDBVersion == toVersion) {
            logger.info("Nothing to migrate.");
            result.put("message", "Nothing to migrate.");
        } else {

            List<FileContent> filesContent = ScriptFileHandler.getRelevantScriptsForMigration(actualDBVersion,
                    toVersion, params.getConfigFile());

            if (!filesContent.isEmpty()) {

                List<String> commands = new ArrayList<String>();
                Integer lastVersion;

                if (this.isDowngrade(actualDBVersion, toVersion)) {
                    lastVersion = FileContentHelper.getLastVersionForDowngrade(filesContent);

                    result.put("action", "downgrade");

                    // downs....
                    commands = this.executeMigrate.executeDowns(filesContent);
                    versionTableHandler.updateDBToVersion(lastVersion);
                }

                if (this.isUpgrade(actualDBVersion, toVersion)) {
                    lastVersion = FileContentHelper.getLastVersionForUpgrade(filesContent);

                    result.put("action", "upgrade");

                    // ups.....
                    commands = this.executeMigrate.executeUps(filesContent);
                    versionTableHandler.updateDBToVersion(lastVersion);
                }

                result.put("commands", commands);

            }

        }
        actualDBVersion = versionTableHandler.getVersion();
        result.put("actualDBVersion", actualDBVersion);

        logger.info("Database updated to version " + actualDBVersion);

        return result;
    }

    boolean isUpgrade(Integer fromVersion, Integer toVersion) {
        return fromVersion < toVersion;
    }

    boolean isDowngrade(Integer fromVersion, Integer toVersion) {
        return fromVersion > toVersion;
    }

}
