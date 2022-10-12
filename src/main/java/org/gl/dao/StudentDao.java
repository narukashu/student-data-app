package org.gl.dao;

import org.gl.dto.StudentDto;
import org.gl.entity.Student;
import org.gl.exception.StudentUpdateDelete;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StudentDao {
    Optional<Student> getStudentById(Long id);

    Student getStudentsById(Long id) throws StudentUpdateDelete;
    //Method to update Student
    Student updateStudent(Long id, Student student) throws StudentUpdateDelete;

    //Method to insert new Student
    Student saveStudent(Student student);

    Student changeStudentData(Long id, Map <String, Object> updates) throws StudentUpdateDelete;

    boolean deleteStudent(Long id);

    @SqlUpdate("SELECT * FROM \"marks\" ")
    List<Student> getAll();

    List<Student>  getAll(String field);

    List<Student> getAllStudentDescendingOrder(String field);

    List<Student> findStudentWithPagination(int offset,int pageSize);

    List<Student> findStudentsWithName(String names);

    StudentDto fetchStudentWithAverageMarks(Long id) throws StudentUpdateDelete;


}
