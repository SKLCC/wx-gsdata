package sklcc.ws.publicer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import sklcc.ws.dao.DataDest;
import sklcc.ws.entity.GroupPublicer;
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
 * Created by sukai on 15/11/10.
 */
public class PubDailyTask {

    private static Logger logger = LogManager.getLogger(PubDailyTask.class.getSimpleName());

    private GroupPublicer groupPublicer;
    private static final String fetchUrl = Configuration.pubDailyUrl;
    private Map<String, Object> params = new HashMap<String, Object>();
    private DataDest dataDest;

    public PubDailyTask(GroupPublicer groupPublicer) {
        this.groupPublicer = groupPublicer;
        this.params.put("wx_nickname", groupPublicer.getWx_nickname());
        this.params.put("wx_name", groupPublicer.getWx_name());
        this.dataDest = new DataDest();
    }

    public void run() {
        if (Configuration.switcher == 2) {
            List<Date> dateList = TimeUtil.dateSplit(Configuration.beginDate, Configuration.endDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Date e: dateList) {
                String eStr = sdf.format(e);
                this.params.put("beginDate", eStr);
                this.params.put("endDate", eStr);
                logger.info(eStr + " :" + this.params);
                JSONObject pubJson = HttpPostFetcher.fetch(this.fetchUrl, this.params);
                if (pubJson == null) {
                    logger.error(this.params + " get json data null from api");
                    continue;
                }
                logger.info("fetch: " + pubJson);
                PubDaily pubDaily = JsonUtil.getPubDaily(pubJson, this.groupPublicer.getNicknameId(), eStr);
                if (pubDaily != null) {
                    this.dataDest.addPubDaily(pubDaily);
                }
                try {
                    // remember to remove signature
                    this.params.remove("signature");
                    //Thread.sleep(5000);
                } catch (Exception ee) {
                    logger.error("sleep Error. " + ee.getMessage());
                }
            }
        } else if (Configuration.switcher == 1) {
            Date yestrDate = TimeUtil.addDay(TimeUtil.getCurrentDate(), -1);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String yestrStr = sdf.format(yestrDate);
            this.params.put("beginDate", yestrStr);
            this.params.put("endDate", yestrStr);
            logger.info(yestrStr + " :" + this.params);
            JSONObject pubJson = HttpPostFetcher.fetch(this.fetchUrl, this.params);
            if (pubJson == null) {
                logger.error(this.params + " get json data null from api");
                this.dataDest.disconnect();
                return;
            }
            logger.info("fetch: " + pubJson);
            PubDaily pubDaily = JsonUtil.getPubDaily(pubJson, this.groupPublicer.getNicknameId(), yestrStr);
            if (pubDaily != null) {
                this.dataDest.addPubDaily(pubDaily);
            }
        }
        this.dataDest.disconnect();
    }
}
