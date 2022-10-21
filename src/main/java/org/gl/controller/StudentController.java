package org.gl.controller;


import java.util.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonMergePatch;
import javax.json.JsonPatch;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import com.github.fge.jsonpatch.JsonPatchException;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.gl.dto.StudentDto;
import org.gl.entity.Student;
import org.gl.exception.StudentNotFoundException;
import org.gl.exception.StudentUpdateDelete;
import org.gl.mapper.JsonMergePatchMapper;
import org.gl.mapper.JsonPatchMapper;
import org.gl.repository.UserRepository;
import org.gl.service.StudentService;


@Path("/students")
@Tag(name = "Student Resources", description = "Student Data RESt APIs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class StudentController {

    //bean instantiation of service interface
    private final StudentService studentService;

    private ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final JsonPatchMapper<Student> mapper;
    //Injection point of service interface
    private final JsonMergePatchMapper<Student> mergeMapper;
    @Inject
    public StudentController(StudentService studentService, UserRepository userRepository, JsonPatchMapper<Student> mapper, JsonMergePatchMapper<Student> mergeMapper) {
        this.studentService = studentService;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.mergeMapper = mergeMapper;
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
    public Response createUser(@Valid Student student) {
        studentService.saveStudent(student.toUser());
        return Response.ok(student).build();
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
    public Response updateStudent(@PathParam("id") Long id, @Valid Student student) throws StudentUpdateDelete {
        studentService.updateStudent(id, student.toUser());
        return Response.status(200).build();
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
    public Response deleteUser(@PathParam("id") Long id) {
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
    public Response changeStudentDataPatch(@PathParam("id") Long id, @RequestBody JsonPatch patch) throws StudentUpdateDelete, JsonPatchException, JsonProcessingException, StudentNotFoundException {
        Student student = studentService.getStudentsById(id);

        Student studentPatched= mapper.apply(student,patch);

        userRepository.save(student);
        return Response.noContent().build();
    }

/*    @PATCH
    @Operation(
            operationId = "changeStudentAddress",
            summary = "To change the Address of the student",
            description = "Change the student address only"
    )
    @Path("/changeStudentData/{id}")
    public Response updateStudentDataPatch(@PathParam("id") Long id, @RequestBody JsonPatch patch) throws StudentUpdateDelete, JsonPatchException, JsonProcessingException, StudentNotFoundException {
        Student student = userRepository.findOne(id);
        *//*Student studentPatched= mapper.apply(student,patch);*//*

        Student studentPatched = applyPatchToCustomer(patch,student);

        userRepository.save(student);
        return Response.noContent().build();
    }*/
 /*   private Student applyPatchToCustomer(
            JsonPatch patch, Student targetCustomer) throws JsonPatchException, JsonProcessingException {
        JsonNode patched = patch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
        return objectMapper.treeToValue(patched, Student.class);
    }*/




    @GET
    @Path("allStudentsData")
    public Response getAllStudent(@QueryParam("field") String field, @QueryParam("names") String names, @QueryParam("offset") int offset, @QueryParam("pageSize") int pageSize) {

        try {   
            List<Student> students;

            if (field != null && names == null) {
                students = studentService.getAll(field);
            } else {
                if (field == null && names != null) {
                    students = studentService.findStudentsWithName(names);
                } else {
                    if (field == null && pageSize >= 0) {
                        students = studentService.findStudentWithPagination(offset, pageSize);
                    } else {
                        students = studentService.getAll();
                    }
                }
            }
            return Response.ok(students).build();
        } catch (Exception e) {
            return Response.noContent().build();
        }
    }

    @GET
    @Path("/studentData/{id}")
    public StudentDto fetchAllStudents(@PathParam("id") Long id) throws StudentUpdateDelete {

       return studentService.fetchAllStudentsByDto(id);
    }
}




