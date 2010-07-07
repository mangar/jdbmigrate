/**
 * 
 */
package mg.jdbmigrate.sql.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author marcio.garcia
 * 
 */
public class FileContentHelper_GetFileName_Test {

    @Test
    public void getFileName_OK_Test() {
        String fileName = "C:\\wks\\dbmigrate\\src\\db\\0_001snapshot_create_version.sql";
        String fileNameFound = FileContentHelper.getFileName(fileName);
        assertEquals("0_001snapshot_create_version.sql", fileNameFound);

        fileName = "0_001snapshot_create_version.sql";
        fileNameFound = FileContentHelper.getFileName(fileName);
        assertEquals("0_001snapshot_create_version.sql", fileNameFound);
    }

    @Test
    public void getFileName_Blank_Test() {
        String fileNameFound = FileContentHelper.getFileName("");
        assertEquals("", fileNameFound);

        fileNameFound = FileContentHelper.getFileName(null);
        assertEquals("", fileNameFound);
    }

}
