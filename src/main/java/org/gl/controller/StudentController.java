package org.gl.controller;


import java.net.URI;
import java.util.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.gl.entity.Marks;
import org.gl.entity.Student;
import org.gl.exception.StudentUpdateDelete;
import org.gl.service.StudentService;


@Path("/students")
@Tag(name = "Student Resources", description = "Student Data RESt APIs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class StudentController {

    //bean instantiation of service interface
    private final StudentService studentService;

    //Injection point of service interface
    @Inject
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
   
    //method to Get a Student by id
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

    @POST
    @Path("/Marks/{id}/{subId}")
    public Response insertMarks(@Valid Marks marks,@PathParam("id") Long id,@PathParam("subId") Long subId){
        studentService.saveMarks(marks,id,subId);
        return Response.created(URI.create("/marks/" + marks.getId())).build();
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
    public Response updateStudent(@PathParam("id") Long id, @Valid Student student) throws StudentUpdateDelete{
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
    @Path("/changeStudentData/{id}")
    public Response changeStudentDataPatch(@PathParam("id") Long id, @Valid Student student) throws StudentUpdateDelete{
        studentService.changeStudentData(id, student);
        return Response.status(204).build();
    }







    @GET
    @Path("allStudentsData")
    public Response getAllStudent(@QueryParam("field") String field,@QueryParam("names") String names,@QueryParam("offset") int offset,@QueryParam("pageSize") int pageSize) {

        try{
            List<Student> students;
            students = studentService.getAll(field);
            if(field == null){
               students = studentService.getAll();
            }else{
                students = studentService.getAllStudentDescendingOrder(field);
            }

            if(names != null){
                students = studentService.findStudentsWithName(names);
            }

            if(pageSize != 0 && offset >= 0){
                students = studentService.findStudentWithpagination(offset, pageSize);
            }

            return Response.ok(students).build();
        }catch (Exception e){
            return Response.noContent().build();
        }
    }

}
