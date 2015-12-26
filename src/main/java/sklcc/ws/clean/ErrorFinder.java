package sklcc.ws.clean;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sklcc.ws.dao.DataDest;
import sklcc.ws.entity.PubArticle;
import sklcc.ws.entity.PubDaily;
import sklcc.ws.entity.Publicer;
import sklcc.ws.util.Configuration;

import java.io.IOException;
import java.util.List;

/**
 * Created by sukai on 12/12/15.
 */
public class ErrorFinder {

    private static Logger logger = LogManager.getLogger(ErrorFinder.class.getSimpleName());
    public DataDest dataDest;
    private static int[] months = {0,1,2,3,4,5,6,7,8,9,10,11};
    private static int[] days = {0,31,28,31,30,31,30,31,31,30,31,30};

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
            res = false;
            return res;
        }

        int read_count = 0;
        int like_count = 0;

        for (int i=0; i<pubArticles.size(); i++) {
            read_count += pubArticles.get(i).getReadnum();
            like_count += pubArticles.get(i).getLikenum();
        }

        if ((read_count != pubDaily.getReadnum_total()) || (like_count != pubDaily.getLikenum_total())) {
            res = false;
            return res;
        } else {
            return res;
        }
    }

    public void run() {
        List<Publicer> publicers = this.dataDest.readPublicers();
        logger.info("publicers: " + publicers.size());
        int year = 2015;
        int count=0;
        for (int m=11; m>=1; m--) {
            String month = String.format("%02d", months[m]);
            for (int d=1; d<=days[m]; d++) {
                String day = String.format("%02d", d);
                String dateStr = year + "-" + month + "-" + day;
                System.out.println(dateStr);
                for (Publicer p: publicers) {
                    logger.info("Checking: " + dateStr + " " + p.getWx_nickname());
                    boolean result = check(p, dateStr);
                    if (result == false) {
                        this.dataDest.writeWSACleaner(p, dateStr, 1);
                        count++;
                    }
                }
            }
        }
        logger.info("run over, count: " + count);
    }

    public static void main(String[] args) throws IOException {
        new Configuration("project.properties");
        ErrorFinder finder = new ErrorFinder();
        finder.run();
    }
}
