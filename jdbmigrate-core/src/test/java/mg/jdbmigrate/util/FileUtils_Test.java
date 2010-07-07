/**
 * 
 */
package mg.jdbmigrate.util;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author marcio.garcia
 *
 */
public class FileUtils_Test {

	String file0, file1, file2, file3;
	
	@Before
	public void setUp() throws Exception {
		file0 = new File(this.getClass().getResource("00_f.sql").toURI()).getAbsolutePath();
		file1 = new File(this.getClass().getResource("01_f.sql").toURI()).getAbsolutePath();
		file2 = new File(this.getClass().getResource("02_f.sql").toURI()).getAbsolutePath();
		file3 = new File(this.getClass().getResource("03_f.sql").toURI()).getAbsolutePath();
	}
	
	@Test
	public void getUpStatements_UpAndDown_Test() {
		List<String> ups = FileUtils.getUpStatements(file0);
		System.out.println(ups);
		assertEquals(2, ups.size());
		
		List<String> downs = FileUtils.getDownStatements(file0);
		System.out.println(downs);
		assertEquals(1, downs.size());
	}
	
}
