/**
 * 
 */
package mg.jdbmigrate.sql;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import mg.test.FileContentTestUtils;

import org.junit.Before;
import org.junit.Test;

/**
 * @author marcio.garcia
 * 
 */
public class ScriptFileHandler_getMoreRecentFile_Test {

    List<FileContent> filesContent;

    @Before()
    public void setUp() {
        filesContent = new ArrayList<FileContent>();
        filesContent.add(FileContentTestUtils.create(10));
        filesContent.add(FileContentTestUtils.create(1));
        filesContent.add(FileContentTestUtils.create(5));
    }

    @Test
    public void getMoreRecentFile_Blank_Test() {
        FileContent fc = ScriptFileHandler.getMoreRecentFile(filesContent);
        assertEquals(Integer.valueOf(10), fc.getVersion());
    }

    @Test
    public void getMoreRecentFile_Null_Test() {
        FileContent fc = ScriptFileHandler.getMoreRecentFile(null);
        assertEquals(Integer.valueOf(0), fc.getVersion());
    }

}
