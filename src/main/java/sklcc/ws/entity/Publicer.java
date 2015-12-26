package sklcc.ws.entity;

/**
 * Created by sukai on 15/11/9.
 */
public class Publicer {

    private int id; // id int
    private String biz;// biz varchar
    private String wx_name;// official_account varchar
    private String wx_nickname;// nickname varchar
    private int wx_type;// type int
    private String wx_qrcode;// qr_code varchar
    private String wx_note;// description varchar
    private String wx_vip;// authentication int
    private String wx_vip_note;// authentication_info varchar
    private String wx_country;// country varchar
    private String wx_province;// province varchar
    private String wx_city;// city varchar
    private String wx_title;// latest_article_title varchar
    private String wx_url;// latest_article_url varchar
    private String wx_url_posttime;// latest_article_publishtime datetime
    private String update_time;// update_time datetime
    private int update_status;// update_status datetime
    private int status;// status
    //- delete_time 删除时间
    //- deleted 是否删除

    public String getWx_note() {
        return wx_note;
    }

    public void setWx_note(String wx_note) {
        this.wx_note = wx_note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public String getWx_name() {
        return wx_name;
    }

    public void setWx_name(String wx_name) {
        this.wx_name = wx_name;
    }

    public String getWx_nickname() {
        return wx_nickname;
    }

    public void setWx_nickname(String wx_nickname) {
        this.wx_nickname = wx_nickname;
    }

    public int getWx_type() {
        return wx_type;
    }

    public void setWx_type(int wx_type) {
        this.wx_type = wx_type;
    }

    public String getWx_qrcode() {
        return wx_qrcode;
    }

    public void setWx_qrcode(String wx_qrcode) {
        this.wx_qrcode = wx_qrcode;
    }

    public String getWx_vip() {
        return wx_vip;
    }

    public void setWx_vip(String wx_vip) {
        this.wx_vip = wx_vip;
    }

    public String getWx_vip_note() {
        return wx_vip_note;
    }

    public void setWx_vip_note(String wx_vip_note) {
        this.wx_vip_note = wx_vip_note;
    }

    public String getWx_country() {
        return wx_country;
    }

    public void setWx_country(String wx_country) {
        this.wx_country = wx_country;
    }

    public String getWx_province() {
        return wx_province;
    }

    public void setWx_province(String wx_province) {
        this.wx_province = wx_province;
    }

    public String getWx_city() {
        return wx_city;
    }

    public void setWx_city(String wx_city) {
        this.wx_city = wx_city;
    }

    public String getWx_title() {
        return wx_title;
    }

    public void setWx_title(String wx_title) {
        this.wx_title = wx_title;
    }

    public String getWx_url() {
        return wx_url;
    }

    public void setWx_url(String wx_url) {
        this.wx_url = wx_url;
    }

    public String getWx_url_posttime() {
        return wx_url_posttime;
    }

    public void setWx_url_posttime(String wx_url_posttime) {
        this.wx_url_posttime = wx_url_posttime;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getUpdate_status() {
        return update_status;
    }

    public void setUpdate_status(int update_status) {
        this.update_status = update_status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
