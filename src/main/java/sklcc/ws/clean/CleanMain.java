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

    public void basicSolve(WSACleaner c) {
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

        if (pubDaily == null || pubArticles == null) {
            logger.error("Data from api are null");
            return;
        }

        if (pubArticles.size() == 0 && pubDaily.getUrl_num_total() != 0) {
            logger.error("pubArticles size is 0, but pubDaily urls size is not.");
            return;
        }

        // 根据article计算出 pubDaily的total read和like
        int readTotals = 0;
        int likeTotals = 0;
        for (PubArticle a: pubArticles) {
            if (!(a.getPosttime().substring(0, 10).equals(pubDaily.getDate()))) {
                logger.error(c.getWx_name() + " " + c.getDate() + ": ReGet data from api error.");
                return;
            }
            readTotals += a.getReadnum();
            likeTotals += a.getLikenum();
        }

        if (pubDaily.getUrl_num_total() != pubArticles.size() ||
                (pubDaily.getReadnum_total() != readTotals) ||
                (pubDaily.getLikenum_total() != likeTotals)) {
            pubDaily.setUrl_num_total(pubArticles.size());
            pubDaily.setReadnum_total(readTotals);
            pubDaily.setLikenum_total(likeTotals);
        }

        // 更新 pubDaily 和 articleDaily
        if (this.dataDest.readPubDaily(pubDaily.getStatistics_id(), pubDaily.getDate()) == null) {
            this.dataDest.addPubDaily(pubDaily);
        } else {
            this.dataDest.updatePubDaily(pubDaily);
        }
        logger.info("更新 pubDaily");
        this.dataDest.updatePubArticles(pubArticles);
        logger.info("更新 PubArticles");
        c.setType(6);
        this.dataDest.updateWSACleaner(c);
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

        // 若从api获取的 pubDaily数据 和 articleDaily数据 不一致,则手动计算daily数据 并 更新pubDaily表
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

    public int run() {
        List<WSACleaner> cleaners = this.dataDest.getWSACleaners();
        logger.info("WSACleaners size: " + cleaners.size());

        for (WSACleaner c: cleaners) {
            switch(c.getType()) {
                case 1:
                    logger.info("solve: " + c.getWx_name() + ' ' + c.getDate());
                    //solveError(c);
                    basicSolve(c);
                    break;
                case 2:
                    logger.info("solve: " + c.getWx_name() + ' ' + c.getDate());
                    //solveNoDaily(c);
                    basicSolve(c);
                    break;
                case 3:
                    logger.info("solve: " + c.getWx_name() + ' ' + c.getDate());
                    //solveNoArticles(c);
                    basicSolve(c);
                    break;
                default:
                    break;
            }
        }
        logger.info("CleanMain job over");
        //this.dataDest.disconnect();
        return cleaners.size();
    }

    public static void main(String[] args) throws IOException {
        new Configuration("project.properties");
        CleanMain cMain = new CleanMain();
        cMain.run();
    }
}