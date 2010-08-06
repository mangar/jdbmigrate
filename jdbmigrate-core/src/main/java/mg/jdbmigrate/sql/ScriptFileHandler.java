/**
 * 
 */
package mg.jdbmigrate.sql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mg.jdbmigrate.App;
import mg.jdbmigrate.sql.helper.FileContentHelper;

import org.apache.commons.lang.math.NumberUtils;

/**
 * @author marcio.garcia
 * 
 */
public class ScriptFileHandler {

    public static FileContent getMoreRecentFile(List<FileContent> filesContent) {
        FileContent recentFileContent = new FileContent();
        recentFileContent.setVersion(0);
        if (filesContent != null && filesContent.size() > 0) {
            for (FileContent fc : filesContent) {
                if (fc.getVersion() >= recentFileContent.getVersion()) {
                    recentFileContent = fc;
                }
            }
        }
        return recentFileContent;
    }

    public static List<FileContent> getRelevantScriptsForMigration(Integer fromVersion, Integer toVersion) {
        List<FileContent> relevantFilesContent = new ArrayList<FileContent>();

        int minVersion = NumberUtils.min(toVersion, fromVersion, toVersion);
        int maxVersion = NumberUtils.max(fromVersion, fromVersion, toVersion);

        List<String> files = ScriptFileLocator.findScriptsFromDir(App.DEFAULT_BASE_DIR);
        List<FileContent> filesContent = FileContentHelper.createFromFilePaths(files);

        Collections.sort(filesContent);

        for (FileContent fileContent : filesContent) {
            if (ScriptFileHandler.isFileInRange(fileContent, minVersion, maxVersion)) {
                relevantFilesContent.add(fileContent);
            }
        }

        return relevantFilesContent;
    }

    private static boolean isFileInRange(FileContent fileContent, Integer minVersion, Integer maxVersion) {
        Boolean is = Boolean.FALSE;
        if (fileContent.getVersion() >= minVersion && fileContent.getVersion() <= maxVersion) {
            is = Boolean.TRUE;
        }
        return is;
    }

}
