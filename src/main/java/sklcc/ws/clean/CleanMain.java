package sklcc.ws.clean;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import sklcc.ws.dao.DataDest;
import sklcc.ws.entity.PubArticle;
import sklcc.ws.entity.PubDaily;
import sklcc.ws.entity.WSACleaner;
import sklcc.ws.fetcher.HttpPostFetcher;
import sklcc.ws.util.Configuration;
import sklcc.ws.util.JsonUtil;

/**
 * Created by sukai on 15/11/28.
 */
public class CleanMain {

    private static Logger logger = LogManager.getLogger(CleanMain.class.getSimpleName());
    public DataDest dataDest;

    public CleanMain() {
        this.dataDest = new DataDest();
    }

    public void solveError(WSACleaner c) {
        String wx_name = c.getWx_name();
        String wx_nickname = c.getWx_nickname();
        String date = c.getDate();

        Map<String, Object> dailyParams = new HashMap<String, Object>();
        dailyParams.put("wx_nickname", wx_nickname);
        dailyParams.put("wx_name", wx_name);
        dailyParams.put("beginDate", date);
        dailyParams.put("endDate", date);
        JSONObject dailyJson = HttpPostFetcher.fetch(Configuration.pubDailyUrl, dailyParams);
        PubDaily pubDaily = JsonUtil.getPubDaily(dailyJson, c.getId(), date);

        Map<String, Object> articleParams = new HashMap<String, Object>();
        articleParams.put("wx_name", wx_name);
        articleParams.put("postdate", date);
        JSONObject artileJson = HttpPostFetcher.fetch(Configuration.pubArticleUrl, articleParams);
        List<PubArticle> pubArticles = JsonUtil.getPubArticles(artileJson);

        if (pubDaily.getUrl_num_total() != pubArticles.size()) {
            c.setType(4);
            dataDest.updateWSACleaner(c);
            return;
        }

        int readTotals = 0;
        int likeTotals = 0;
        for (PubArticle a: pubArticles) {
            readTotals += a.getReadnum();
            likeTotals += a.getLikenum();
        }

        if ((pubDaily.getReadnum_total() != readTotals) || (pubDaily.getLikenum_total() != likeTotals)) {
            c.setType(4);
            dataDest.updateWSACleaner(c);
            return;
        }

        dataDest.updatePubDaily(pubDaily);
        dataDest.updatePubArticles(pubArticles);
        c.setType(6);
        dataDest.updateWSACleaner(c);
    }

    public void solveNoDaily(WSACleaner c) {
        String wx_name = c.getWx_name();
        String wx_nickname = c.getWx_nickname();
        String date = c.getDate();

        Map<String, Object> dailyParams = new HashMap<String, Object>();
        dailyParams.put("wx_nickname", wx_nickname);
        dailyParams.put("wx_name", wx_name);
        dailyParams.put("beginDate", date);
        dailyParams.put("endDate", date);
        JSONObject dailyJson = HttpPostFetcher.fetch(Configuration.pubDailyUrl, dailyParams);
        PubDaily pubDaily = JsonUtil.getPubDaily(dailyJson, c.getId(), date);

        Map<String, Object> articleParams = new HashMap<String, Object>();
        articleParams.put("wx_name", wx_name);
        articleParams.put("postdate", date);
        JSONObject artileJson = HttpPostFetcher.fetch(Configuration.pubArticleUrl, articleParams);
        List<PubArticle> pubArticles = JsonUtil.getPubArticles(artileJson);

        if (pubDaily.getUrl_num_total() != pubArticles.size()) {
            c.setType(4);
            dataDest.updateWSACleaner(c);
            return;
        }

        int readTotals = 0;
        int likeTotals = 0;
        for (PubArticle a: pubArticles) {
            readTotals += a.getReadnum();
            likeTotals += a.getLikenum();
        }

        if ((pubDaily.getReadnum_total() != readTotals) || (pubDaily.getLikenum_total() != likeTotals)) {
            c.setType(4);
            dataDest.updateWSACleaner(c);
            return;
        }

        dataDest.addPubDaily(pubDaily);
        dataDest.updatePubArticles(pubArticles);
        c.setType(6);
        dataDest.updateWSACleaner(c);
    }

    public void solveNoArticles(WSACleaner c) {
        String wx_name = c.getWx_name();
        String wx_nickname = c.getWx_nickname();
        String date = c.getDate();

        Map<String, Object> dailyParams = new HashMap<String, Object>();
        dailyParams.put("wx_nickname", wx_nickname);
        dailyParams.put("wx_name", wx_name);
        dailyParams.put("beginDate", date);
        dailyParams.put("endDate", date);
        JSONObject dailyJson = HttpPostFetcher.fetch(Configuration.pubDailyUrl, dailyParams);
        PubDaily pubDaily = JsonUtil.getPubDaily(dailyJson, c.getId(), date);

        if (pubDaily.getUrl_num_total() == 0) {
            c.setType(5);
            dataDest.updateWSACleaner(c);
            return;
        }

        Map<String, Object> articleParams = new HashMap<String, Object>();
        articleParams.put("wx_name", wx_name);
        articleParams.put("postdate", date);
        JSONObject artileJson = HttpPostFetcher.fetch(Configuration.pubArticleUrl, articleParams);
        List<PubArticle> pubArticles = JsonUtil.getPubArticles(artileJson);

        if (pubDaily.getUrl_num_total() != pubArticles.size()) {
            c.setType(4);
            dataDest.updateWSACleaner(c);
            return;
        }

        int readTotals = 0;
        int likeTotals = 0;
        for (PubArticle a: pubArticles) {
            readTotals += a.getReadnum();
            likeTotals += a.getLikenum();
        }

        if ((pubDaily.getReadnum_total() != readTotals) || (pubDaily.getLikenum_total() != likeTotals)) {
            c.setType(4);
            dataDest.updateWSACleaner(c);
            return;
        }

        dataDest.updatePubDaily(pubDaily);
        dataDest.updatePubArticles(pubArticles);
        c.setType(6);
        dataDest.updateWSACleaner(c);
    }

    public void run() {
        List<WSACleaner> cleaners = dataDest.getWSACleaners();
        logger.info("WSACleaners size: " + cleaners.size());

        for (WSACleaner c: cleaners) {
            switch(c.getType()) {
                case 1:
                    logger.info("solve type 1");
                    solveError(c);
                    break;
                case 2:
                    logger.info("solve type 2");
                    solveNoDaily(c);
                    break;
                case 3:
                    logger.info("solve type 3");
                    solveNoArticles(c);
                    break;
                default:
                    break;
            }
        }
        logger.info("CleanMain job over");
        this.dataDest.disconnect();
    }

    public static void main(String[] args) throws IOException {
        new Configuration("project.properties");
        CleanMain cMain = new CleanMain();
        cMain.run();
    }
}