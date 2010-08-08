/**
 * 
 */
package mg.jdbmigrate.sql;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.ArrayList;
import java.util.List;

import mg.jdbmigrate.sql.helper.FileContentHelper;
import mg.test.FileContentTestUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author marcio.garcia
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( { ScriptFileLocator.class, FileContentHelper.class })
public class ScriptFileHandler_getRelevantScriptsForMigration_Test {

    List<FileContent> filesContent;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {

        mockStatic(ScriptFileLocator.class);
        when(ScriptFileLocator.findScriptsFromDir(anyString())).thenReturn(null);

        filesContent = new ArrayList<FileContent>();
        filesContent.add(FileContentTestUtils.create(10));
        filesContent.add(FileContentTestUtils.create(1));
        filesContent.add(FileContentTestUtils.create(5));

        mockStatic(FileContentHelper.class);
        when(FileContentHelper.createFromFilePaths(anyList())).thenReturn(filesContent);

    }

    @Test
    public void one_element() {
        Integer fromVersion = Integer.valueOf(10);
        Integer toVersion = Integer.valueOf(100);

        List<FileContent> contents = ScriptFileHandler.getRelevantScriptsForMigration(fromVersion, toVersion, "./");
        assertEquals(1, contents.size());
    }

}
