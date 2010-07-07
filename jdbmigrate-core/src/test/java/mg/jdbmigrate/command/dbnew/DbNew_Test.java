/**
 * 
 */
package mg.jdbmigrate.command.dbnew;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.util.Map;

import mg.jdbmigrate.sql.FileContent;
import mg.jdbmigrate.sql.ScriptFileHandler;
import mg.jdbmigrate.sql.helper.FileContentHelper;
import mg.jdbmigrate.util.FileUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * @author marcio.garcia
 * 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest( { ScriptFileHandler.class, FileContentHelper.class, DbNewParams.class, FileUtils.class })
public class DbNew_Test {

    @Mock
    FileContent recentFileContent;

    @Mock
    DbNewParams params;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        mockStatic(FileContentHelper.class);
        when(FileContentHelper.createFromFilePath(anyString())).thenReturn(null);

        when(recentFileContent.getVersion()).thenReturn(Integer.valueOf(1));

        mockStatic(ScriptFileHandler.class);
        when(ScriptFileHandler.getMoreRecentFile(anyList())).thenReturn(recentFileContent);
    }

    @Test
    public void getNextVersion_Test() {
        DbNew dbNew = Whitebox.newInstance(DbNew.class);
        Whitebox.setInternalState(dbNew, "dbNewParams", params);

        String nextVersion = dbNew.getNextVersion();
        assertEquals("2", nextVersion);
    }

    @Test
    public void run_NoParam_Test() {
        mockStatic(FileUtils.class);
        when(FileUtils.generateFile(anyString(), anyString())).thenReturn(Boolean.TRUE);

        DbNew dbNew = new DbNew(new String[] {});

        Map<String, Object> result = dbNew.run();

        String completePathFileName = (String) result.get("completePathFileName");
        assertEquals("src/db/2.sql", completePathFileName);
        // PowerMock.verify(FileUtils.generateFile(anyString(), anyString()));
    }

    
    @Test
    public void run_Name_Test() {
        mockStatic(FileUtils.class);
        when(FileUtils.generateFile(anyString(), anyString())).thenReturn(Boolean.TRUE);

        DbNew dbNew = new DbNew(new String[] {"name=create_table"});

        Map<String, Object> result = dbNew.run();

        String completePathFileName = (String) result.get("completePathFileName");
        assertEquals("src/db/2_create_table.sql", completePathFileName);
        // PowerMock.verify(FileUtils.generateFile(anyString(), anyString()));
    }    
    
    
    @Test
    public void run_Output_Test() {
        mockStatic(FileUtils.class);
        when(FileUtils.generateFile(anyString(), anyString())).thenReturn(Boolean.TRUE);

        DbNew dbNew = new DbNew(new String[] {"output=/dir/out"});

        Map<String, Object> result = dbNew.run();

        String completePathFileName = (String) result.get("completePathFileName");
        assertEquals("/dir/out/2.sql", completePathFileName);
        // PowerMock.verify(FileUtils.generateFile(anyString(), anyString()));
    }     
    
}
