package sklcc.ws.clean;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sklcc.ws.dao.DataDest;
import sklcc.ws.entity.PubArticle;
import sklcc.ws.entity.PubDaily;
import sklcc.ws.entity.Publicer;
import sklcc.ws.util.Configuration;
import sklcc.ws.util.TimeUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by sukai on 12/12/15.
 */
public class ErrorFinder {

    private static Logger logger = LogManager.getLogger(ErrorFinder.class.getSimpleName());
    public DataDest dataDest;

    public ErrorFinder() {
        this.dataDest = new DataDest();
    }

    public boolean check(Publicer p, String dateStr) {
        boolean res = true;

        PubDaily pubDaily = this.dataDest.readPubDaily(p.getId(), dateStr);
        if (pubDaily == null) {
            this.dataDest.writeWSACleaner(p, dateStr, 2);
            return res;
        }

        List<PubArticle> pubArticles = this.dataDest.readPubArticles(p.getId(), dateStr);
        if (pubArticles == null) {
            this.dataDest.writeWSACleaner(p, dateStr, 3);
            return res;
        }


        if (pubDaily.getUrl_num_total() != pubArticles.size()) {
            logger.info("文章数量错误: " + pubDaily.getUrl_num_total() + " " + pubArticles.size());
            res = false;
            return res;
        }

        int read_count = 0;
        int like_count = 0;

        for (int i=0; i<pubArticles.size(); i++) {
            //logger.info(pubArticles.get(i).getReadnum() + " " + pubArticles.get(i).getLikenum());
            read_count += pubArticles.get(i).getReadnum();
            like_count += pubArticles.get(i).getLikenum();
        }

        if ((read_count != pubDaily.getReadnum_total()) || (like_count != pubDaily.getLikenum_total())) {
            logger.info("阅读和点赞错误: ");
            logger.info(pubDaily.getReadnum_total() + " " + pubDaily.getLikenum_total());
            logger.info(read_count + " " + like_count);
            res = false;
            return res;
        }
        return res;
    }

    public void run() {
        List<Publicer> publicers = this.dataDest.readPublicers();
        logger.info("publicers: " + publicers.size());
        List<Date> dates = null;
        if (Configuration.switcher == 1) {
            Date yestrDate = TimeUtil.addDay(TimeUtil.getCurrentDate(), -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String yestrStr = sdf.format(yestrDate);
            dates = TimeUtil.dateSplit(yestrStr, yestrStr);
        } else if (Configuration.switcher == 2) {
            dates = TimeUtil.dateSplit(Configuration.beginDate, Configuration.endDate);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Date e: dates) {
            String eStr = sdf.format(e);
            for (Publicer p: publicers) {
                logger.info("Checking: " + eStr + " " + p.getWx_nickname());
                boolean result = check(p, eStr);
                if (result == false) {
                    this.dataDest.writeWSACleaner(p, eStr, 1);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Configuration("project.properties");
        ErrorFinder finder = new ErrorFinder();
        finder.run();
        /*
        int year = 2015;
        for (int m=12; m>=1; m--) {
            String month = String.format("%02d", months[m]);
            for (int d=1; d<=days[m]; d++) {
                String day = String.format("%02d", d);
                String dateStr = year + "-" + month + "-" + day;
                System.out.println(dateStr);
            }
        }
        */
    }
}