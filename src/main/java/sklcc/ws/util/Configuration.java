package sklcc.ws.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sukai on 15/11/10.
 */
public class Configuration {

    public static String publicerFetchUrl;
    public static String pubDailyUrl;
    public static String pubArticleUrl;
    public static String pubWeekUrl;
    public static String pubGroupPubUrl;
    public static String pubGroupInfoUrl;
    public static String appId;
    public static String appKey;
    public static String mysqlIp;
    public static String mysqlDB;
    public static String mysqlUsr;
    public static String mysqlPass;
    public static String beginDate;
    public static String endDate;
    public static int switcher;
    public static int crawlFlag;
    public static int weekFlag;
    public static int cleanFlag;

    public Configuration(String file) throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(file);
        Properties properties = new Properties();
        properties.load(inputStream);
        publicerFetchUrl = properties.getProperty("publicerFetchUrl");
        pubDailyUrl = properties.getProperty("pubDailyUrl");
        pubArticleUrl = properties.getProperty("pubArticleUrl");
        pubWeekUrl = properties.getProperty("pubWeekUrl");
        pubGroupPubUrl = properties.getProperty("pubGroupPubUrl");
        pubGroupInfoUrl = properties.getProperty("pubGroupInfoUrl");
        appId = properties.getProperty("appId");
        appKey = properties.getProperty("appKey");
        mysqlIp = properties.getProperty("mysqlIp");
        mysqlDB = properties.getProperty("mysqlDB");
        mysqlUsr = properties.getProperty("mysqlUsr");
        mysqlPass = properties.getProperty("mysqlPass");
        beginDate = properties.getProperty("beginDate");
        endDate = properties.getProperty("endDate");
        switcher = Integer.valueOf(properties.getProperty("switcher"));
        crawlFlag = Integer.valueOf(properties.getProperty("crawlFlag"));
        weekFlag = Integer.valueOf(properties.getProperty("weekFlag"));
        cleanFlag = Integer.valueOf(properties.getProperty("cleanFlag"));
    }
}
