package org.gl.entity;


import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnore;



//Student entity class and PanacheEntity to utilize repository 
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student{
        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotBlank
    @Size(min = 2,max = 20)
    @Schema(example = "Shubham")
    private String names;

    @NotBlank
    @Size(min = 2,max = 20)
    @Schema(example = "20")
    private int age;

    @NotBlank
    @Size(min = 2,max = 20)
    @Schema(example = "kalwar Road")
    private String address;
    @OneToMany(targetEntity = Subjects.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "stu_fk", referencedColumnName = "id")
    private List<Subjects> subjects;



    public Student toUser() {
        Student user = new Student();
        user.setAddress(address);
        user.setAge(age);
        user.setNames(names);
        user.setSubjects(subjects);
        return user;
    }
}
