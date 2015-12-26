package sklcc.ws.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by sukai on 15/11/10.
 */
public class DatabaseUtil {

    private static Logger logger = LogManager.getLogger(DatabaseUtil.class.getSimpleName());

    public static Connection getMysqlConnection(String ip, String database, String userName, String password) {
        String url = "jdbc:mysql://" + ip + ":3306/" + database
                + "?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, userName, password);
            logger.info("getMysqlConnection success");
        } catch (Exception e) {
            logger.error("getMysqlConnection Error." + e.getMessage());
        }
        return connection;
    }

    public static boolean testMysqlConnection(String ip, String database, String userName, String password) {
        try {
            Connection connection = getMysqlConnection(ip, database, userName, password);
            connection.close();
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}

