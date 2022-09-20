package org.gl.repository;


import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.gl.entity.Student;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

//Application Scoped will create instance only once for the duration of the whole application
@ApplicationScoped
public class UserRepository implements PanacheRepository<Student>{
    //PanacheRepository interface to implement predefined implemented methods

    public List<Student> findByNames(String names){
        return list("SELECT s FROM Student s WHERE s.names = ?1 ORDER BY id", names);
    }
}
