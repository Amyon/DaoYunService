package lu.springboot.entity;

import lombok.Data;
import lombok.ToString;

import java.sql.Date;

@Data
@ToString
public class dy_school_Info {

    private String school_id;

    private String name;

    private String school_parent_id;

    private int sort;

    private Date gmt_create;

    private Date gmt_modified;

    public dy_school_Info(String name, String school_parent_id){
//        this.school_id = school_id;
        this.name = name;
        this.school_parent_id = school_parent_id;
        this.sort = sort;
    }
}
