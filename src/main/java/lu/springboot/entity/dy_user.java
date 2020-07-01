package lu.springboot.entity;

import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
@ToString
public class dy_user {
    private String user_id;
    private String user_name;
    private String user_pwd;
    private int user_sex;
    private String user_tele;
    private int sort;
    private String role_id;
    private String school_id;
    private String school_parent_id;
    private int is_disabled;
    private int is_deleted;
    private Date gmt_create;
    private Date gmt_modified;

    public dy_user(){}

    public dy_user(String UserID, String UserName, int Sex, String Tele, String PassWord){
        this.user_id = UserID;
        this.user_name = UserName;
        this.user_sex = Sex;
        this.user_tele = Tele;
        this.user_pwd = PassWord;
    }
}

