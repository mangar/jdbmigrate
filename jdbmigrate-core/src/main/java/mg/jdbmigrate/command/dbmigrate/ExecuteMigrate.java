/**
 * 
 */
package mg.jdbmigrate.command.dbmigrate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import mg.jdbmigrate.access.DBConnection;
import mg.jdbmigrate.sql.FileContent;

/**
 * @author marcio.garcia
 * 
 */
public class ExecuteMigrate {

    DBConnection dbConnection;

    private static Logger logger = Logger.getLogger(ExecuteMigrate.class.getName());

    private ExecuteMigrate() {

    }

    public static ExecuteMigrate create(DBConnection dbConnection) {
        ExecuteMigrate instance = new ExecuteMigrate();
        instance.dbConnection = dbConnection;
        return instance;
    }

    // REFACTORY
    public List<String> executeDowns(List<FileContent> filesContent) {
        logger.info("executeDowns.init");
        List<String> commands = new ArrayList<String>();

        filesContent = this.sortAndRemoveLastIfGtThanZero(filesContent);

        Collections.reverse(filesContent);

        for (FileContent fc : filesContent) {
            logger.info("Downing to version " + fc.getVersion() + " (" + fc.getFileName() + ")");
            for (int i = 0; i < fc.getDownStatements().size(); i++) {
                String statement = fc.getDownStatements().get(i);
                logger.info(" " + i + " - " + statement);
                this.dbConnection.execute(statement);
                commands.add(statement);
            }
        }

        return commands;
    }

    // REFACTORY
    public List<String> executeUps(List<FileContent> filesContent) {
        logger.info("executeUps.init");
        List<String> commands = new ArrayList<String>();

        filesContent = this.sortAndRemoveLastIfGtThanZero(filesContent);

        for (FileContent fc : filesContent) {
            logger.info("Uping to version " + fc.getVersion() + " (" + fc.getFileName() + ")");
            for (int i = 0; i < fc.getUpStatements().size(); i++) {
                logger.info(" " + i + " - " + fc.getUpStatements().get(i));
                String statement = fc.getUpStatements().get(i);
                logger.info(" " + i + " - " + statement);
                this.dbConnection.execute(statement);

                commands.add(statement);
            }
        }

        return commands;
    }

    List<FileContent> sortAndRemoveLastIfGtThanZero(List<FileContent> filesContent) {
        Collections.sort(filesContent);
        if (filesContent.size() > 1) {
            filesContent.remove(0);
        }
        return filesContent;
    }

}
