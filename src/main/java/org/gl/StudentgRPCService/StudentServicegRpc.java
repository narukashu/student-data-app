package org.gl.StudentgRPCService;

import io.grpc.stub.StreamObserver;
import io.quarkus.example.Student;
import io.quarkus.example.StudentDataGrpc;
import io.quarkus.example.Subjects;
import io.quarkus.grpc.GrpcService;
import org.gl.entity.Marks;
import org.gl.service.StudentService;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@GrpcService
public class StudentServicegRpc extends StudentDataGrpc.StudentDataImplBase {


    private final StudentService studentService;

    @Inject
    public StudentServicegRpc(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void create(Student student, StreamObserver<Student> responseObserver){
        Long id = student.getId();
        String name = student.getName();
        int age = student.getAge();
        String address = student.getAddress();
        org.gl.entity.Student student1 = new org.gl.entity.Student();
        org.gl.entity.Subjects subjects1 = new org.gl.entity.Subjects();
        List<org.gl.entity.Subjects> subjectsEntity = new ArrayList<>();
        Marks marks = new Marks();

        List<Subjects> subjects = new ArrayList<>();
        for(int i =0;i<student.getSubjectsCount();i++){
            subjects.add(student.getSubjects(i));
            subjects1.setSubjectName(student.getSubjects(i).getName());
            marks.setMark(student.getSubjects(i).getMark().getMarks());
            subjects1.setMarks(marks);
            subjectsEntity.add(subjects1);
        }

        student1.setNames(name);
        student1.setAge(age);
        student1.setAddress(address);
        student1.setSubjects(subjectsEntity);

        studentService.saveStudent(student1);


        responseObserver.onNext(Student.newBuilder().setId(id).setName(name).setAge(age).setAddress(address).addAllSubjects(subjects).build());
        responseObserver.onCompleted();

    }
}
