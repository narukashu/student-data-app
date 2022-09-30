package org.gl.service;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.gl.dao.StudentDao;
import org.gl.entity.Student;
import org.gl.exception.StudentUpdateDelete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@QuarkusTest
public class StudentServiceTest {

    @InjectMock
    StudentDao studentDao;

    @Inject
    StudentService studentService;
    private Student student;


    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(5L);
        student.setNames("Shubham Naruka");
        student.setAddress("Kalwar Road");
        student.setAge(20);

    }

    @Test
    void saveStudent() {
        Mockito.when(studentDao.saveStudent(student)).thenReturn(student);

        Student student1 = studentService.saveStudent(student);
        assertNotNull(student1);

        assertEquals(student1, student);
        assertEquals(student1.getNames(),student.getNames());
    }

    @Test
    void getStudentById() throws StudentUpdateDelete {
        Mockito.when(studentDao.getStudentsById(5L)).thenReturn(student);

        Student student1 = studentService.getStudentsById(5L);
        assertNotNull(student1);
        assertEquals(student1, student);
        assertEquals(student1.getNames(),student.getNames());
    }

    @Test
    void updateStudent() throws StudentUpdateDelete {
        Student updateStudent = new Student();
        updateStudent.setNames("Wash");
        Mockito.when(studentDao.getStudentById(student.getId())).thenReturn(Optional.of(student));
        studentService.updateStudent(5L,updateStudent);
        verify(studentDao).saveStudent(updateStudent);
        verify(studentDao).getStudentById(student.getId());
    }

/*


        verify(userRepository).findById(user.getId());
    }

    @Test(expected = RuntimeException.class)
    public void should_throw_exception_when_user_does'nt_exist() {
        User user = new User();
        user.setId(89L);
        user.setName("Test Name");

        User newUser = new User();
        newUser.setId(90L);
        user.setName("New Test Name");

        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        updateUserService.updateUser(user.getId(), newUser);
    }
}
    */

    @Test
    void changeAddress() {
    }

    @Test
    void deleteStudent() {
        Mockito.when(studentDao.deleteStudent(5L)).thenReturn(true);
        boolean bool = studentService.deleteStudent(5L);
        assertTrue(bool);
    }

    @Test
    void getAll() {
        List<Student> students = new ArrayList<>();
        students.add(student);
        Mockito.when(studentDao.getAll()).thenReturn(students);
        List<Student> studentList = studentService.getAll();
        assertNotNull(studentList);
        assertEquals(students,studentList);

    }

    @Test
    void testGetAll() {
        List<Student> students = new ArrayList<>();
        students.add(student);
        String field = "names";
        Mockito.when(studentDao.getAll(field)).thenReturn(students);
        List<Student> studentList = studentService.getAll(field);
        assertNotNull(studentList);
        assertEquals(students,studentList);
    }

    @Test
    void getAllStudentDescendingOrder() {
        List<Student> students = new ArrayList<>();
        students.add(student);
        String field = "names";
        Mockito.when(studentDao.getAllStudentDescendingOrder(field)).thenReturn(students);
        List<Student> studentList = studentService.getAllStudentDescendingOrder(field);
        assertNotNull(studentList);
        assertEquals(students,studentList);
    }

    @Test
    void findStudentWithPagination() {
        List<Student> students = new ArrayList<>();
        students.add(student);
        Mockito.when(studentDao.findStudentWithPagination(0,2)).thenReturn(students);
        List<Student> studentList = studentService.findStudentWithPagination(0,2);
        assertNotNull(studentList);
        assertEquals(students,studentList);

    }

    @Test
    void findStudentsWithName() {
        List<Student> students = new ArrayList<>();
        students.add(student);
        Mockito.when(studentDao.findStudentsWithName("Shubham")).thenReturn(students);
        List<Student> studentList = studentService.findStudentsWithName("Shubham");
        assertNotNull(studentList);
        assertEquals(students,studentList);

    }
}
