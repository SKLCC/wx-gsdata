package sklcc.ws;

//import cn.gsdata.index.ApiSdk;

import sklcc.ws.fetcher.ApiSdk;

import java.util.*;

public class Demo {

    // yang
    private final static String appId = "";
    private final static String appKey = "";
    private final static ApiSdk apiSdk = ApiSdk.getApiSdk(appId, appKey);

    public static void main(String[] args) throws Exception {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("wx_nickname", "苏州日报");
        params.put("wx_name", "suzhoudaily");
        //params.put("beginDate", "2015-12-03");
        //params.put("endDate", "2015-12-03");
        params.put("postdate", "2016-01-09");
        //params.put("datestart", "2015-11-03");
        //params.put("dateend", "2015-11-03");
        //params.put("groupid", 33518);
        //params.put("day", "2015-11-14");
        String ret_json;
        ret_json = apiSdk.callInterFace("http://open.gsdata.cn/api/wx/opensearchapi/content_list", params);
        System.out.println(ret_json);
    }
}
