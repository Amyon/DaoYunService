package lu.springboot.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class present {
    private int id;
    private int class_id;
    private String longitude;
    private String latitude;
    private int state;

    public present(int class_id, String longitude, String latitude, int state) {

        this.class_id = class_id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.state = state;
    }
    public int getId(){
        return this.id;
    }
    public int getClass_id(){
        return this.class_id;
    }

    public String getLongitude(){
        return this.longitude;
    }
    public String getLatitude(){
        return this.latitude;
    }
}
