/**
 * 
 */
package mg.jdbmigrate.sql;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author marcio.garcia
 * 
 */
public class ScriptFileLocator_Test {

    String baseDir;

    @Before
    public void setUp() throws Exception {
        baseDir = new File(this.getClass().getResource("00_f.sql").toURI()).getParent();
    }

    @Test
    public void findScriptsFromDir_Test() {
        List<String> scripts = ScriptFileLocator.findScriptsFromDir(baseDir);
        assertEquals(4, scripts.size());
    }

    @Test(expected = RuntimeException.class)
    public void findScriptsFromDir_Fail_Test() {
        ScriptFileLocator.findScriptsFromDir("/some_dir/that/doesnt/exist/here");
    }    
    
    
}
