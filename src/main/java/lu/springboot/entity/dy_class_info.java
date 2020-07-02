package lu.springboot.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class dy_class_info {
    private int class_id;
    private String class_name;
    private String course_name;

    private String section;
    private String school_info;

    private String user_id;

    public dy_class_info(String class_name, String course_name, String section, String school_info, String user_id) {
        this.class_name = class_name;
        this.course_name = course_name;
        this.section = section;
        this.school_info = school_info;
        this.user_id = user_id;
    }
}
