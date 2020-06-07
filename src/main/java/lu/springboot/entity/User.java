package lu.springboot.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
    private Integer ID;
    private String UserID;
    private String UserName;
    private String Sex;
    private Integer SchoolInfo;
    private String Tele;
    private String PassWord;


    public User(String UserID, String UserName, String Sex, String Tele, String PassWord){
        this.UserID = UserID;
        this.UserName = UserName;
        this.Sex = Sex;
        this.Tele = Tele;
        this.PassWord = PassWord;
    }
}


