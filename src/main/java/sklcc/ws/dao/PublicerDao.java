package sklcc.ws.dao;

import org.json.JSONObject;
import sklcc.ws.entity.Group;
import sklcc.ws.entity.GroupPublicer;
import sklcc.ws.fetcher.HttpPostFetcher;
import sklcc.ws.util.Configuration;
import sklcc.ws.util.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sukai on 15/11/10.
 */
public class PublicerDao {

    public static Map<String, String> getOriginPubs() {
        Map<String, String> pubs = new HashMap<String, String>();
        // wx_name, wx_nickname
        pubs.put("zhongguokunshan", "昆山发布");
        pubs.put("szgsfb", "苏州姑苏发布");
        pubs.put("wuzhongfabu", "吴中发布");
        pubs.put("szwjfb", "苏州吴江发布");
        pubs.put("tcfbwx", "太仓发布");
        pubs.put("sipacnc", "苏州工业园区发布");
        pubs.put("i_changshu", "i常熟");
        pubs.put("snd-szgxqfb", "苏州高新区发布");
        pubs.put("zjgcitynews", "张家港城事");
        pubs.put("suzhou-xc", "今日相城");
        return pubs;
    }

    public static List<GroupPublicer> getPubsFromApi() {
        Map<String, Object> params = new HashMap<String, Object>();
        JSONObject pubJson = HttpPostFetcher.fetch(Configuration.pubGroupPubUrl, params);
        List<GroupPublicer> groupPublicers = JsonUtil.getPubsFromApi(pubJson);
        return groupPublicers;
    }

    public static List<Group> getGroups() {
        Map<String, Object> params = new HashMap<String, Object>();
        JSONObject groupJson = HttpPostFetcher.fetch(Configuration.pubGroupInfoUrl, params);
        List<Group> groups = JsonUtil.getGroupInfos(groupJson);
        return groups;
    }

    public static void main(String[] args) throws IOException {
        new Configuration("project.properties");
        /*
        List<GroupPublicer> groupPublicers = getPubsFromApi();
        System.out.println(groupPublicers.size());
        for (int i=0;i<groupPublicers.size(); i++) {
            System.out.println(groupPublicers.get(i).getNicknameId() + " " + groupPublicers.get(i).getWx_nickname() + " " + groupPublicers.get(i).getWx_name());
        }
        */
        List<Group> g = getGroups();
        /*
        30469
        33516
        33517
        33518
         */
        System.out.println(g.size());
        for (int i=0;i<g.size();i++){
            System.out.println(g.get(i).getGroupId());
        }
    }

}
