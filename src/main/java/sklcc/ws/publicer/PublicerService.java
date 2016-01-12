package sklcc.ws.publicer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sklcc.ws.clean.CleanMain;
import sklcc.ws.clean.ErrorFinder;
import sklcc.ws.dao.PublicerDao;
import sklcc.ws.entity.Group;
import sklcc.ws.entity.GroupPublicer;
import sklcc.ws.util.Configuration;
import sklcc.ws.util.TimeUtil;

import java.util.Date;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by sukai on 15/11/10.
 */
public class PublicerService extends TimerTask {

    private static Logger logger = LogManager.getLogger(PublicerService.class.getSimpleName());

    public void run() {

        logger.info("PublicerService begin");
        /*
            crawl data
        */
        if (Configuration.crawlFlag == 1) {
            List<GroupPublicer> groupPublicers = PublicerDao.getPubsFromApi();
            logger.info("Get GroupPubs:" + groupPublicers.size());
            for (int i=0; i < groupPublicers.size(); i++) {
                PublicerJob job = new PublicerJob(groupPublicers.get(i));
                logger.info(i + "th " + groupPublicers.get(i).getWx_name() + " job begin");
                job.run();
                logger.info(groupPublicers.get(i).getWx_name() + " job over");
            }
        }

        /*
            crawl week data
         */
        if (Configuration.weekFlag == 1) {
            logger.info("article week data task begin");
            if (Configuration.switcher == 1) {
                Date yestrDate = TimeUtil.addDay(TimeUtil.getCurrentDate(), -1);
                if (TimeUtil.getCurWeekDayByStr(yestrDate) == 7) {
                    List<Date> weekDates = TimeUtil.getWeekDates(yestrDate);
                    List<GroupPublicer> groupPublicers = PublicerDao.getPubsFromApi();
                    for (int i=0; i < groupPublicers.size(); i++) {
                        PubArticleTask pubArticleTask = new PubArticleTask(groupPublicers.get(i));
                        pubArticleTask.runBatchModel(weekDates);
                    }
                }
            }
            logger.info("article week data task end");

            logger.info("Loading...");

            logger.info("pub week data task begin");
            List<Group> groups = PublicerDao.getGroups();
            for (Group g: groups) {
                PubWeekTask pubWeekTask = new PubWeekTask(g.getGroupId());
                logger.info("Group: " + g.getGroupId() + " weekJob Begin");
                pubWeekTask.run();
                logger.info("Group: " + g.getGroupId() + " weekJob Over");
            }
            logger.info("pub week task date over");
        }

        /*
            clean data
         */
        if (Configuration.cleanFlag == 1) {
            logger.info("Clean Task begin");

            ErrorFinder finder = new ErrorFinder();
            CleanMain cMain = new CleanMain();
            int errorCount = 1;

            while (errorCount != 0) {
                logger.info("ErrorFinder begin");
                finder.run();
                logger.info("ErrorFinder over");

                logger.info("CleanMain begin");
                errorCount = cMain.run();
                logger.info("CleanMain over");
            }
            logger.info("Clean Task begin");
        }
        logger.info("PublicerService over");
    }
}
