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
        //params.put("wx_nickname", "苏州新闻");
        //params.put("wx_name", "szxwwx");
        //params.put("beginDate", "2015-10-24");
        //params.put("endDate", "2015-10-24");
        //params.put("postdate", "2015-11-03");
        //params.put("datestart", "2015-11-03");
        //params.put("dateend", "2015-11-03");
        //params.put("groupid", 33518);
        //params.put("day", "2015-11-14");
        String ret_json;
        ret_json = apiSdk.callInterFace("http://open.gsdata.cn/api/wx/wxapi/nickname", params);
        System.out.println("\nfucK:");
        System.out.println(ret_json);
    }
}
