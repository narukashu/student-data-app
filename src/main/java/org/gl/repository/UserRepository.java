package org.gl.repository;


import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.gl.entity.Student;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import org.gl.exception.StudentNotFoundException;

//Application Scoped will create instance only once for the duration of the whole application
@ApplicationScoped
public class UserRepository implements PanacheRepository<Student>{
    //PanacheRepository interface to implement predefined implemented methods
    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Student> findByNames(String names){
        return list("SELECT s FROM Student s WHERE s.names = ?1 ORDER BY id", names);
    }

    public Student save(Student patchedStudent) throws StudentNotFoundException {

        students.removeIf(student -> student.getId() == patchedStudent.getId());
        students.add(patchedStudent);

        return findOne(patchedStudent.getId());
    }

    public Student findOne(Long id) throws StudentNotFoundException {
        return students.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException("Person with id {} not found"));
    }


}
