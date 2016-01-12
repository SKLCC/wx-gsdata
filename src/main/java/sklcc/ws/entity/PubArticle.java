package sklcc.ws.entity;

/**
 * Created by sukai on 15/11/11.
 */
public class PubArticle {

    private String id;
    private int nickname_id;// official_account_id varchar unique not null 公众号账号表中的id
    private String posttime;// publish_time datetime 文章发布时间
    private String title;// title varchar 文章标题
    private String content;// summary varchar 文章摘要
    // content text 文章正文
    private String url;// url varchar 文章地址
    private String add_time;// add_time datetime 文章存入数据库时间
    private String get_time;// statistics_get_time datetime 文章获取时间
    private int readnum;// read_count int 文章阅读总数
    private int likenum;// like_count int 文章点赞总数
    private int top;// ranking int 文章当天推送位置排行，1为头条，其他为位置顺序
    private String sourceurl;// source_url varchar 原文地址
    private String author;// author varchar 微信文章作者
    private String copyright;// copyright int 原创|非原创|未知
    private int weekreadnum;
    private int weeklikenum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNickname_id() {
        return nickname_id;
    }

    public void setNickname_id(int nickname_id) {
        this.nickname_id = nickname_id;
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getGet_time() {
        return get_time;
    }

    public void setGet_time(String get_time) {
        this.get_time = get_time;
    }

    public int getReadnum() {
        return readnum;
    }

    public void setReadnum(int readnum) {
        this.readnum = readnum;
    }

    public int getLikenum() {
        return likenum;
    }

    public void setLikenum(int likenum) {
        this.likenum = likenum;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getSourceurl() {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public int getWeekreadnum() {
        return weekreadnum;
    }

    public void setWeekreadnum(int weekreadnum) {
        this.weekreadnum = weekreadnum;
    }

    public int getWeeklikenum() {
        return weeklikenum;
    }

    public void setWeeklikenum(int weeklikenum) {
        this.weeklikenum = weeklikenum;
    }
}