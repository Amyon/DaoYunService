package outfox.springboot.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Fanyi {
    private Integer id;
    private String devToken;
    private String imei;
}
