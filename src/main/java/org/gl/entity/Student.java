package org.gl.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnore;



//Student entity class and PanacheEntity to utilize repository 
@Entity
public class Student{
        
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @NotBlank
    @Size(min = 2,max = 20)
    private String names;

    @NotBlank
    @Size(min = 2,max = 20)
    private int age;

    @NotBlank
    @Size(min = 2,max = 20)
    private String address;
    @OneToMany(targetEntity = Subjects.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "stud_fk", referencedColumnName = "id")
    private List<Subjects> subjects;
    
    public Student() {
    }

    public Student(Long id, String names, int age, List<Subjects> subjects, String address) {
        this.id = id;
        this.names = names;
        this.age = age;
        this.subjects = subjects;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Schema(example = "Shubham")
    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }


    @Schema(example = "20")
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Subjects> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subjects> subjects) {
        this.subjects = subjects;
    }


    @Schema(example = "kalwar Road")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Student toUser() {
        Student user = new Student();
        user.setNames(names);
        user.setAge(age);
        user.setSubjects(subjects);
        user.setAddress(address);
        return user;
    }
}
