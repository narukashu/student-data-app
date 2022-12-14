package org.gl.controller;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.gl.entity.Student;
import org.gl.exception.StudentUpdateDelete;
import org.gl.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class StudentControllerTest {


    @InjectMock
    UserRepository userRepository;

    @Inject
    StudentController studentController;

    private Student student;

   
    @BeforeEach
  void setUp() {
    student = new Student();
    student.setNames("Shubham");
    student.setId(1L);
  }

    @Test
    void testCreateUser() {

      Mockito.doNothing().when(userRepository).persist(
          ArgumentMatchers.any(Student.class)
      );

      Mockito.when(userRepository.isPersistent(
        ArgumentMatchers.any(Student.class)
      )).thenReturn(true);

      Student newStudent = new Student();
      newStudent.setNames("Shubham");
      newStudent.setAge(20);
      newStudent.setAddress("Kalwar");

      Response response = studentController.createUser(newStudent);
      assertNotNull(response);
      assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
      assertNotNull(response.getEntity());
    }


    //test case for get the user
    @Test
    void testGetStudent()  {
        
        Mockito.when(userRepository.findByIdOptional(1L)).thenReturn(Optional.of(student));

       Response response = studentController.getStudent(1L);
       assertNotNull(response);
       assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
       assertNotNull(response.getEntity());
       Student entity = (Student) response.getEntity();
       assertEquals(1L, entity.getId());

    }

    //test case for get the user
    @Test
    void testGetStudentKo() {
        
        Mockito.when(userRepository.findByIdOptional(11L)).thenReturn(Optional.empty());

       Response response = studentController.getStudent(11L);
       assertNotNull(response);
       assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
       assertNull(response.getEntity());

    }

    @Test
    void testUpdateStudent() throws StudentUpdateDelete {
      Student updateStudent = new Student();
      updateStudent.setNames("Yash");
      Mockito.when(userRepository.findByIdOptional(1L)).thenReturn(Optional.of(student));

      Response response = studentController.updateStudent(1L, updateStudent);

      assertNotNull(response);
      assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
      assertNull(response.getEntity());

    }

    @Test
    void testDeleteUser() throws StudentUpdateDelete {
      Mockito.when(userRepository.deleteById(1L)).thenReturn(true);
      Response response = studentController.deleteUser(1L);
      assertNotNull(response);
      assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
      assertNull(response.getEntity());
    }

}
