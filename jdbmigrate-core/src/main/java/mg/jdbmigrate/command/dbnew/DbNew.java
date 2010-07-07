/**
 * 
 */
package mg.jdbmigrate.command.dbnew;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import mg.jdbmigrate.App;
import mg.jdbmigrate.command.Command;
import mg.jdbmigrate.sql.FileContent;
import mg.jdbmigrate.sql.ScriptFileHandler;
import mg.jdbmigrate.sql.helper.FileContentHelper;
import mg.jdbmigrate.util.FileUtils;

import org.apache.commons.lang.StringUtils;

/**
 * @author marcio.garcia
 * 
 */
public class DbNew implements Command {

    public static final String DEFAULT_BASE_DIR = App.DEFAULT_BASE_DIR;

    private static Logger logger = Logger.getLogger(DbNew.class.getName());

    private DbNewParams dbNewParams;

    public DbNew(String... params) {
        this.dbNewParams = DbNewParams.createFromParams(params);
    }

    @Override
    public Map<String, Object> run() {
        logger.info("running command db:new");

        // 1 - last sequence
        String nextVersion = this.getNextVersion();
        String fileName = this.createFileNameAndPath(nextVersion);
        String content = this.defaultContent();

        // 4 - create the file..
        String completePathFileName = this.dbNewParams.getOutput() + "/" + fileName;
        Boolean created = FileUtils.generateFile(completePathFileName, content);

        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("fileCreated", created);
        ret.put("fileName", fileName);
        ret.put("completePathFileName", completePathFileName);
        ret.put("content", content);

        return ret;
    }

    String createFileNameAndPath(String sequence) {
        logger.info("createFileNameAndPath.init > sequence : " + sequence);

        StringBuilder fileName = new StringBuilder();

        fileName.append(sequence);
        if (!StringUtils.isBlank(dbNewParams.getName())) {
            fileName.append("_").append(dbNewParams.getName());
        }
        fileName.append(".sql");

        logger.info("createFileNameAndPath.returning > " + fileName);
        return fileName.toString();
    }

    String getNextVersion() {
        List<FileContent> filesContent = FileContentHelper.createFromFilePath(dbNewParams.getOutput());
        FileContent recentFileContent = ScriptFileHandler.getMoreRecentFile(filesContent);

        int version = recentFileContent.getVersion();
        version++;

        return String.valueOf(version);
    }

    String defaultContent() {
        StringBuilder content = new StringBuilder();
        content.append("up:").append("\n").append("\n");
        content.append("down:").append("\n").append("\n");
        return content.toString();
    }

}
