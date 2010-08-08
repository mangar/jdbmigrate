/**
 * 
 */
package mg.jdbmigrate.ant;

import static org.junit.Assert.assertTrue;
import mg.jdbmigrate.App;

import org.junit.Test;

/**
 * @author mmangar
 * 
 */
public class JDBMigrateTask_Test {

    @Test
    public void test_first_version() {
        String[] params = { "db:migrate", "to=0",
                "config=/Users/mmangar/projects/opensource/jdbmigrate/jdbmigrate-ant/src/db/dbmigrate.properties" };
        App app = new App(params);
        app.run();

        assertTrue(true);
    }

    @Test
    public void test_last_version() {
        String[] params = { "db:migrate",
                "config=/Users/mmangar/projects/opensource/jdbmigrate/jdbmigrate-ant/src/db/dbmigrate.properties" };
        App app = new App(params);
        app.run();

        assertTrue(true);
    }

}
