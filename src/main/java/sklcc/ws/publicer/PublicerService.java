package sklcc.ws.publicer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sklcc.ws.clean.CleanMain;
import sklcc.ws.clean.ErrorFinder;
import sklcc.ws.dao.PublicerDao;
import sklcc.ws.entity.Group;
import sklcc.ws.entity.GroupPublicer;
import sklcc.ws.util.Configuration;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * Created by sukai on 15/11/10.
 */
public class PublicerService extends TimerTask {

    private static Logger logger = LogManager.getLogger(PublicerService.class.getSimpleName());

    public void run() {
        /*
        logger.info("get origin publicers");
        Map<String, String> pubs = PublicerDao.getOriginPubs();

        for(String name: pubs.keySet()) {
            PublicerJob job = new PublicerJob(name, pubs.get(name));
            logger.info(name + " job begin");
            job.run();
            logger.info(name + " job over");
        }
        */

        logger.info("PublicerService begin");
        /*
            数据抓取 任务
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

        if (Configuration.weekFlag == 1) {
            logger.info("pubWeekTask begin");
            List<Group> groups = PublicerDao.getGroups();
            for (Group g: groups) {
                PubWeekTask pubWeekTask = new PubWeekTask(g.getGroupId());
                logger.info("Group: " + g.getGroupId() + " weekJob Begin");
                pubWeekTask.run();
                logger.info("Group: " + g.getGroupId() + " weekJob Over");
            }
            logger.info("pubWeekTask over");
        }

        /*
            数据清理任务
         */
        if (Configuration.cleanFlag == 1) {
            logger.info("Clean Task begin");

            logger.info("ErrorFinder begin");
            ErrorFinder finder = new ErrorFinder();
            finder.run();
            logger.info("ErrorFinder over");

            logger.info("CleanMain begin");
            CleanMain cMain = new CleanMain();
            cMain.run();
            logger.info("CleanMain over");

            logger.info("Clean Task begin");
        }
        logger.info("PublicerService over");
    }
}
