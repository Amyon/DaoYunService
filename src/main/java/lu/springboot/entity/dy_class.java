package lu.springboot.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class dy_class {
    private int class_id;
    private String user_id;
    private int score;

    public dy_class(int class_id, String user_id) {
        this.class_id = class_id;
        this.user_id = user_id;
        this.score = 0;

    }
}

