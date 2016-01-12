package sklcc.ws.publicer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sklcc.ws.entity.GroupPublicer;

/**
 * Created by sukai on 15/11/10.
 */
public class PublicerJob {

    private static Logger logger = LogManager.getLogger(PublicerJob.class.getSimpleName());

    public GroupPublicer groupPublicer;

    public PublicerJob(GroupPublicer groupPublicer) {
        this.groupPublicer = groupPublicer;
    }

    public void run() {

        PubInfoTask pubInfoTask = new PubInfoTask(this.groupPublicer);
        logger.info(this.groupPublicer.getWx_name() + " pubInfoTask begin");
        pubInfoTask.run();
        logger.info(this.groupPublicer.getWx_name() + " pubInfoTask over");

        PubDailyTask pubDaliyTask = new PubDailyTask(this.groupPublicer);
        logger.info(this.groupPublicer.getWx_name() + " pubDaliyTask begin");
        pubDaliyTask.run();
        logger.info(this.groupPublicer.getWx_name() + " pubDaliyTask over");

        PubArticleTask pubArticleTask = new PubArticleTask(this.groupPublicer);
        logger.info(this.groupPublicer.getWx_name() + " pubArticleTask begin");
        pubArticleTask.run();
        logger.info(this.groupPublicer.getWx_name() + " pubArticleTask over");
    }
}

