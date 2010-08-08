/**
 * 
 */
package mg.jdbmigrate.sql.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mg.jdbmigrate.sql.FileContent;
import mg.jdbmigrate.sql.ScriptFileLocator;
import mg.jdbmigrate.util.FileUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

/**
 * @author marcio.garcia
 * 
 */
public class FileContentHelper {

    public static final Integer VERSION = Integer.valueOf(999);

    // TEST
    public static List<FileContent> createFromFilePath(String path) {        
        List<String> scriptFilePaths = ScriptFileLocator.findScriptsFromDir(path);
        List<FileContent> fileContents = FileContentHelper.createFromFilePaths(scriptFilePaths);
        return fileContents;
    }

    // TEST
    public static List<FileContent> createFromFilePaths(List<String> files) {
        List<FileContent> fileContents = new ArrayList<FileContent>();

        if (files != null) {
            for (String file : files) {
                FileContent fc = FileContentHelper.createFromFile(file);
                fileContents.add(fc);
            }
        }

        return fileContents;
    }

    // TEST
    public static FileContent createFromFile(String filePath) {
        FileContent instance = new FileContent();

        instance.setVersion(FileContentHelper.getVersion(filePath));
        instance.setFileName(FileContentHelper.getFileName(filePath));

        instance.setUpStatements(FileUtils.getUpStatements(filePath));
        instance.setDownStatements(FileUtils.getDownStatements(filePath));

        return instance;
    }

    static Integer getVersion(String path) {
        Integer version = FileContentHelper.VERSION;
        if (!StringUtils.isBlank(path)) {

            String fileName = FileContentHelper.getFileName(path);
            int stUnderPosition = StringUtils.indexOf(fileName, "_");

            if (stUnderPosition >= 0) {
                String v = fileName.substring(0, stUnderPosition);
                v = v.substring(v.length()-1);
                version = NumberUtils.toInt(v, FileContentHelper.VERSION);
            }
        }

        return version;
    }

    static String getFileName(String path) {
        String fileName = "";
        if (!StringUtils.isBlank(path)) {
            int lastSlashForFile = StringUtils.lastIndexOf(path, "\\");
            fileName = path.substring(lastSlashForFile + 1);
        }
        return fileName;
    }

    public static Integer getLastVersionForUpgrade(List<FileContent> filesContent) {
        Integer lastVersion = Integer.valueOf(0);
        Collections.sort(filesContent);
        if (!filesContent.isEmpty()) {
            FileContent fc = filesContent.get(filesContent.size() - 1);
            lastVersion = fc.getVersion();
        }
        return lastVersion;
    }

    public static Integer getLastVersionForDowngrade(List<FileContent> filesContent) {
        Integer lastVersion = Integer.valueOf(0);

        Collections.sort(filesContent);

        if (!filesContent.isEmpty()) {
            FileContent fc = filesContent.get(0);
            lastVersion = fc.getVersion();
        }

        return lastVersion;
    }

}
