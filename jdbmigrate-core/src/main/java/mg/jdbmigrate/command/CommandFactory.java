/**
 * 
 */
package mg.jdbmigrate.command;

import mg.jdbmigrate.command.dbmigrate.DbMigrate;
import mg.jdbmigrate.command.dbnew.DbNew;

import org.apache.commons.lang.ArrayUtils;

/**
 * @author marcio.garcia
 * 
 */
public class CommandFactory {

    public static final String NEW_COMMAND = "db:new";
    public static final String NEW_MIGRATE = "db:migrate";

    private String[] params = new String[] {};

    private CommandFactory(String... params) {
        this.params = params;
    }

    // TEST
    public static Command createCommand(String... params) {

        CommandFactory factory = new CommandFactory(params);
        String commandParam = factory.getCommandFromParams();

        Command command = null;

        if (commandParam.equalsIgnoreCase(CommandFactory.NEW_COMMAND)) {
            command = factory.createNew();
        } else if (commandParam.equalsIgnoreCase(CommandFactory.NEW_MIGRATE)) {
            command = factory.createMigrate();
        }

        return command;
    }

    String getCommandFromParams() {
        String command = "";
        if (params != null && !ArrayUtils.isEmpty(params)) {
            command = params[0].trim().toLowerCase();
        }
        return command;
    }

    Command createNew() {
        Command dbNew = new DbNew(this.params);
        return dbNew;
    }

    Command createMigrate() {
        Command dbMigrate = new DbMigrate(this.params);
        return dbMigrate;
    }

    public static class Params {

        private String action;
        private String toVersion;
        private String configFile;

        public Params(String... args) {

        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
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

}
