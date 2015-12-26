package sklcc.ws.fetcher;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import org.nutz.http.Request;
import org.nutz.http.Response;
import org.nutz.http.Sender;
import org.nutz.http.Request.METHOD;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Lang;
import org.nutz.repo.Base64;

public class ApiSdk {
    private final String appid;
    private final String appkey;
    private static volatile ApiSdk apiSdk;

    private ApiSdk(String appid, String appkey) {
        this.appid = appid;
        this.appkey = appkey;
    }

    public static ApiSdk getApiSdk(String appId, String appKey) {
        if(apiSdk == null) {
            Class var2 = ApiSdk.class;
            synchronized(ApiSdk.class) {
                if(apiSdk == null) {
                    if(appId == null || "".equals(appId) || appKey == null || "".equals(appKey)) {
                        try {
                            NullPointerException e = new NullPointerException("构造实例失败，函数参数缺失!");
                            throw e;
                        } catch (Exception var4) {
                            var4.printStackTrace();
                            return null;
                        }
                    }

                    apiSdk = new ApiSdk(appId, appKey);
                }
            }
        }

        return apiSdk;
    }

    public String callInterFace(String apiUrl, Map<String, Object> map) {
        String ret_str = "";

        try {
            JsonFormat e = new JsonFormat();
            e.setAutoUnicode(true);
            e.setCompact(true);
            NullPointerException str;
            if(apiUrl != null && !"".equals(apiUrl)) {
                if(map == null) {
                    str = new NullPointerException("参数为空");
                    throw str;
                } else if(this.appid != null && !"".equals(this.appid) && this.appkey != null && !"".equals(this.appkey)) {
                    map.put("appid", this.appid);
                    String str1 = Json.toJson(sortByKey(map), e);
                    System.out.println("str:" + str1);
                    String mysignature = Lang.md5(str1.toLowerCase() + this.appkey);
                    System.out.println("mysignature:" + mysignature);
                    map.put("signature", mysignature);
                    String postData = Json.toJson(map, e);
                    Request reqq = Request.create(apiUrl, METHOD.POST);
                    reqq.setData(Base64.encodeToByte(postData.getBytes(), false));
                    reqq.getHeader().set("Content-Type", "application/octet-stream");
                    Response response = Sender.create(reqq).send();
                    if(response.isOK()) {
                        ret_str = response.getContent("utf-8");
                    } else {
                        ret_str = new String("返回" + response.getStatus() + ",请稍后再试");
                    }

                    return ret_str;
                } else {
                    return "appid或者appkey参数缺失";
                }
            } else {
                str = new NullPointerException("apiUrl 参数缺失");
                throw str;
            }
        } catch (Exception var10) {
            return "异常!? " + var10.getMessage();
        }
    }

    static void exceptionThrow(String s) throws Exception {
        Exception e = new Exception(s);
        throw e;
    }

    private static LinkedHashMap<?, ?> sortByKey(Map map) {
        LinkedList list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                String key1 = String.valueOf(((Entry)o1).getKey());
                String key2 = String.valueOf(((Entry)o2).getKey());
                return key1.compareTo(key2);
            }
        });
        LinkedHashMap result = new LinkedHashMap();
        Iterator it = list.iterator();

        while(it.hasNext()) {
            Entry entry = (Entry)it.next();
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static void main(String[] args) {
    }
}
