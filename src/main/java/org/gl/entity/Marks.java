package org.gl.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Marks {

    @Id
    private int id;
    private int mark;

    @OneToOne(targetEntity = Student.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "student_fk",referencedColumnName = "id")
    private Student student;

    @OneToOne(targetEntity = Subjects.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_fk1",referencedColumnName = "id")
    private Subjects subjects;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subjects getSubjects() {
        return subjects;
    }

    public void setSubjects(Subjects subjects) {
        this.subjects = subjects;
    }

    public Marks(int id, int mark, Student student, Subjects subjects) {
        this.id = id;
        this.mark = mark;
        this.student = student;
        this.subjects = subjects;
    }

    public Marks() {
    }

    
   
}
