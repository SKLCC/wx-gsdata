package sklcc.ws.dao;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import sklcc.ws.entity.PubArticle;
import sklcc.ws.entity.PubDaily;
import sklcc.ws.entity.PubWeek;
import sklcc.ws.entity.Publicer;
import sklcc.ws.util.Configuration;
import sklcc.ws.util.DatabaseUtil;
import sklcc.ws.entity.WSACleaner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sukai on 15/11/10.
 */
public class DataDest {

    private static Logger logger = LogManager.getLogger(DataDest.class.getSimpleName());
    private Connection connection;

    public DataDest() {
        connection = DatabaseUtil.getMysqlConnection(Configuration.mysqlIp, Configuration.mysqlDB, Configuration.mysqlUsr,
                Configuration.mysqlPass);
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            logger.error("setAutoCommit Error: " + e.getMessage());
        }
    }

    public void disconnect() {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("Disconnect from DB " + "Where ip " + Configuration.mysqlIp + "Error: " + e.getMessage());
        }
    }

    public boolean testConnection() {
        boolean result = false;
        try {
            result = connection != null && !connection.isClosed();
        } catch (SQLException e) {
            logger.error("Test connect To DB " + "Where ip " + Configuration.mysqlIp + "Error: " + e.getMessage());
        }
        return result;
    }

    public List<Publicer> readPublicers() {
        List<Publicer> publicers = new ArrayList<Publicer>();
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT id, nickname, official_account FROM wsa_official_account;"
                    );
            ResultSet rs = ps.executeQuery();
            //connection.commit();
            while (rs.next()) {
                Publicer pub = new Publicer();
                pub.setWx_name(rs.getString("official_account"));
                pub.setWx_nickname(rs.getString("nickname"));
                pub.setId(rs.getInt("id"));
                publicers.add(pub);
            }
        } catch (SQLException e) {
            logger.error("readPublicers Error: " + e.getMessage());
        }
        return publicers;
    }

    public boolean searchPublicer(Publicer pub) {
        boolean res = false;
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT id FROM wsa_official_account WHERE id=?"
                    );
            ps.setInt(1, pub.getId());
            ResultSet rs = ps.executeQuery();
            //connection.commit();
            if (rs.next()) {
                res = true;
            } else {
                res = false;
            }
        } catch (SQLException e) {
            logger.error("searchPublicer Error: " + e.getMessage());
        }
        return res;
    }

    public void addPublicerFromExcel(Publicer pub) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO wsa_official_account(official_account, nickname) VALUES(?,?)");
            ps.setString(1, pub.getWx_name());
            ps.setString(2, pub.getWx_nickname());
            ps.execute();
            connection.commit();
            logger.info("addPublicerFromExcel success");
        } catch (SQLException e) {
            logger.error("addPublicerFromExcel Error: " + e.getMessage());
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException e1) {
                logger.error("RollBack Error: " + e1.getMessage());
            }
        }
    }

    public void addPublicer(Publicer pub) {
        try {
            // update table set field_1='A',field_2='b',field_3='C' where field_4='F'
            /*
            private int id; // id int
            private String biz;// biz varchar
            private String wx_name;// official_account varchar
            private String wx_nickname;// nickname varchar
            private int wx_type;// type int
            private String wx_qrcode;// qr_code varchar
            private String wx_note;// description varchar
            private String wx_vip;// authentication varchar
            private String wx_vip_note;// authentication_info varchar
            private String wx_country;// country varchar
            private String wx_province;// province varchar
            private String wx_city;// city varchar
            private String wx_title;// latest_article_title varchar
            private String wx_url;// latest_article_url varchar
            private String wx_url_posttime;// latest_article_publishtime datetime
            private String update_time;// update_time datetime
            private int update_status;// update_status int
            private int status;// status
            + "qr_code=?,"
             */
            if (searchPublicer(pub)) {
                PreparedStatement ps = connection
                        .prepareStatement("UPDATE wsa_official_account SET "
                                        + "biz=?,"
                                        + "official_account=?,"
                                        + "nickname=?,"
                                        + "type=?,"
                                        + "description=?,"
                                        + "authentication=?,"
                                        + "authentication_info=?,"
                                        + "country=?,"
                                        + "province=?,"
                                        + "city=?,"
                                        + "latest_article_title=?,"
                                        + "latest_article_url=?,"
                                        + "latest_article_publishtime=?,"
                                        + "update_time=?,"
                                        + "update_status=? "
                                        +"WHERE id=?"
                        );
                ps.setString(1, pub.getBiz());
                ps.setString(2, pub.getWx_name());
                ps.setString(3, pub.getWx_nickname());
                ps.setInt(4, pub.getWx_type());
                //ps.setString(5, pub.getWx_qrcode());
                ps.setString(5, pub.getWx_note());
                ps.setString(6, pub.getWx_vip());
                ps.setString(7, pub.getWx_vip_note());
                ps.setString(8, pub.getWx_country());
                ps.setString(9, pub.getWx_province());
                ps.setString(10, pub.getWx_city());
                ps.setString(11, pub.getWx_title());
                ps.setString(12, pub.getWx_url());
                // String to datetime
                if (pub.getWx_url_posttime() != null) {
                    ps.setTimestamp(13, Timestamp.valueOf(pub.getWx_url_posttime()));
                } else {
                    ps.setTimestamp(13, Timestamp.valueOf("1900-1-1 00:00:00"));
                }
                Timestamp t = Timestamp.valueOf("1900-1-1 00:00:00");
                if (pub.getUpdate_time() != null) {
                    ps.setTimestamp(14, Timestamp.valueOf(pub.getUpdate_time()));
                } else {
                    ps.setTimestamp(14, Timestamp.valueOf("1900-1-1 00:00:00"));
                }
                ps.setInt(15, pub.getUpdate_status());
                ps.setInt(16, pub.getId());
                ps.executeUpdate();
                connection.commit();
                logger.info("updatePublicer success");
            } else {
                PreparedStatement ps = connection
                        .prepareStatement("INSERT INTO wsa_official_account(" +
                                        "biz,official_account,nickname,type,qr_code,description,authentication,authentication_info," +
                                        "country,province,city,latest_article_title,latest_article_url," +
                                        "latest_article_publishtime,update_time,update_status,id)" +
                                        " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
                        );
                ps.setString(1, pub.getBiz());
                ps.setString(2, pub.getWx_name());
                ps.setString(3, pub.getWx_nickname());
                ps.setInt(4, pub.getWx_type());
                ps.setString(5, pub.getWx_qrcode());
                ps.setString(6, pub.getWx_note());
                ps.setString(7, pub.getWx_vip());
                ps.setString(8, pub.getWx_vip_note());
                ps.setString(9, pub.getWx_country());
                ps.setString(10, pub.getWx_province());
                ps.setString(11, pub.getWx_city());
                ps.setString(12, pub.getWx_title());
                ps.setString(13, pub.getWx_url());
                // String to datetime
                if (pub.getWx_url_posttime() != null) {
                    ps.setTimestamp(14, Timestamp.valueOf(pub.getWx_url_posttime()));
                } else {
                    ps.setTimestamp(14, Timestamp.valueOf("1900-1-1 00:00:00"));
                }
                if (pub.getUpdate_time() != null) {
                    ps.setTimestamp(15, Timestamp.valueOf(pub.getUpdate_time()));
                } else {
                    ps.setTimestamp(15, Timestamp.valueOf("1900-1-1 00:00:00"));
                }
                ps.setInt(16, pub.getUpdate_status());
                ps.setInt(17, pub.getId());
                ps.executeUpdate();
                connection.commit();
                logger.info("insertPublicer success");
            }
        } catch (SQLException e) {
            logger.error("addPublicer Error: " + e.getMessage());
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException e1) {
                logger.error("RollBack Error: " + e1.getMessage());
            }
        }
    }

    public void addPubDaily(PubDaily pub) {
        /*
        insert into UserInfo(name, sex, idCard, location, birthday) values(?,?,?,?,?);
        private int id; // id int
        private int statistics_id; // statistics_id int
        private int statistics_type;// statistics_type int 1表示公众号，2表示文章
        private String date;// date date 日期，表明是哪一天的数据
        private String url_num_total;// article_count int
        private String readnum_total;// read_count int （每日）阅读数
        private String likenum_total;// like_count int (每日)点赞数
         */
        try {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO wsa_daily_statistics(statistics_id,statistics_type,date,read_count,like_count,article_count)"
                                    + " VALUES(?,?,?,?,?,?)"
                    );
            ps.setString(1, String.valueOf(pub.getStatistics_id()));
            ps.setInt(2, pub.getStatistics_type());
            ps.setDate(3, Date.valueOf(pub.getDate()));
            ps.setInt(4, pub.getReadnum_total());
            ps.setInt(5, pub.getLikenum_total());
            ps.setInt(6, pub.getUrl_num_total());
            ps.execute();
            connection.commit();
            logger.info("addPubDaily success");
        } catch (SQLException e) {
            logger.error("addPubDaily Error: " + e.getMessage());
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException e1) {
                logger.error("RollBack Error: " + e1.getMessage());
            }
        }
    }

    public boolean searchPubArticle(PubArticle article) {
        boolean res = false;
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT id FROM wsa_article WHERE id=?"
                    );
            ps.setString(1, article.getId());
            ResultSet rs = ps.executeQuery();
            //connection.commit();
            if (rs.next()) {
                res = true;
            } else {
                res = false;
            }
        } catch (SQLException e) {
            logger.error("searchPubArticle Error: " + e.getMessage());
        }
        return res;
    }

    public void addPubArticle(PubArticle article) {
        /*
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
         */
        if (searchPubArticle(article)) {
            try {
                PreparedStatement ps = connection
                        .prepareStatement("UPDATE wsa_article SET " + "read_count_week=?,"
                                + "like_count_week=? "
                                +"WHERE id=?"
                        );
                ps.setInt(1, article.getWeekreadnum());
                ps.setInt(2, article.getWeeklikenum());
                ps.setString(3, article.getId());
                ps.executeUpdate();
                connection.commit();
                logger.info("updatePubArticle success");
            } catch (SQLException e) {
                logger.error("updatePubArticle Error: " + e.getMessage());
                try {
                    if (connection != null)
                        connection.rollback();
                } catch (SQLException e1) {
                    logger.error("RollBack Error: " + e1.getMessage());
                }
            }
        } else {
            try {
                PreparedStatement ps = connection
                        .prepareStatement("INSERT INTO wsa_article(id,official_account_id,publish_time,title,summary,url,add_time,statistics_get_time," +
                                "read_count,like_count,ranking,source_url,author,copyright,read_count_week,like_count_week) "
                                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
                        );
                ps.setString(1, article.getId());
                ps.setInt(2, article.getNickname_id());
                ps.setTimestamp(3, Timestamp.valueOf(article.getPosttime()));
                ps.setString(4, article.getTitle());
                ps.setString(5, article.getContent());
                ps.setString(6, article.getUrl());
                //logger.info(article.getAdd_time());
                ps.setTimestamp(7, Timestamp.valueOf(article.getAdd_time()));
                Timestamp t = Timestamp.valueOf("1900-1-1 00:00:00");
                try {
                    t = Timestamp.valueOf(article.getGet_time());
                } catch (Exception timeE) {
                    logger.error(article.getGet_time() + "get time trans Error.");
                }
                ps.setTimestamp(8, t);
                ps.setInt(9, article.getReadnum());
                ps.setInt(10, article.getLikenum());
                ps.setInt(11, article.getTop());
                ps.setString(12, article.getSourceurl());
                ps.setString(13, article.getAuthor());
                ps.setString(14, article.getCopyright());
                ps.setInt(15, article.getWeekreadnum());
                ps.setInt(16, article.getWeeklikenum());
                ps.execute();
                connection.commit();
                logger.info("addPubArticle success");
            } catch (SQLException e) {
                logger.error("addPubArticle Error: " + e.getMessage());
                try {
                    if (connection != null)
                        connection.rollback();
                } catch (SQLException e1) {
                    logger.error("RollBack Error: " + e1.getMessage());
                }
            }
        }
    }

    public void addArticleDaily(PubArticle article) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO wsa_daily_statistics(statistics_id,statistics_type,date,read_count,like_count)"
                                    + " VALUES(?,?,?,?,?)"
                    );
            ps.setString(1, article.getId());
            ps.setInt(2, 2);
            ps.setTimestamp(3, Timestamp.valueOf(article.getPosttime()));
            ps.setInt(4, article.getReadnum());
            ps.setInt(5, article.getLikenum());
            ps.execute();
            connection.commit();
            logger.info("addArticleDaily success");
        } catch (SQLException e) {
            logger.error("addArticleDaily Error: " + e.getMessage());
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException e1) {
                logger.error("RollBack Error: " + e1.getMessage());
            }
        }
    }

    public void addPubArticles(List<PubArticle> pubArticles) {
        for (PubArticle article: pubArticles) {
            addPubArticle(article);
            // 数据库表 冗余, 舍弃存储
            //addArticleDaily(article);
        }
    }

    public void addPubWeek(PubWeek pubWeek) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO wsa_weekly_statistics(official_account_id,date,push_times," +
                    "top_article_readnum,top_article_readnum_change," +
                    "article_count,article_count_change,article_count_10w," +
                    "article_count_10w_change,total_readnum,total_readnum_change,ave_readnum," +
                    "ave_readnum_change,total_likenum,total_likenum_change,ave_likenum,ave_likenum_change," +
                    "max_readnum,max_likenum,like_rate,ranking,ranking_change,WCI,WCI_UP)"
                    + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"
            );
            ps.setInt(1, pubWeek.getNickname_id());
            ps.setDate(2, Date.valueOf(pubWeek.getDate()));
            ps.setInt(3, pubWeek.getUrl_times());
            ps.setInt(4, pubWeek.getUrl_times_readnum());
            ps.setInt(5, pubWeek.getUrl_times_readnum_up());
            ps.setInt(6, pubWeek.getUrl_num());
            ps.setInt(7, pubWeek.getUrl_num_up());
            ps.setInt(8, pubWeek.getUrl_num_10w());
            ps.setInt(9, pubWeek.getUrl_num_10w_up());
            ps.setInt(10, pubWeek.getReadnum_all());
            ps.setInt(11, pubWeek.getReadnum_all_up());
            ps.setInt(12, pubWeek.getReadnum_av());
            ps.setInt(13, pubWeek.getReadnum_av_up());
            ps.setInt(14, pubWeek.getLikenum_all());
            ps.setInt(15, pubWeek.getLikenum_all_up());
            ps.setInt(16, pubWeek.getLikenum_av());
            ps.setInt(17, pubWeek.getLikenum_av_up());
            ps.setInt(18, pubWeek.getReadnum_max());
            ps.setInt(19, pubWeek.getLikenum_max());
            ps.setDouble(20, pubWeek.getLikenum_readnum_rate());
            ps.setInt(21, pubWeek.getRowno());
            ps.setInt(22, pubWeek.getRowno_up());
            ps.setDouble(23, pubWeek.getWci());
            ps.setDouble(24, pubWeek.getWci_up());
            ps.execute();
            connection.commit();
            logger.info("addPubWeek success");
        } catch (SQLException e) {
            logger.error("addPubWeek Error: " + e.getMessage());
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException e1) {
                logger.error("RollBack Error: " + e1.getMessage());
            }
        }
    }

    public void addPubWeeks(List<PubWeek> pubWeeks) {
        for (PubWeek pubWeek: pubWeeks) {
            addPubWeek(pubWeek);
        }
    }


    /*
    ** below are methods using by cleaning program.
     */

    public void writeWSACleaner(Publicer pub, String dateStr, int type) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO wsa_clean(wx_name, wx_nickname, date, id, type) VALUES (?,?,?,?,?)");
            ps.setString(1, pub.getWx_name());
            ps.setString(2, pub.getWx_nickname());
            ps.setString(3, dateStr);
            ps.setInt(4, pub.getId());
            ps.setInt(5, type);
            ps.executeUpdate();
            connection.commit();
            logger.info("writeWSACleaner Ok: " + pub.getWx_name() + " " + pub.getWx_nickname() + " " + dateStr);
        } catch (SQLException e) {
            logger.error("writeWSACleaner Error." + e.getMessage());
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException e1) {
                logger.error("RollBack Error: " + e1.getMessage());
            }
        }
        return;
    }

    public List<WSACleaner> getWSACleaners() {
        List<WSACleaner> cleaners = new ArrayList<WSACleaner>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT wx_name, wx_nickname, date, type, id FROM wsa_clean  where type=1 or type=2 or type=3");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                WSACleaner c = new WSACleaner();
                c.setWx_name(rs.getString("wx_name"));
                c.setWx_nickname(rs.getString("wx_nickname"));
                c.setDate(rs.getString("date"));
                c.setType(rs.getInt("type"));
                c.setId(rs.getInt("id"));
                cleaners.add(c);
            }
            rs.close();
        } catch (SQLException e) {
            logger.error("getWSACleaners Error." + e.getMessage());
        }
        return cleaners;
    }

    public void updateWSACleaner(WSACleaner c) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE wsa_clean SET type = ? WHERE id = ? AND date = ?");
            ps.setInt(1, c.getType());
            ps.setInt(2, c.getId());
            //ps.setString(2, c.getWx_name());
            ps.setString(3, c.getDate());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("updateWSACleaner Error." + e.getMessage());
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException e1) {
                logger.error("RollBack Error: " + e1.getMessage());
            }
        }
    }

    public void updatePubDaily(PubDaily pubDaily) {
        try {
            PreparedStatement ps = connection
                    .prepareStatement("UPDATE wsa_daily_statistics SET read_count= ?, like_count= ?, article_count=? WHERE statistics_id =? AND date= ? ");
            ps.setInt(1, pubDaily.getReadnum_total());
            ps.setInt(2, pubDaily.getLikenum_total());
            ps.setInt(3, pubDaily.getUrl_num_total());
            ps.setString(4, String.valueOf(pubDaily.getStatistics_id()));
            ps.setString(5, pubDaily.getDate());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("updatePubDaily Error: " + e.getMessage());
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException e1) {
                logger.error("RollBack Error: " + e1.getMessage());
            }
        }
    }

    public void deletePubArticle(PubArticle pubArticle) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM wsa_article WHERE id=?");
            ps.setString(1, pubArticle.getId());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("deletePubArticle Error: " + e.getMessage());
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException e1) {
                logger.error("RollBack Error: " + e1.getMessage());
            }
        }
    }

    public void deleteArticleDaily(PubArticle pubArticle) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM wsa_daily_statistics WHERE statistics_id=? AND statistics_type=2");
            ps.setString(1, pubArticle.getId());
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            logger.error("deletePubArticle Error: " + e.getMessage());
            try {
                if (connection != null)
                    connection.rollback();
            } catch (SQLException e1) {
                logger.error("RollBack Error: " + e1.getMessage());
            }
        }
    }

    public void updatePubArticles(List<PubArticle> pubArticles) {
        for (PubArticle article: pubArticles) {
            deletePubArticle(article);
            //deleteArticleDaily(article);
        }
        addPubArticles(pubArticles);
    }

    public PubDaily readPubDaily(int id, String dateStr) {
        boolean flag = false;
        PubDaily pubDaily = new PubDaily();
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT read_count, like_count, article_count FROM wsa_daily_statistics WHERE statistics_type=1 AND statistics_id=? AND date=?"
                    );
            ps.setString(1, String.valueOf(id));
            ps.setString(2, dateStr);
            ResultSet rs = ps.executeQuery();
            //connection.commit();
            while (rs.next()) {
                flag = true;
                pubDaily.setId(id);
                pubDaily.setReadnum_total(rs.getInt("read_count"));
                pubDaily.setLikenum_total(rs.getInt("like_count"));
                pubDaily.setUrl_num_total(rs.getInt("article_count"));
            }
        } catch (SQLException e) {
            logger.error("readPubDaily Error: " + e.getMessage());
        }
        if (flag) {
            return pubDaily;
        } else {
            return null;
        }
    }

    public List<PubArticle> readPubArticles(int id, String dateStr) {
        boolean flag = false;
        List<PubArticle> pubArticles = new ArrayList<PubArticle>();
        try {
            PreparedStatement ps = connection
                    .prepareStatement("SELECT read_count, like_count FROM wsa_article WHERE official_account_id=? AND publish_time like ?"
                    );
            ps.setString(1, String.valueOf(id));
            ps.setString(2, dateStr+"%");
            ResultSet rs = ps.executeQuery();
            //connection.commit();
            while (rs.next()) {
                //logger.info(id + " " + dateStr + " find one");
                flag = true;
                PubArticle pubArticle = new PubArticle();
                pubArticle.setReadnum(rs.getInt("read_count"));
                pubArticle.setLikenum(rs.getInt("like_count"));
                pubArticle.setNickname_id(id);
                pubArticle.setPosttime(dateStr);
                pubArticles.add(pubArticle);
            }
        } catch (SQLException e) {
            logger.error("redPubArticles Error: " + e.getMessage());
        }
        if (flag) {
            return pubArticles;
        } else {
            return null;
        }
    }
}