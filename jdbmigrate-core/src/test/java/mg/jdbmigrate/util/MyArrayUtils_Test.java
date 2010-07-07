/**
 * 
 */
package mg.jdbmigrate.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * @author marcio.garcia
 * 
 */
public class MyArrayUtils_Test {

    String[] params;

    @Before
    public void setUp() {
        params = new String[] { "dir=dir_value", "output=output_value" };
    }

    @Test
    public void findParam_Test() {
        String dir = MyArrayUtils.findParam(params, "dir");
        assertEquals("dir_value", dir);
    }

    @Test
    public void findParam_Null_Test() {
        String dir = MyArrayUtils.findParam(null, "dir");
        assertEquals("", dir);

        dir = MyArrayUtils.findParam(null, null);
        assertEquals("", dir);

        dir = MyArrayUtils.findParam(params, null);
        assertEquals("", dir);
    }
}
