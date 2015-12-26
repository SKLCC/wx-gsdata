package sklcc.ws.publicer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import sklcc.ws.dao.DataDest;
import sklcc.ws.entity.PubWeek;
import sklcc.ws.fetcher.HttpPostFetcher;
import sklcc.ws.util.Configuration;
import sklcc.ws.util.JsonUtil;
import sklcc.ws.util.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sukai on 15/11/13.
 */
public class PubWeekTask {

    private static Logger logger = LogManager.getLogger(PubWeekTask.class.getSimpleName());

    private int groupid;
    private Map<String, Object> params = new HashMap<String, Object>();
    private DataDest dataDest;

    public PubWeekTask(int groupid) {
        this.groupid = groupid;
        this.params.put("groupid", this.groupid);
        this.dataDest = new DataDest();
    }

    public void run() {
        if (Configuration.switcher == 2) {
            List<Date> saturdays = TimeUtil.GetSaturDays(Configuration.beginDate, Configuration.endDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (Date e: saturdays) {
                String eStr = sdf.format(e);
                this.params.put("day", eStr);
                JSONObject pubJson = HttpPostFetcher.fetch(Configuration.pubWeekUrl, this.params);
                if (pubJson == null) {
                    logger.error(this.params + " get json data null from api");
                    continue;
                }
                logger.info("Week: " + eStr + " " + pubJson);
                List<PubWeek> pubWeeks = JsonUtil.getPubWeeks(pubJson, eStr);
                if (pubWeeks != null) {
                    this.dataDest.addPubWeeks(pubWeeks);
                }
                try {
                    // remember to remove signature
                    this.params.remove("signature");
                    //Thread.sleep(8000);
                } catch (Exception ee) {
                    logger.error("sleep Error. " + ee.getMessage());
                }
            }
        } else if (Configuration.switcher == 1){
            Date yestrDate = TimeUtil.addDay(TimeUtil.getCurrentDate(), -1);
            if (TimeUtil.getCurWeekDayByStr(yestrDate) == 7) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String yestrStr = sdf.format(yestrDate);
                this.params.put("day", yestrStr);
                JSONObject pubJson = HttpPostFetcher.fetch(Configuration.pubWeekUrl, this.params);
                if (pubJson == null) {
                    logger.error(this.params + " get json data null from api");
                    this.dataDest.disconnect();
                    return;
                }
                logger.info("Week: " + yestrStr + " " + pubJson);
                List<PubWeek> pubWeeks = JsonUtil.getPubWeeks(pubJson, yestrStr);
                if (pubWeeks != null) {
                    this.dataDest.addPubWeeks(pubWeeks);
                }
            }
        }
        this.dataDest.disconnect();
    }
}