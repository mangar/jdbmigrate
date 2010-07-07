/**
 * 
 */
package mg.jdbmigrate.sql.helper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import mg.jdbmigrate.sql.FileContent;

import org.junit.Before;
import org.junit.Test;

/**
 * @author marcio.garcia
 * 
 */
public class FileContentHelper_getLastVersionFor {

    List<FileContent> filesContent;

    @Before
    public void setUp() {
        filesContent = new ArrayList<FileContent>();
        filesContent.add(this.create(100));
        filesContent.add(this.create(110));
        filesContent.add(this.create(10));
        filesContent.add(this.create(5));
        filesContent.add(this.create(60));
    }

    FileContent create(int version) {
        FileContent fc = new FileContent();
        fc.setVersion(version);
        return fc;
    }

    @Test
    public void getLastVersionForUpgrade_Test() {
        assertEquals(Integer.valueOf(110), FileContentHelper.getLastVersionForUpgrade(filesContent));
    }

    @Test
    public void getLastVersionForDowngrade_Test() {
        assertEquals(Integer.valueOf(5), FileContentHelper.getLastVersionForDowngrade(filesContent));
    }

}
