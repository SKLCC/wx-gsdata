package sklcc.ws.entity;

/**
 * Created by sukai on 12/11/15.
 */
public class GroupPublicer {

    private int groupId;
    private int nicknameId;
    private String wx_nickname;
    private String wx_name;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getNicknameId() {
        return nicknameId;
    }

    public void setNicknameId(int nicknameId) {
        this.nicknameId = nicknameId;
    }

    public String getWx_nickname() {
        return wx_nickname;
    }

    public void setWx_nickname(String wx_nickname) {
        this.wx_nickname = wx_nickname;
    }

    public String getWx_name() {
        return wx_name;
    }

    public void setWx_name(String wx_name) {
        this.wx_name = wx_name;
    }
}
