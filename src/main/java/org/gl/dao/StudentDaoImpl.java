package org.gl.dao;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import org.gl.dto.StudentDto;
import org.gl.entity.Marks;
import org.gl.entity.Student;
import org.gl.entity.Subjects;
import org.gl.exception.StudentUpdateDelete;
import org.gl.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class StudentDaoImpl implements StudentDao{

    @Inject
    UserRepository userRepository;




    @Override
    public Optional<Student> getStudentById(Long id) {
        return userRepository.findByIdOptional(id);
    }

    @Override
    public Student getStudentsById(Long id) throws StudentUpdateDelete {
        return userRepository.findByIdOptional(id).orElseThrow(() -> new StudentUpdateDelete(""));
    }

    //method to update student data whose is present
    @Transactional
    @Override
    public Student updateStudent(Long id, Student student) throws StudentUpdateDelete {
        Student existingUser = getStudentsById(id);
        existingUser.setNames(student.getNames());
        existingUser.setAge(student.getAge());
        existingUser.setSubjects(student.getSubjects());
        userRepository.persist(existingUser);
        return existingUser;
    }

    //Method to insert new Student
    @Transactional
    @Override
    public Student saveStudent(Student student) {
        userRepository.persist(student);
        return student;
    }

    @Transactional
    @Override
    public boolean deleteStudent(Long id){
        return userRepository.deleteById(id);
    }



    @Override
    public List<Student> getAll() {

        return this.userRepository.listAll();
    }

    @Override
    public List<Student> getAll(String field) {
        return this.userRepository.listAll(Sort.by(field));
    }

    @Override
    public List<Student> getAllStudentDescendingOrder(String field) {

        return this.userRepository.listAll(Sort.by(field).descending());
    }


    @Override
    public List<Student> findStudentWithPagination(int offset, int pageSize) {

        return userRepository.findAll().page(Page.of(offset, pageSize)).list();
    }

    @Override
    public List<Student> findStudentsWithName(String names) {
        return userRepository.findByNames(names);
    }

    @Override
    public StudentDto fetchStudentWithAverageMarks(Long id) throws StudentUpdateDelete {
        Student existingStudent = getStudentsById(id);
        StudentDto studentDto = new StudentDto();
        studentDto.setId(existingStudent.getId());
        studentDto.setNames(existingStudent.getNames());
        studentDto.setAge(existingStudent.getAge());
        studentDto.setAddress(existingStudent.getAddress());
        studentDto.setSubjects(existingStudent.getSubjects());
        List<Subjects> ls = existingStudent.getSubjects();
        int sum = 0;
        int count = 1;
        for (Subjects l : ls) {
            Marks n = l.getMarks();
            int m = n.getMark();
            sum += m;
            count++;
        }
        studentDto.setAverageMarks(sum/count);
        return studentDto;
    }

    @Override
    @Transactional
    public Student changeStudentData(Long id) throws StudentUpdateDelete {

        return null;
    }
}
