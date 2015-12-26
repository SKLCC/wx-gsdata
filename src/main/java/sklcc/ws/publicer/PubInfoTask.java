package sklcc.ws.publicer;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import sklcc.ws.dao.DataDest;
import sklcc.ws.entity.GroupPublicer;
import sklcc.ws.entity.Publicer;
import sklcc.ws.fetcher.HttpPostFetcher;
import sklcc.ws.util.Configuration;
import sklcc.ws.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sukai on 15/11/10.
 */
public class PubInfoTask {

    private static Logger logger = LogManager.getLogger(PubInfoTask.class.getSimpleName());

    private String name;
    private String nickName;
    private static final String fetchUrl = Configuration.publicerFetchUrl;
    private Map<String, Object> params = new HashMap<String, Object>();
    private DataDest dataDest;

    public PubInfoTask(GroupPublicer groupPublicer) {
        this.name = groupPublicer.getWx_name();
        this.nickName = groupPublicer.getWx_nickname();
        this.params.put("wx_nickname", this.nickName);
        this.params.put("wx_name", this.name);
        this.dataDest = new DataDest();
    }

    public void run() {
        JSONObject pubJson = HttpPostFetcher.fetch(this.fetchUrl, this.params);
        if (pubJson == null) {
            logger.error(this.params + " get json data null from api");
            this.dataDest.disconnect();
            return;
        }
        logger.info(this.name + " fetch from api complete");
        Publicer pub = JsonUtil.getPublicer(pubJson);
        if (pub != null) {
            this.dataDest.addPublicer(pub);
            logger.info(this.name + " write to DB complete");
        }
        this.dataDest.disconnect();
    }
}
