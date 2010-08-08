/**
 * 
 */
package mg.jdbmigrate.access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

import mg.jdbmigrate.App;
import mg.jdbmigrate.util.PropertiesLoad;

import org.apache.commons.lang.StringUtils;

/**
 * @author marcio.garcia
 * 
 */
public class DBConnection {

    private static Logger logger = Logger.getLogger(DBConnection.class.getName());

    private String driver;
    private String url;
    private String username;
    private String pwd;
    private Connection connection;

    DBConnection() {

    }

    public static DBConnection createDefault(String file) {
        DBConnection instance = new DBConnection();

        Properties props = instance.getPropertiesFromFile(file);

        instance.driver = props.getProperty("driver_class");
        instance.url = props.getProperty("url");
        instance.username = props.getProperty("username");
        instance.pwd = props.getProperty("password");

        instance.connection = instance.connect();

        return instance;
    }

    // TEST
    Properties getPropertiesFromFile(String file) {
        Properties props = null;

        if (StringUtils.isEmpty(file)) {
            props = PropertiesLoad.loadDBConfiguration(App.DEFAULT_BASE_DIR + "/" + App.DEFAULT_PROPERTIES_FILE);
        } else {
            props = PropertiesLoad.loadDBConfiguration(file);
        }

        return props;
    }

    public static DBConnection create(String driver, String url, String username, String pwd) {
        DBConnection instance = new DBConnection();
        instance.driver = driver;
        instance.url = url;
        instance.username = username;
        instance.pwd = pwd;

        instance.connection = instance.connect();

        return instance;
    }

    // TEST
    Connection connect() {
        logger.info("connecting to database (" + this.url + ")");
        Connection connection = null;
        try {
            String driverName = this.driver;
            Class.forName(driverName);

            String url = this.url;
            String username = this.username;
            String password = this.pwd;
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException("Problems to connect to the Database (" + this.driver + " / " + this.url + " / "
                    + this.username + ": " + e.toString());
        }
        return connection;
    }

    // TEST
    public void closeConnection() {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                this.connection.close();
            }
        } catch (Exception e) {
            logger.warning("Problems to close db connection: " + e.toString());
        }
    }

    // TEST
    public Boolean execute(String statement) {
        logger.info("executing statemant: " + statement);
        Boolean response = Boolean.FALSE;
        try {
            Statement stmt = this.connection.createStatement();
            stmt.execute(statement);
        } catch (Exception e) {
            logger.severe("Problem executing the statement: " + statement + " >> " + e.toString());
            throw new RuntimeException("Problem executing the statement: " + statement + " >> " + e.toString());
        }
        return response;
    }

    // TEST
    public Integer getLastDBVersion() {
        Integer response = Integer.valueOf(0);
        try {
            PreparedStatement pstmt = this.connection.prepareStatement(VersionTableHandler.LAST_DB_VERSION_STATEMENT);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                response = rs.getInt(1);
            }
        } catch (Exception e) {
            logger.severe("Problem executing the prepared statement: " + VersionTableHandler.LAST_DB_VERSION_STATEMENT
                    + " >> " + e.toString());
            throw new RuntimeException("Problem executing the prepared statement: "
                    + VersionTableHandler.LAST_DB_VERSION_STATEMENT + " >> " + e.toString());
        }
        return response;
    }

    public Boolean existTable(String tableName) {
        Boolean exist = Boolean.FALSE;
        try {
            PreparedStatement pstmt = this.connection.prepareStatement("show tables like ?;");
            pstmt.setString(1, tableName);
            ResultSet rs = pstmt.executeQuery();

            if ((rs.next())) {
                exist = Boolean.TRUE;
            }

        } catch (Exception e) {
            throw new RuntimeException("Problem executing the prepared statement: show tables like ?' >> "
                    + e.toString());
        }

        return exist;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
