/**
 * 
 */
package mg.jdbmigrate.sql.helper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * @author marcio.garcia
 * 
 */
public class FileContentHelper_GetVersion_Test {

    @Test
    public void getVersion_OK_Test() {
        String fileName = "C:\\wks\\dbmigrate\\src\\db\\0_001snapshot_create_version.sql";
        Integer version = FileContentHelper.getVersion(fileName);
        assertTrue(0 == version);

        fileName = "0_001snapshot_create_version.sql";
        version = FileContentHelper.getVersion(fileName);
        assertTrue(0 == version);
    }

    @Test
    public void getVersion_DefaultVersion_FailToConvert_Test() {
        String fileName = "C:\\wks\\dbmigrate\\src\\db\\001snapshot_create_version.sql";
        Integer version = FileContentHelper.getVersion(fileName);
        assertEquals(FileContentHelper.VERSION, version);

        fileName = "001snapshot_create_version.sql";
        version = FileContentHelper.getVersion(fileName);
        assertEquals(FileContentHelper.VERSION, version);

        fileName = "version.sql";
        version = FileContentHelper.getVersion(fileName);
        assertEquals(FileContentHelper.VERSION, version);

        version = FileContentHelper.getVersion(null);
        assertEquals(FileContentHelper.VERSION, version);
    }

}
