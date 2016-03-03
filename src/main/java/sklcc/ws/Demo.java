package sklcc.ws;

//import cn.gsdata.index.ApiSdk;

import sklcc.ws.fetcher.ApiSdk;

import java.util.*;

public class Demo {

    // yang
    private final static String appId = "aJ2Vx03fS7j4LdxeSrb5";
    private final static String appKey = "zgaWRumyOf586J7z7uiH9E2D4";
    private final static ApiSdk apiSdk = ApiSdk.getApiSdk(appId, appKey);

    public static void main(String[] args) throws Exception {

        Map<String, Object> params = new HashMap<String, Object>();
        String ret_json;
        //params.put("wx_nickname", "今日相城");
        /*
        content_list
         */
        /*
        params.put("wx_name", "i_changshu");
        params.put("postdate", "2016-02-27");
        ret_json = apiSdk.callInterFace("http://open.gsdata.cn/api/wx/opensearchapi/content_list", params);
        */
        //params.put("endDate", "2016-01-24");
        //params.put("postdate", "2016-01-01");
        //params.put("datestart", "2015-11-03");
        //params.put("dateend", "2015-11-03");
        /*
        result_week
         */
        params.put("groupid", 33516);
        params.put("day", "2016-02-27");
        ret_json = apiSdk.callInterFace("http://open.gsdata.cn/api/wx/wxapi/result_week", params);


        System.out.println(ret_json);
    }
}