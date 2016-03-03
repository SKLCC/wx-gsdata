package sklcc.ws.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import sklcc.ws.entity.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sukai on 15/11/10.
 */
public class JsonUtil {

    private static Logger logger = LogManager.getLogger(JsonUtil.class.getSimpleName());

    public static Publicer getPublicer(JSONObject pubJson) {
        if (pubJson.has("errcode")) {
            logger.error("getPublicer error: " + "errcode" + pubJson);
            return null;
        }
        Publicer publicer = new Publicer();
        if (pubJson.getString("returnCode").equals("1001")) {
            try {
                JSONObject retData = pubJson.getJSONObject("returnData");
                //logger.info(retData);
                publicer.setId(retData.getInt("id"));
                publicer.setBiz(retData.getString("wx_biz"));
                publicer.setWx_name(retData.getString("wx_name"));
                publicer.setWx_nickname(retData.getString("wx_nickname"));
                publicer.setWx_type(retData.getInt("wx_type"));
                String qrcode = "http://mp.weixin.qq.com/mp/qrcode?scene=10000001&size=102&__biz=" + retData.getString("wx_biz");
                publicer.setWx_qrcode(qrcode);
                publicer.setWx_note(retData.getString("wx_note"));
                if (retData.has("wx_vip")) {
                    publicer.setWx_vip(retData.getString("wx_vip"));
                }
                if (retData.has("wx_vip_note")) {
                    publicer.setWx_vip_note(retData.getString("wx_vip_note"));
                }
                if (retData.has("wx_country")) {
                    publicer.setWx_country(retData.getString("wx_country"));
                }
                if (retData.has("wx_province")) {
                    publicer.setWx_province(retData.getString("wx_province"));
                }
                if (retData.has("wx_city")) {
                    publicer.setWx_city(retData.getString("wx_city"));
                }
                if (retData.has("wx_title")) {
                    publicer.setWx_title(retData.getString("wx_title"));
                }
                if (retData.has("wx_url")) {
                    publicer.setWx_url(retData.getString("wx_url"));
                }
                if (retData.has("wx_url_posttime")) {
                    publicer.setWx_url_posttime(retData.getString("wx_url_posttime"));
                }
                if (retData.has("update_time")) {
                    publicer.setUpdate_time(retData.getString("update_time"));
                }
                if (retData.has("update_status")) {
                    publicer.setUpdate_status(retData.getInt("update_status"));
                }
                if (retData.has("status")) {
                    publicer.setStatus(retData.getInt("status"));
                }
                logger.info("getPublicer success.");
            } catch (JSONException e) {
                logger.error("getPublicer Error. " + e.getMessage() + ": " + pubJson);
                return null;
            }
        }
        return publicer;
    }

    public static PubDaily getPubDaily(JSONObject pubJson, int pubId, String dateStr) {
        if (pubJson.has("errcode")) {
            logger.error("getPubDaily error: " + "errcode" + pubJson);
            return null;
        }
        PubDaily pubDaily = new PubDaily();
        //logger.info("shit " + pubJson);
        if (pubJson.getString("returnCode").equals("1001")) {
            try {
                JSONObject retData = pubJson.getJSONObject("returnData");
                if (retData.has("url_num_total")) {
                    pubDaily.setUrl_num_total(retData.getInt("url_num_total"));
                }
                //logger.info("fuck getPubDaily: " + dateStr);
                /**
                 * {"returnCode":"1001","returnMsg":"接口调用成功","returnData":{"total":8,"facet":[],"viewtotal":8,"num":8,"items":[{"posttime":"2015-11-22 13:11:43","ispush":"0","get_time_week":"","id":"201511-18991587","index_name":"wx_url","author":"","title":"90后媳妇给婆婆写了封大逆不道的信，婆婆看完却服了.","nickname_id":"702540","likenum":"145","likenum_pm":"145","wx_name":"ifanjian","name":"犯贱心理学","table_name":"201511","top":"1","likenum_week":"0","get_time":"2015-11-23 10:20:31","status":"1","picurl":"http://mmbiz.qpic.cn/mmbiz/0reocA6vHOWp7F4sWPAuiaSmIyGPnTBy9zMY3T3K5uQOxUH9I8WWH1YR4jrfzJfwlWUU2WJuicIZiamsEwicZWt7Bw/0?wx_fmt=jpeg","table_id":"18991587","copyright":"无","url":"http://mp.weixin.qq.com/s?__biz=MjM5NjIyMTMyMA==&mid=400909222&idx=1&sn=1ad41ceba39a4f21f294d9a5ac19f9c2&scene=4#wechat_redirect","readnum":"38112","content":"添加微信xjx388每天一堂瑜伽课，享&amp;quot;瘦&amp;quot;优雅生活!婆婆：你只不过是我丈夫的母亲，在结婚之前，你在我的生命","sourceurl":"http://u.eqxiu.com/s/0g6Ah78b","get_time_pm":"2015-11-23 10:20:31","add_time":"2015-11-23 01:47:34","readnum_week":"0","readnum_pm":"38112"},{"posttime":"2015-11-22 13:11:44","ispush":"0","get_time_week":"","id":"201511-18991603","index_name":"wx_url","author":"","title":"世上最性感的情侣床上姿势！","nickname_id":"702540","likenum":"19","likenum_pm":"19","wx_name":"ifanjian","name":"犯贱心理学","table_name":"201511","top":"3","likenum_week":"0","get_time":"2015-11-23 10:20:31","status":"1","picurl":"http://mmbiz.qpic.cn/mmbiz/0reocA6vHOWp7F4sWPAuiaSmIyGPnTBy9bTCDMkqXmBnLicDcOjN3xffNGmU0DqngkxiaA9O9xAhBSEP2C1v0dvGw/0?wx_fmt=jpeg","table_id":"18991603","copyright":"无","url":"http://mp.weixin.qq.com/s?__biz=MjM5NjIyMTMyMA==&mid=400909222&idx=3&sn=b876d0430c91292c3a99249050835abe&scene=4#wechat_redirect","readnum":"17354","content":"添加微信xjx388每天一堂瑜伽课，享&amp;quot;瘦&amp;quot;优雅生活!点击下方阅读原文领取福利～专业瘦身美容师微信推荐","sourceurl":"http://u.eqxiu.com/s/0g6Ah78b","get_time_pm":"2015-11-23 10:20:31","add_time":"2015-11-23 01:47:34","readnum_week":"0","readnum_pm":"17354"},{"posttime":"2015-11-22 13:11:44","ispush":"0","get_time_week":"","id":"201511-18991606","index_name":"wx_url","author":"","title":"越\u201c分手\u201d越相爱、揭秘男女关系怪定律~","nickname_id":"702540","likenum":"9","likenum_pm":"9","wx_name":"ifanjian","name":"犯贱心理学","table_name":"201511","top":"4","likenum_week":"0","get_time":"2015-11-23 10:20:31","status":"1","picurl":"http://mmbiz.qpic.cn/mmbiz/0reocA6vHOWp7F4sWPAuiaSmIyGPnTBy9iaK35icq7zVB7yiamBGhic7AJJmU4SsliaCx5NFcgNqB8hNdQn0xVJvyVbw/0?wx_fmt=jpeg","table_id":"18991606","copyright":"无","url":"http://mp.weixin.qq.com/s?__biz=MjM5NjIyMTMyMA==&mid=400909222&idx=4&sn=8fec70c77ea7370c1c51e348d99c10cc&scene=4#wechat_redirect","readnum":"13401","content":"添加微信xjx388每天一堂瑜伽课，享&amp;quot;瘦&amp;quot;优雅生活!导语：感情的世界是最没道理的，像下面这样的怪规律，要不","sourceurl":"http://u.eqxiu.com/s/0g6Ah78b","get_time_pm":"2015-11-23 10:20:31","add_time":"2015-11-23 01:47:34","readnum_week":"0","readnum_pm":"13401"},{"posttime":"2015-11-22 13:11:44","ispush":"0","get_time_week":"","id":"201511-18991600","index_name":"wx_url","author":"","title":"老公\u2026","nickname_id":"702540","likenum":"6","likenum_pm":"6","wx_name":"ifanjian","name":"犯贱心理学","table_name":"201511","top":"2","likenum_week":"0","get_time":"2015-11-23 10:20:31","status":"1","picurl":"http://mmbiz.qpic.cn/mmbiz/0reocA6vHOWp7F4sWPAuiaSmIyGPnTBy90OCzxeZV0N5AhNhpSdNIVhSRLYxqmd3qhD6xNNViacIEvtjniaXMLDjw/0?wx_fmt=jpeg","table_id":"18991600","copyright":"无","url":"http://mp.weixin.qq.com/s?__biz=MjM5NjIyMTMyMA==&mid=400909222&idx=2&sn=77a7144990f61e20a2b66102fb2dcb47&scene=4#wechat_redirect","readnum":"13356","content":"添加微信xjx388每天一堂瑜伽课，享&amp;quot;瘦&amp;quot;优雅生活!在男人听来，\u201c老公\u201d绝对是这世界上最温柔的情话；在女人","sourceurl":"http://u.eqxiu.com/s/0g6Ah78b","get_time_pm":"2015-11-23 10:20:31","add_time":"2015-11-23 01:47:34","readnum_week":"0","readnum_pm":"13356"},{"posttime":"2015-11-22 13:11:44","ispush":"0","get_time_week":"","id":"201511-18991620","index_name":"wx_url","author":"","title":"男人女人身体有个部位越丑越健康?赶紧对照一下吧！","nickname_id":"702540","likenum":"6","likenum_pm":"6","wx_name":"ifanjian","name":"犯贱心理学","table_name":"201511","top":"8","likenum_week":"0","get_time":"2015-11-23 10:20:32","status":"1","picurl":"http://mmbiz.qpic.cn/mmbiz/0reocA6vHOWp7F4sWPAuiaSmIyGPnTBy9y6rOIDqtyksjU936OddicibEL2BEHjU5AAYSP6SMC5lpJibCFSUFt0SdQ/0?wx_fmt=jpeg","table_id":"18991620","copyright":"无","url":"http://mp.weixin.qq.com/s?__biz=MjM5NjIyMTMyMA==&mid=400909222&idx=8&sn=e1fe1804b50e5f598b6d4b7d04e9019d&scene=4#wechat_redirect","readnum":"13274","content":"添加微信xjx388每天一堂瑜伽课，享&amp;quot;瘦&amp;quot;优雅生活!英国《每日邮报》和美国\u201cMSN健康生活网\u201d等国外媒体最","sourceurl":"http://u.eqxiu.com/s/0g6Ah78b","get_time_pm":"2015-11-23 10:20:32","add_time":"2015-11-23 01:47:34","readnum_week":"0","readnum_pm":"13274"},{"posttime":"2015-11-22 13:11:44","ispush":"0","get_time_week":"","id":"201511-18991618","index_name":"wx_url","author":"","title":"请尊重孩子的磨蹭 99%的家长不知道的秘密","nickname_id":"702540","likenum":"9","likenum_pm":"9","wx_name":"ifanjian","name":"犯贱心理学","table_name":"201511","top":"6","likenum_week":"0","get_time":"2015-11-23 10:20:31","status":"1","picurl":"http://mmbiz.qpic.cn/mmbiz/0reocA6vHOWp7F4sWPAuiaSmIyGPnTBy9he84tdw6Efx5LzvA3y7z1MzTQib88WsnouxbTibcAqfR3j7zNI3WQddw/0?wx_fmt=jpeg","table_id":"18991618","copyright":"无","url":"http://mp.weixin.qq.com/s?__biz=MjM5NjIyMTMyMA==&mid=400909222&idx=6&sn=943ec5a7bcad599fa70bbd457121b73b&scene=4#wechat_redirect","readnum":"8704","content":"添加微信xjx388每天一堂瑜伽课，享&amp;quot;瘦&amp;quot;优雅生活!来源：成长树（ID：chengzhangshu9）曾经","sourceurl":"http://u.eqxiu.com/s/0g6Ah78b","get_time_pm":"2015-11-23 10:20:31","add_time":"2015-11-23 01:47:34","readnum_week":"0","readnum_pm":"8704"},{"posttime":"2015-11-22 13:11:44","ispush":"0","get_time_week":"","id":"201511-18991607","index_name":"wx_url","author":"","title":"这个秋冬，我只买最流行的颜色，真漂亮。","nickname_id":"702540","likenum":"3","likenum_pm":"3","wx_name":"ifanjian","name":"犯贱心理学","table_name":"201511","top":"5","likenum_week":"0","get_time":"2015-11-23 10:20:31","status":"1","picurl":"http://mmbiz.qpic.cn/mmbiz/0reocA6vHOWp7F4sWPAuiaSmIyGPnTBy9uAw1pLYNrWB01HZlKhrLMZQjVslFR7gLAEaIvx2EGkELORfAlo4thw/0?wx_fmt=jpeg","table_id":"18991607","copyright":"无","url":"http://mp.weixin.qq.com/s?__biz=MjM5NjIyMTMyMA==&mid=400909222&idx=5&sn=e8175e5567a2ff7fab200391aed8cd0a&scene=4#wechat_redirect","readnum":"8021","content":"添加微信xjx388每天一堂瑜伽课，享&amp;quot;瘦&amp;quot;优雅生活!1、灰色灰色一个每一年秋天都必备的颜色2、太妃糖色这一","sourceurl":"http://u.eqxiu.com/s/0g6Ah78b","get_time_pm":"2015-11-23 10:20:31","add_time":"2015-11-23 01:47:34","readnum_week":"0","readnum_pm":"8021"},{"posttime":"2015-11-22 13:11:44","ispush":"0","get_time_week":"","id":"201511-18991619","index_name":"wx_url","author":"","title":"旧衣物改造大翻天，亲，是你给了它们重生的机会！","nickname_id":"702540","likenum":"5","likenum_pm":"5","wx_name":"ifanjian","name":"犯贱心理学","table_name":"201511","top":"7","likenum_week":"0","get_time":"2015-11-23 10:20:31","status":"1","picurl":"http://mmbiz.qpic.cn/mmbiz/0reocA6vHOWp7F4sWPAuiaSmIyGPnTBy9dnllEAFticMCWvDib2BHicOeib1PqFXKa4umXYSfk1VQzH0xSjg8INEnnA/0?wx_fmt=jpeg","table_id":"18991619","copyright":"无","url":"http://mp.weixin.qq.com/s?__biz=MjM5NjIyMTMyMA==&mid=400909222&idx=7&sn=a0ee65cfdfd7204aac97fe1bd8a38b05&scene=4#wechat_redirect","readnum":"7040","content":"添加微信xjx388每天一堂瑜伽课，享&amp;quot;瘦&amp;quot;优雅生活!旧毛衣，也是个鸡肋的物品，穿着不时尚，不保暖，丢掉又可","sourceurl":"http://u.eqxiu.com/s/0g6Ah78b","get_time_pm":"2015-11-23 10:20:31","add_time":"2015-11-23 01:47:34","readnum_week":"0","readnum_pm":"7040"}],"searchtime":0.008251},"feeCount":99713}
                 */
                //logger.info(pubJson);
                pubDaily.setReadnum_total(retData.getInt("readnum_total"));
                pubDaily.setLikenum_total(retData.getInt("likenum_total"));
                pubDaily.setStatistics_id(pubId);
                pubDaily.setStatistics_type(1);
                pubDaily.setDate(dateStr);
                logger.info("getPubDaily success.");
            } catch (JSONException e) {
                logger.error("getPubDaily Error. " + e.getMessage() + " " + dateStr +": " + pubJson);
                return null;
            }
        }
        return pubDaily;
    }

    public static List<PubArticle> getPubArticles(JSONObject pubJson) {
        if (pubJson.has("errcode")) {
            logger.error("getPubArticles error: " + "errcode" + pubJson);
            return null;
        }
        List<PubArticle> pubArticles = new ArrayList<PubArticle>();
        logger.info("getPubArticles: " + pubJson);
        if (pubJson.getString("returnCode").equals("1001")) {
            try {
                int num = pubJson.getJSONObject("returnData").getInt("num");
                JSONArray items = pubJson.getJSONObject("returnData").getJSONArray("items");
                logger.info("num: " + num);
                for (int i=0; i<num; i++) {
                    JSONObject t = items.getJSONObject(i);
                    PubArticle article = new PubArticle();
                    article.setId(t.getString("id"));
                    article.setNickname_id(t.getInt("nickname_id"));
                    article.setPosttime(t.getString("posttime"));
                    article.setTitle(t.getString("title"));
                    article.setContent(t.getString("content"));
                    article.setUrl(t.getString("url"));
                    article.setAdd_time(t.getString("add_time"));
                    //logger.info("get_time: " + t.getString("get_time"));
                    if (t.has("get_time")) {
                        article.setGet_time(t.getString("get_time"));
                    }
                    article.setReadnum(t.getInt("readnum"));
                    article.setLikenum(t.getInt("likenum"));
                    article.setWeeklikenum(t.getInt("likenum_week"));
                    article.setWeekreadnum(t.getInt("readnum_week"));
                    article.setTop(t.getInt("top"));
                    article.setSourceurl(t.getString("sourceurl"));
                    article.setAuthor(t.getString("author"));
                    article.setCopyright(t.getString("copyright"));
                    pubArticles.add(article);

                }
                logger.info("getPubArticles success.");
            } catch (JSONException e) {
                logger.error("getPubArticles Error. " + e.getMessage() + ": " + pubJson);
                return null;
            }
        }
        return pubArticles;
    }

    public static List<PubWeek> getPubWeeks(JSONObject pubJson, String dateStr) {
        if (pubJson.has("errcode")) {
            logger.error("getPubWeeks error: " + "errcode" + pubJson);
            return null;
        }
        List<PubWeek> pubWeeks = new ArrayList<PubWeek>();
        if (pubJson.getString("returnCode").equals("1001")) {
            try {
                JSONObject returnData = pubJson.getJSONObject("returnData");
                int total = returnData.getInt("total");
                logger.info(total);
                JSONArray rows = returnData.getJSONArray("rows");
                for (int i=0; i<total; i++) {
                    JSONObject t = rows.getJSONObject(i);
                    PubWeek pubWeek = new PubWeek();
                    pubWeek.setDate(dateStr);
                    pubWeek.setNickname_id(t.getInt("nickname_id"));
                    pubWeek.setWx_nickname(t.getString("wx_nickname"));
                    pubWeek.setWx_name(t.getString("wx_name"));
                    pubWeek.setUrl_times(t.getInt("url_times"));
                    pubWeek.setUrl_times_up(t.getInt("url_times_up"));
                    pubWeek.setUrl_times_readnum(t.getInt("url_times_readnum"));
                    pubWeek.setUrl_times_readnum_up(t.getInt("url_times_readnum_up"));
                    pubWeek.setUrl_num(t.getInt("url_num"));
                    pubWeek.setUrl_num_up(t.getInt("url_num_up"));
                    pubWeek.setUrl_num_10w(t.getInt("url_num_10w"));
                    pubWeek.setUrl_num_10w_up(t.getInt("url_num_10w_up"));
                    pubWeek.setReadnum_all(t.getInt("readnum_all"));
                    pubWeek.setReadnum_all_up(t.getInt("readnum_all_up"));
                    pubWeek.setReadnum_av(t.getInt("readnum_av"));
                    pubWeek.setReadnum_av_up(t.getInt("readnum_av_up"));
                    pubWeek.setLikenum_all(t.getInt("likenum_all"));
                    pubWeek.setLikenum_all_up(t.getInt("likenum_all_up"));
                    pubWeek.setLikenum_av(t.getInt("likenum_av"));
                    pubWeek.setLikenum_av_up(t.getInt("likenum_av_up"));
                    pubWeek.setReadnum_max(t.getInt("readnum_max"));
                    pubWeek.setLikenum_max(t.getInt("likenum_max"));
                    pubWeek.setLikenum_readnum_rate(t.getDouble("likenum_readnum_rate"));
                    pubWeek.setWcir(t.getDouble("wcir"));
                    pubWeek.setWciz(t.getDouble("wciz"));
                    pubWeek.setWci(t.getDouble("wci"));
                    pubWeek.setWci_up(t.getDouble("wci_up"));
                    pubWeek.setRowno(t.getInt("rowno"));
                    pubWeek.setRowno_up(t.getInt("rowno_up"));
                    pubWeeks.add(pubWeek);
                }
                logger.info("getPubWeeks success.");
            } catch (JSONException e) {
                logger.error("getPubWeeks Error. " + e.getMessage() + ": " + pubJson);
                return null;
            }
        }
        return pubWeeks;
    }

    public static List<GroupPublicer> getPubsFromApi(JSONObject pubJson) {
        if (pubJson.has("errcode")) {
            logger.error("getPubsFromApi error: " + "errcode" + pubJson);
            return null;
        }
        List<GroupPublicer> groupPublicers = new ArrayList<GroupPublicer>();
        if (pubJson.getString("returnCode").equals("1001")) {
            try {
                JSONObject returnData = pubJson.getJSONObject("returnData");
                int groupSize = returnData.getInt("groupsize");
                JSONArray lists = returnData.getJSONArray("list");
                for(int i=0; i<groupSize; i++){
                    JSONObject group = lists.getJSONObject(i);
                    int num = group.getInt("num");
                    logger.info("fetch: " + group.getInt("groupid") + " " + group.getString("groupname") + " " + num);
                /*if (group.getInt("groupid") != 33516) {
                    continue;
                }*/
                    JSONArray nicknames =group.getJSONArray("nicknames");
                    for (int j=0; j<num; j++) {
                        JSONObject pub = nicknames.getJSONObject(j);
                        GroupPublicer groupPublicer = new GroupPublicer();
                        groupPublicer.setGroupId(pub.getInt("group_id"));
                        groupPublicer.setNicknameId(pub.getInt("nickname_id"));
                        groupPublicer.setWx_nickname(pub.getString("wx_nickname"));
                        groupPublicer.setWx_name(pub.getString("wx_name"));
                        groupPublicers.add(groupPublicer);
                    }
                }
            } catch (JSONException e) {
                logger.error("getPubsFromApi Error. " + e.getMessage() + ": " + pubJson);
                return null;
            }
        }
        return groupPublicers;
    }

    public static List<Group> getGroupInfos(JSONObject groupJson) {
        if (groupJson.has("errcode")) {
            logger.error("getGroupInfos error: " + "errcode" + groupJson);
            return null;
        }
        List<Group> groups = new ArrayList<Group>();
        if (groupJson.getString("returnCode").equals("1001")) {
            try {
                JSONObject returnData = groupJson.getJSONObject("returnData");
                int groupCount = returnData.getInt("groupCount");
                JSONArray lists = returnData.getJSONArray("groupList");
                for(int i=0; i<groupCount; i++){
                    JSONObject group = lists.getJSONObject(i);
                    Group t = new Group();
                    t.setGroupId(group.getInt("groupid"));
                    t.setGroupName(group.getString("groupname"));
                    t.setNum(group.getInt("count"));
                    groups.add(t);
                }
            } catch (JSONException e) {
                logger.error("getGroupInfos Error. " + e.getMessage() + ": " + groupJson);
                return null;
            }
        }
        return groups;
    }
}