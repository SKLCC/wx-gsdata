package sklcc.ws;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sklcc.ws.publicer.PublicerService;
import sklcc.ws.publicer.TimerManager;
import sklcc.ws.util.Configuration;

import java.io.IOException;

/**
 * Created by sukai on 15/11/10.
 */
public class Bootstrap {

    private static Logger logger = LogManager.getLogger(Bootstrap.class.getSimpleName());

    public static void main(String[] args) throws IOException {

        // read config infos from file
        new Configuration("project.properties");

        logger.info("Read conf infos");
        logger.info("mysqlIp " + Configuration.mysqlIp);
        logger.info("mysqlDB " + Configuration.mysqlDB);
        logger.info("switcher " + Configuration.switcher);

        if (Configuration.switcher == 2) {
            // Batch-Model
            logger.info("beginDate " + Configuration.beginDate + ", endDate " + Configuration.endDate);
            logger.info("Batch Model begin");

            PublicerService pService = new PublicerService();
            pService.run();

            logger.info("Batch Model over");
        } else if (Configuration.switcher == 1) {
            // Day-Model
            logger.info("Day Model begin");

            new TimerManager();

            logger.info("Day Model over");
        }
    }
}