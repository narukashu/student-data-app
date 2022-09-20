package org.gl.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.gl.entity.Student;
import org.gl.exception.StudentUpdateDelete;
import org.gl.repository.UserRepository;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
//Implementation of the service class All business logic are present here
@ApplicationScoped
public class StudentsServiceImpl implements StudentService{
    //Injecting repository bean in Service class
    private final UserRepository userRepository;

    @Inject
    public StudentsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //method to get the student data using Id and using findByIdOptional present in repository interface
    @Override
    public Optional<Student> getStudentById(Long id) {
        return userRepository.findByIdOptional(id);
    }

    public Student getStudentsById(long id) throws StudentUpdateDelete {
        return userRepository.findByIdOptional(id).orElseThrow(() -> new StudentUpdateDelete(""));
    }

    //method to update student data whose is is present
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
    @Transactional
    public Student changeAddress(Long id,Student student) throws StudentUpdateDelete {
        Student exiStudent =  getStudentsById(id);
        exiStudent.setAddress(student.getAddress());
        return student;
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
    public List<Student> findStudentWithpagination(int offset, int pageSize) {

        
        
        return userRepository.findAll().page(Page.of(offset, pageSize)).list();
    }

    @Override
    public List<Student> findStudentsWithName(String names) {
        
        return userRepository.findByNames(names);
    }

  
    
}

