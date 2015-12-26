package sklcc.ws.entity;

/**
 * Created by sukai on 15/11/10.
 */
public class PubDaily {

    private int id; // id int
    private int statistics_id; // statistics_id int
    private int statistics_type;// statistics_type int 1表示公众号，2表示文章
    private String date;// date date 日期，表明是哪一天的数据
    private int url_num_total;// article_count int
    private int readnum_total;// read_count int （每日）阅读数
    private int likenum_total;// like_count int (每日)点赞数

    public int getStatistics_type() {
        return statistics_type;
    }

    public void setStatistics_type(int statistics_type) {
        this.statistics_type = statistics_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatistics_id() {
        return statistics_id;
    }

    public void setStatistics_id(int statistics_id) {
        this.statistics_id = statistics_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getLikenum_total() {
        return likenum_total;
    }

    public void setLikenum_total(int likenum_total) {
        this.likenum_total = likenum_total;
    }

    public int getReadnum_total() {
        return readnum_total;
    }

    public void setReadnum_total(int readnum_total) {
        this.readnum_total = readnum_total;
    }

    public int getUrl_num_total() {
        return url_num_total;
    }

    public void setUrl_num_total(int url_num_total) {
        this.url_num_total = url_num_total;
    }

}

