/**
 * 
 */
package mg.jdbmigrate.sql;

import java.util.ArrayList;
import java.util.List;

/**
 * @author marcio.garcia
 * 
 */
public class FileContent implements Comparable<FileContent> {

    private Integer version;
    private String fileName;
    private List<String> upStatements = new ArrayList<String>();
    private List<String> downStatements = new ArrayList<String>();

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getUpStatements() {
        return upStatements;
    }

    public void setUpStatements(List<String> upStatements) {
        this.upStatements = upStatements;
    }

    public List<String> getDownStatements() {
        return downStatements;
    }

    public void setDownStatements(List<String> downStatements) {
        this.downStatements = downStatements;
    }

    public String toString() {
        return this.version.toString();
    }

    @Override
    public int compareTo(FileContent o) {
        int compare = 0;
        if (o != null) {
            if (o.getVersion() > this.version) {
                compare = -1;
            } else if (o.getVersion() < this.version) {
                compare = 1;
            }
        }
        return compare;
    }

}
