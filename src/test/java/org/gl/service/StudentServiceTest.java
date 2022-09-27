package org.gl.service;

import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.gl.entity.Student;
import org.gl.exception.StudentUpdateDelete;
import org.gl.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;


import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class StudentServiceTest {

    @InjectMock
    private UserRepository userRepository;

    @Inject
    private StudentsServiceImpl studentsService;

    private Student student;


    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(5l);
        student.setNames("Shubham Naruka");
        student.setAddress("Kalwar Road");
        student.setAge(20);

    }

    @Test
    void saveStudent() {
        Mockito.doNothing().when(userRepository).persist(
                ArgumentMatchers.any(Student.class)
        );

        Mockito.when(userRepository.isPersistent(
                ArgumentMatchers.any(Student.class)
        )).thenReturn(true);

        Student student1 = studentsService.saveStudent(student);
        assertNotNull(student1);

        assertEquals(student1, student);
        assertEquals(student1.getNames(),student.getNames());
    }

    @Test
    void getStudentById() throws StudentUpdateDelete {
        Mockito.when(userRepository.findByIdOptional(5L)).thenReturn(Optional.of(student));

        Student student1 = studentsService.getStudentsById(5L);
        assertNotNull(student1);
        assertEquals(student1, student);
        assertEquals(student1.getNames(),student.getNames());
    }

    @Test
    void updateStudent() throws StudentUpdateDelete {
        Student updateStudent = new Student();
        updateStudent.setNames("Wash");
        Mockito.when(userRepository.findByIdOptional(5L)).thenReturn(Optional.of(student));

        Student newStudent = studentsService.updateStudent(5L,updateStudent);
        assertNotNull(newStudent);
        assertEquals(updateStudent.getNames(),newStudent.getNames());
    }



    @Test
    void changeAddress() {
    }

    @Test
    void deleteStudent() {
        Mockito.when(userRepository.deleteById(5L)).thenReturn(true);
        boolean bool = studentsService.deleteStudent(5L);
        assertTrue(bool);
    }

    @Test
    void getAll() {
        List<Student> students = new ArrayList<>();
        students.add(student);
        Mockito.when(userRepository.listAll()).thenReturn((List<Student>) students);
        List<Student> studentList = studentsService.getAll();
        assertNotNull(studentList);
        assertEquals(students,studentList);

    }

    @Test
    void testGetAll() {

    }

    @Test
    void getAllStudentDescendingOrder() {
    }

    @Test
    void findStudentWithPagination() {
    }

    @Test
    void findStudentsWithName() {
    }
}
