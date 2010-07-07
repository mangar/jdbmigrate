/**
 * 
 */
package mg.jdbmigrate.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author marcio.garcia
 * 
 */
public class FileUtils {

    static final String UP = "up:";
    static final String DOWN = "down:";

    // // TEST
    // public static String getFileContent(String path) {
    // StringBuilder content = new StringBuilder();
    // try {
    // BufferedReader in = new BufferedReader(new FileReader(path));
    // String str;
    // while ((str = in.readLine()) != null) {
    // content.append(str);
    // }
    // in.close();
    // } catch (IOException e) {
    // throw new RuntimeException("Problem geting content from file " + path +
    // ": " + e.toString());
    // }
    // return content.toString();
    // }

    public static List<String> getUpStatements(String path) {
        return FileUtils.getStatements(path, FileUtils.UP, FileUtils.DOWN);
    }

    public static List<String> getDownStatements(String path) {
        return FileUtils.getStatements(path, FileUtils.DOWN, FileUtils.UP);
    }

    public static List<String> getStatements(String path, String keyToGet, String keyToNotGet) {
        List<String> statements = new ArrayList<String>();
        try {
            boolean nextSection = false;
            BufferedReader in = new BufferedReader(new FileReader(path));

            StringBuilder statement = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {

                if (nextSection && FileUtils.isAValidStatementLine(line)) {

                    statement.append(line);
                    if (FileUtils.isEndOfStatement(line)) {
                        statements.add(statement.toString());
                        statement = new StringBuilder();
                    }

                }
                if (StringUtils.equals(line, keyToGet)) {
                    nextSection = true;
                }
                if (StringUtils.equals(line, keyToNotGet)) {
                    nextSection = false;
                }
            }
            in.close();
        } catch (IOException e) {
            throw new RuntimeException("Problem getting content from file " + path + ": " + e.toString());
        }
        return statements;
    }

    private static boolean isAValidStatementLine(String line) {
        boolean isValid = Boolean.FALSE;
        if (!StringUtils.isBlank(line) && !line.equals(FileUtils.DOWN) && !line.equals(FileUtils.UP)) {
            isValid = Boolean.TRUE;
        }
        return isValid;
    }

    private static Boolean isEndOfStatement(String line) {
        boolean isEnd = line.trim().endsWith(";");
        return isEnd;
    }

    public static boolean generateFile(String fileName, String content) {
        Boolean created = Boolean.FALSE;
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(content);
            out.close();

            created = Boolean.TRUE;
        } catch (IOException e) {
            throw new RuntimeException("Problems writing the file: " + fileName);
        }
        return created;
    }

}
