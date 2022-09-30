package org.gl.service;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.gl.dao.StudentDao;
import org.gl.entity.Marks;
import org.gl.entity.Student;
import org.gl.exception.StudentUpdateDelete;

//Implementation of the service class All business logic are present here
@ApplicationScoped
public class StudentsServiceImpl implements StudentService{

    //Injecting repository bean in Service class

    @Inject
    StudentDao studentDao;

    //method to get the student data using id and using findByIdOptional present in repository interface
    @Override
    public Optional<Student> getStudentById(Long id) {
        return studentDao.getStudentById(id);
    }

    @Override
    public Student getStudentsById(Long id) throws StudentUpdateDelete {
        return studentDao.getStudentsById(id);
    }



    //method to update student data whose is present
    @Transactional
    @Override
    public Student updateStudent(Long id, Student student) throws StudentUpdateDelete {

        return studentDao.updateStudent(id,student);
    }

    //Method to insert new Student
    @Transactional
    @Override
    public Student saveStudent(Student student) {
        return studentDao.saveStudent(student);
    }
    @Transactional
    @Override
    public Marks saveMarks(Marks marks,Long id,Long subId) {
        return studentDao.saveMarks(marks,id,subId);
    }

    @Transactional
    @Override
    public boolean deleteStudent(Long id){
        return studentDao.deleteStudent(id);
    }

    @Override
    @Transactional
    public Student changeStudentData(Long id,Student student) throws StudentUpdateDelete {
     return studentDao.changeStudentData(id,student);
    }

    @Override
    public List<Student> getAll() {
        return studentDao.getAll();
    }

    @Override
    public List<Student> getAll(String field) {
        return studentDao.getAll(field);
    }

    @Override
    public List<Student> getAllStudentDescendingOrder(String field) {
        return studentDao.getAllStudentDescendingOrder(field);
    }
    

    @Override
    public List<Student> findStudentWithPagination(int offset, int pageSize) {
        return studentDao.findStudentWithPagination(offset,pageSize);
    }

    @Override
    public List<Student> findStudentsWithName(String names) {
        return studentDao.findStudentsWithName(names);
    }

  
    
}

