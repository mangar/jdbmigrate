/**
 * 
 */
package mg.jdbmigrate.command.dbmigrate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.List;
import java.util.Map;

import mg.jdbmigrate.access.DBConnection;
import mg.jdbmigrate.access.VersionTableHandler;
import mg.jdbmigrate.sql.FileContent;
import mg.jdbmigrate.sql.ScriptFileHandler;
import mg.test.FileContentTestUtils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author marcio.garcia
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( { DbMigrateParams.class, DBConnection.class, VersionTableHandler.class, ExecuteMigrate.class,
        ScriptFileHandler.class })
public class DbMigrate_Test {

    @Mock
    DBConnection dbConnection;

    @Mock
    VersionTableHandler versionTableHandler;

    @Mock
    ExecuteMigrate executeMigrate;

    List<FileContent> filesContent10To100;

    @Before
    public void setUp() {
        mockStatic(DBConnection.class);
        when(DBConnection.createDefault()).thenReturn(dbConnection);

        mockStatic(VersionTableHandler.class);
        when(VersionTableHandler.create((DBConnection) any())).thenReturn(versionTableHandler);

        mockStatic(ExecuteMigrate.class);
        when(ExecuteMigrate.create((DBConnection) any())).thenReturn(executeMigrate);

        when(versionTableHandler.getVersion()).thenReturn(50);

        filesContent10To100 = FileContentTestUtils.create(new Integer[] { 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 });
    }

    @Test
    public void sameVersion_50To50_test() {

        DbMigrate dbMigrate = new DbMigrate(new String[] { "to=50" });

        Map<String, Object> result = dbMigrate.run();

        Integer actualVersion = (Integer) result.get("actualDBVersion");
        assertEquals(Integer.valueOf(50), actualVersion);
        assertEquals("Nothing to migrate.", (String) result.get("message"));
    }

    @SuppressWarnings("unchecked")
    @Test
    @Ignore
    public void upgrade_50To100_test() {
        when(versionTableHandler.getVersion()).thenReturn(50);

        mockStatic(ScriptFileHandler.class);
        when(ScriptFileHandler.getRelevantScriptsForMigration(anyInt(), anyInt())).thenReturn(filesContent10To100);

        DbMigrate dbMigrate = new DbMigrate();

        dbMigrate.run();

        verify(executeMigrate).executeUps(anyList());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void upgrade_50To10_test() {
        when(versionTableHandler.getVersion()).thenReturn(50);

        mockStatic(ScriptFileHandler.class);
        when(ScriptFileHandler.getRelevantScriptsForMigration(anyInt(), anyInt())).thenReturn(filesContent10To100);

        DbMigrate dbMigrate = new DbMigrate(new String[] { "to=10" });

        dbMigrate.run();

        verify(executeMigrate).executeDowns(anyList());
    }

}
