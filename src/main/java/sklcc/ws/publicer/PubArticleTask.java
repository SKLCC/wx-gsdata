package sklcc.ws.publicer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import sklcc.ws.dao.DataDest;
import sklcc.ws.entity.GroupPublicer;
import sklcc.ws.entity.PubArticle;
import sklcc.ws.entity.PubDaily;
import sklcc.ws.fetcher.HttpPostFetcher;
import sklcc.ws.util.Configuration;
import sklcc.ws.util.JsonUtil;
import sklcc.ws.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sukai on 15/11/11.
 */
public class PubArticleTask {

    private static Logger logger = LogManager.getLogger(PubArticleTask.class.getSimpleName());

    private String name;
    private String nickName;
    private GroupPublicer groupPublicer;
    private static final String fetchUrl = Configuration.pubArticleUrl;
    private Map<String, Object> params = new HashMap<String, Object>();
    private DataDest dataDest;

    public PubArticleTask(GroupPublicer groupPublicer) {
        this.groupPublicer = groupPublicer;
        this.name = groupPublicer.getWx_name();
        this.nickName = groupPublicer.getWx_nickname();
        this.params.put("wx_name", this.name);
        this.dataDest = new DataDest();
    }

    public void run() {
        if (Configuration.switcher == 2) {

            List<Date> dateList = TimeUtil.dateSplit(Configuration.beginDate, Configuration.endDate);
            runBatchModel(dateList);

        } else if (Configuration.switcher == 1){
            Date yestrDate = TimeUtil.addDay(TimeUtil.getCurrentDate(), -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String yestrStr = sdf.format(yestrDate);
            this.params.put("postdate", yestrStr);
            logger.info(yestrStr + " :" + this.params);
            JSONObject pubJson = HttpPostFetcher.fetch(this.fetchUrl, this.params);
            if (pubJson == null) {
                logger.error(this.params + " get json data null from api");
                this.dataDest.disconnect();
                return;
            }
            List<PubArticle> pubArticles = JsonUtil.getPubArticles(pubJson);
            if (pubArticles != null) {
                this.dataDest.addPubArticles(pubArticles);
            }
        }
        this.dataDest.disconnect();
    }

    public void runBatchModel(List<Date> dateList) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Date e: dateList) {
            String eStr = sdf.format(e);
            this.params.put("postdate", eStr);
            logger.info(eStr + " :" + this.params);
            JSONObject pubJson = HttpPostFetcher.fetch(this.fetchUrl, this.params);
            if (pubJson == null) {
                logger.error(this.params + " get json data null from api");
                continue;
            }
            List<PubArticle> pubArticles = JsonUtil.getPubArticles(pubJson);
            if (pubArticles != null) {
                this.dataDest.addPubArticles(pubArticles);
            }
            try {
                // remember to remove signature
                this.params.remove("signature");
                Thread.sleep(1000);
            } catch (Exception ee) {
                logger.error("sleep Error. " + ee.getMessage());
            }
        }
    }
}

