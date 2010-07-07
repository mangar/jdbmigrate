package mg.test;

import java.util.ArrayList;
import java.util.List;

import mg.jdbmigrate.sql.FileContent;

public class FileContentTestUtils {

    public static FileContent create(int version) {
        FileContent fc = new FileContent();
        fc.setVersion(version);

        List<String> downStatements = new ArrayList<String>();
        downStatements.add("down::" + version);

        fc.setDownStatements(downStatements);

        List<String> upStatements = new ArrayList<String>();
        upStatements.add("up::" + version);

        fc.setUpStatements(upStatements);

        return fc;
    }

    public static List<FileContent> create(Integer... versions) {
        List<FileContent> content = new ArrayList<FileContent>();
        for (Integer v : versions) {
            content.add(FileContentTestUtils.create(v));
        }
        return content;
    }

}
