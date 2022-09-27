package org.gl.service;


import java.util.List;
import java.util.Optional;

import org.gl.entity.Marks;
import org.gl.entity.Student;
import org.gl.exception.StudentUpdateDelete;

//Service class of the Student 
public interface StudentService {
    //method to get the student data using Id
    Optional<Student> getStudentById(Long id);

    //Method to update Student
    Student updateStudent(Long id, Student student) throws StudentUpdateDelete;

    //Method to insert new Student
    Student saveStudent(Student student);

    Marks saveMarks(Marks marks,Long id,Long subId);
    Student changeStudentData(Long id,Student student) throws StudentUpdateDelete;

    boolean deleteStudent(Long id);

    List<Student>  getAll();

    List<Student>  getAll(String field);

    List<Student> getAllStudentDescendingOrder(String field);

    List<Student> findStudentWithpagination(int offset,int pageSize);

    List<Student> findStudentsWithName(String names);
}
