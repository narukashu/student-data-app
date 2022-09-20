package org.gl.controller;


import java.net.URI;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.gl.entity.Student;
import org.gl.exception.StudentUpdateDelete;
import org.gl.service.StudentService;


@Path("/students")
@Tag(name = "Student Resources", description = "Student Data RESt APIs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class StudentController {

    //bean instation of service interface
    private final StudentService studentService;

    //Injection point of service interface
    @Inject
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
   
    //method to Get a Student by Id
    @GET
    @Operation(
        operationId = "getStudent",
        summary = "Get Student Using Id",
        description = "To Get a student"
    )
    @APIResponse(
        responseCode = "200",
        description = "Operation Completed",
        content = @Content(mediaType = MediaType.APPLICATION_JSON,
        schema = @Schema(implementation = Student.class))
    )
    @Path("/{id}")
    public Response getStudent(@PathParam("id") Long id) {
        return studentService.getStudentById(id).map(student -> Response.ok(student).build()).orElse(Response.status(Status.NOT_FOUND).build());
    }

    //method to Create a student

    @POST
    @Operation(
        operationId = "addStudent",
        summary = "Add New Student",
        description = "To add New student"
    )
    @APIResponse(
        responseCode = "201",
        description = "Operation Completed",
        content = @Content(mediaType = MediaType.APPLICATION_JSON,
        schema = @Schema(implementation = Student.class))
    )
    public Response createUser(@Valid Student student)  {
        studentService.saveStudent(student.toUser());
        return Response.created(URI.create("/students/" + student.getId())).build();
    }

    //method to Update a student
    @PUT
    @Operation(
        operationId = "updateStudent",
        summary = "Update Student Data",
        description = "To Update student Data"
    )
    @APIResponse(
        responseCode = "204",
        description = "Operation Completed",
        content = @Content(mediaType = MediaType.APPLICATION_JSON,
        schema = @Schema(implementation = Student.class))
    )
    @Path("/{id}")
    public Response updatStudent(@PathParam("id") Long id, @Valid Student student) throws StudentUpdateDelete{
         studentService.updateStudent(id, student.toUser());
         return Response.status(204).build();
    }

    //method to delete student
    @DELETE
    @Operation(
        operationId = "deleteStudent",
        summary = "delete Student",
        description = "To Delete student Data"
    )
    @APIResponse(
        responseCode = "204",
        description = "Operation Completed",
        content = @Content(mediaType = MediaType.APPLICATION_JSON,
        schema = @Schema(implementation = Student.class))
    )
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") Long id){
        boolean deleted = studentService.deleteStudent(id);
        return deleted ? Response.noContent().build() : Response.status(Status.NO_CONTENT).build();
    }


    @PATCH
    @Operation(
        operationId = "changeStudentAddress",
        summary = "To change the Address of the student",
        description = "Change the student address only"
    )
    @Path("/changeAddress/{id}")
    public Response changeAddress(@PathParam("id") Long id, @Valid Student student) throws StudentUpdateDelete{
        studentService.changeAddress(id, student);
        return Response.status(204).build();
    }

    @GET
    @Operation(
        operationId = "getListStudent",
        summary = "Get all the students",
        description = "To Get a List of student"
    )
    public Response getAllStudents(){
        List<Student> students = studentService.getAll();
        return Response.ok(students).build();
    }

    @GET
    @Operation(
        operationId = "sortStudentByColoumn",
        summary = "Get all the students with specified coloumn",
        description = "for example To Get a student sort by their age, names or id"
    )
    @Path("sort/{field}")
    public List<Student> getAllStudents(@PathParam("field") String field){  
        return studentService.getAll(field);
    }

    @GET
    @Operation(
        operationId = "sortStudentByColoumn",
        summary = "Get all the students with specified coloumn",
        description = "for example To Get a student sort by their age, names or id"
    )
    @Path("sortDescending/{field}")
    public List<Student> getAllStudentsDescendingOrder(@PathParam("field") String field){  
        return studentService.getAllStudentDescendingOrder(field);
    }


    @GET
    @Operation(
        operationId = "getLimitedStudents",
        summary = "Get limited students based on the page size",
        description = "for example To Get a student with page size, Here, pageSize is to denote the no. of students shown and offset is for next page"
    )
    @Path("/pagination/{offset}/{pageSize}")
    public List<Student> getAllStudentswithPages(@PathParam("offset") int offset,@PathParam("pageSize") int pageSize){
       return studentService.findStudentWithpagination(offset, pageSize);
    }

    @GET
    @Operation(
        operationId = "getStudent",
        summary = "Get all the students with specified name",
        description = "for example To Get a student with name shubham put name of student in names string"
    )
    @Path("/names/{names}")
    public List<Student> getAllStudentWithName(@PathParam("names") String names){
        return studentService.findStudentsWithName(names);
    }

}
