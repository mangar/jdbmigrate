/**
 * 
 */
package mg.jdbmigrate.sql;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author marcio.garcia
 * 
 */
public class ScriptFileLocator {

    public static List<String> findScriptsFromDir(String baseDir) {
        List<String> fileNames = new ArrayList<String>();

        File dir = new File(baseDir);

        if (dir.isFile()) {
            dir = new File(dir.getParent());
        }        
        
        if (!dir.exists()) {
            throw new RuntimeException("Dir: " + dir + " not found!");
        }

        File[] files = dir.listFiles(new SQLFileFilter());

        for (File f : files) {
            fileNames.add(f.getAbsolutePath());
        }
        return fileNames;
    }

    public static class SQLFileFilter implements FileFilter {
        private final String[] okFileExtensions = new String[] { "sql" };

        public boolean accept(File file) {
            for (String extension : okFileExtensions) {
                if (file.getName().toLowerCase().endsWith(extension)) {
                    return true;
                }
            }
            return false;
        }
    }

}
