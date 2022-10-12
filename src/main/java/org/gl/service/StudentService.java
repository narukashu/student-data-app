package org.gl.service;


import java.util.List;
import java.util.Optional;

import org.gl.dto.StudentDto;
import org.gl.entity.Student;
import org.gl.exception.StudentUpdateDelete;

//Service class of the Student 
public interface StudentService {
    //method to get the student data using id
    Optional<Student> getStudentById(Long id);

    Student getStudentsById(Long id) throws StudentUpdateDelete;

    //Method to update Student
    Student updateStudent(Long id, Student student) throws StudentUpdateDelete;

    //Method to insert new Student
    Student saveStudent(Student student);

    Student changeStudentData(Student student) throws StudentUpdateDelete;

    boolean deleteStudent(Long id);

    List<Student>  getAll();

    List<Student>  getAll(String field);

    List<Student> getAllStudentDescendingOrder(String field);

    List<Student> findStudentWithPagination(int offset,int pageSize);

    List<Student> findStudentsWithName(String names);



    // demo method

    /*List<StudentDto> fetchAllStudents();*/
    StudentDto fetchAllStudentsByDto(Long id) throws StudentUpdateDelete;
}
