/**
 * 
 */
package mg.jdbmigrate.command;

import java.util.List;

import mg.jdbmigrate.command.dbmigrate.DbMigrate;
import mg.jdbmigrate.command.dbnew.DbNew;
import mg.jdbmigrate.sql.FileContent;

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

}
