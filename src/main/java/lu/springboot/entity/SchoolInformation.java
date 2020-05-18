package lu.springboot.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SchoolInformation {

    private int ID;

    private String college;

    private String Faculty;

    private String Major;
}
