package lu.springboot.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class checkin {

    private int checkin_id;
    private String student_id;
    private int present_id;

    public checkin(String user_id, int present_id) {
        this.student_id = user_id;
        this.present_id = present_id;

    }
}
