package mg.jdbmigrate;

import mg.jdbmigrate.access.DBConnection;
import mg.jdbmigrate.access.VersionTableHandler;
import mg.jdbmigrate.command.Command;
import mg.jdbmigrate.command.CommandFactory;

/**
 * 
 */
public class App {

    public static final String DEFAULT_BASE_DIR = "src/db";
    public static final Integer DEFAULT_LAST_VERSION = Integer.valueOf(9999);

    DBConnection dbConnection;
    VersionTableHandler versionTableHandler;

    public App() {
        this.dbConnection = DBConnection.createDefault();
        this.versionTableHandler = VersionTableHandler.create(this.dbConnection);
    }

    public void closeDBConnection() {
        this.dbConnection.closeConnection();
    }

    public static void main(String... args) {
        Command command = CommandFactory.createCommand(args);
        if (command != null) {
            command.run();
        } else {

            System.out.println("Usage:");
            System.out.println(" Ex.: java App [command] [param1] [param2]... [paramX]");
            System.out.println("Where [command] can be: db:new, db:migrate");
            System.out.println();
            System.out.println("db:new");
            System.out.println("- db:new output=/dir1/db name=create_user_table");
            System.out.println("It will generate a new file called: [last_seq+1]_create_user_table.sql");
            System.out.println("in /dir/db directory");
            System.out.println("");
            System.out.println("db:migrate");
            System.out.println("- db:migrate to=12");
            System.out.println("will migrate to version 12. ");
            System.out.println("Upgrade and downgrade is automatically calculated by the lib");
            System.out.println("If not specified the 'to' param it will assume the last version (upgrade)");
            System.exit(0);
        }
    }

}
