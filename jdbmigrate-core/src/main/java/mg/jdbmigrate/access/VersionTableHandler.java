/**
 * 
 */
package mg.jdbmigrate.access;

/**
 * @author marcio.garcia
 * 
 */
public class VersionTableHandler {

    DBConnection dbConnection;

    private VersionTableHandler() {

    }

    public static VersionTableHandler create(DBConnection dbConnection) {
        VersionTableHandler instance = new VersionTableHandler();
        instance.dbConnection = dbConnection;
        return instance;
    }

    public Integer getVersion() {
        Integer version = Integer.valueOf(0);

        if (!hasVersionTableInDatabase()) {
            this.createVersionTable();
            this.startVersionTable();
        }

        version = this.dbConnection.getLastDBVersion();
        return version;
    }

    public boolean hasVersionTableInDatabase() {
        Boolean has = Boolean.FALSE;
        has = this.dbConnection.existTable("version");
        return has;
    }

    public static final String CREATE_VERSION_STATEMENT = "create table version(version integer not null)";

    public void createVersionTable() {
        this.dbConnection.execute(VersionTableHandler.CREATE_VERSION_STATEMENT);
    }

    public static final String START_VERSION_TABLE_STATEMENT = "insert into version(version) values(0)";

    public void startVersionTable() {
        this.dbConnection.execute(VersionTableHandler.START_VERSION_TABLE_STATEMENT);
    }

    public static final String LAST_DB_VERSION_STATEMENT = "select version from version";

    public static final String UPDATE_DB_VERSION_STATEMENT = "update version set version = ?";

    // public void updateDBVersion() {
    // String query = VersionTableHandler.UPDATE_DB_VERSION_STATEMENT;
    // query = query.replace("?", "version +1");
    // this.dbConnection.execute(query);
    // }

    public void updateDBToVersion(Integer version) {
        String query = VersionTableHandler.UPDATE_DB_VERSION_STATEMENT;
        query = query.replace("?", version.toString());
        this.dbConnection.execute(query);
    }

}
