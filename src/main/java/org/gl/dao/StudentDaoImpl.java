package org.gl.dao;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import org.gl.entity.Marks;
import org.gl.entity.Student;
import org.gl.entity.Subjects;
import org.gl.exception.StudentUpdateDelete;
import org.gl.repository.MarksRepository;
import org.gl.repository.SubjectRepository;
import org.gl.repository.UserRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class StudentDaoImpl implements StudentDao{

    private final UserRepository userRepository;

    private final MarksRepository marksRepository;

    private final SubjectRepository subjectRepository;

    @Inject
    public StudentDaoImpl(UserRepository userRepository, MarksRepository marksRepository, SubjectRepository subjectRepository) {
        this.userRepository = userRepository;
        this.marksRepository = marksRepository;
        this.subjectRepository = subjectRepository;
    }

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
    public Marks saveMarks(Marks marks, Long id, Long subId) {
        Student student = userRepository.findById(id);
        Subjects subjects = subjectRepository.findById(subId);
        marks.setStudent(student);
        marks.setSubjects(subjects);
        marksRepository.persist(marks);
        return marks;
    }

    @Transactional
    @Override
    public boolean deleteStudent(Long id){
        return userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Student changeStudentData(Long id,Student student) throws StudentUpdateDelete {
        Student entity =  getStudentsById(id);
        if(entity == null) {
            throw new WebApplicationException("Student with id " + id + " does not exist.", 404);
        }
        entity = student;
        return entity;
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


}