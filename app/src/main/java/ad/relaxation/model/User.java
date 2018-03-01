package ad.relaxation.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/15 0015.
 * 用户实体类
 */

public class User implements Serializable{
    private String name;
    private String password;
    private String uid;
    public int userid;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }


    public User(String name, String uid, int userid) {
        this.name = name;
        this.uid = uid;
        this.userid = userid;
    }
    public User(String name, String password, String uid, int userid) {
        this.name = name;
        this.password = password;
        this.uid = uid;
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
