package sklcc.ws.fetcher;

//import cn.gsdata.index.ApiSdk;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import sklcc.ws.util.Configuration;

import java.util.Map;

/**
 * Created by sukai on 15/11/9.
 */
public class HttpPostFetcher {

    private static Logger logger = LogManager.getLogger(HttpPostFetcher.class.getSimpleName());
    private final static ApiSdk apiSdk = ApiSdk.getApiSdk(Configuration.appId, Configuration.appKey);

    public static JSONObject fetch(String fetchUrl, Map params) {
        String ret_json = apiSdk.callInterFace(fetchUrl, params);
        //ret_json.replaceAll("\\s", "");
        JSONObject apiResult = null;
        try {
            apiResult = new JSONObject(ret_json);
        } catch (JSONException e) {
            logger.error("fetch ret_json: " + ret_json + " error. " + e.getMessage());
        }
        return apiResult;
    }
}
