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

    public SchoolInformation(String college, String Faculty, String Major){
        this.college = college;
        this.Faculty = Faculty;
        this.Major = Major;
    }
}
