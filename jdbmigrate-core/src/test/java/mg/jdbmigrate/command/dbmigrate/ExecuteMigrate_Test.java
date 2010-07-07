/**
 * 
 */
package mg.jdbmigrate.command.dbmigrate;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;

import mg.jdbmigrate.access.DBConnection;
import mg.jdbmigrate.sql.FileContent;
import mg.test.FileContentTestUtils;

import org.junit.Before;
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
@PrepareForTest( { DBConnection.class })
public class ExecuteMigrate_Test {

    @Mock
    DBConnection dbConnection;

    ExecuteMigrate executeMigrate;
    List<FileContent> filesContent0To100;

    @Before
    public void setUp() {
        filesContent0To100 = FileContentTestUtils.create(new Integer[] { 20, 0, 100, 90, 80, 70, 60, 50, 40, 30 });
        executeMigrate = ExecuteMigrate.create(dbConnection);
    }

    @Test
    public void executeDowns_test() {
        executeMigrate.executeDowns(filesContent0To100);
        verify(dbConnection, times(9)).execute(anyString());
    }

    @Test
    public void executeUps_test() {
        executeMigrate.executeUps(filesContent0To100);
        verify(dbConnection, times(9)).execute(anyString());
    }

}
