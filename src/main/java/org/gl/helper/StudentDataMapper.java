package org.gl.helper;

import org.gl.dto.StudentDto;
import org.gl.entity.Marks;
import org.gl.entity.Student;
import org.gl.entity.Subjects;
import org.gl.service.StudentService;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentDataMapper{
/*
    @Inject
    StudentService studentService;
    public static StudentDto entityTODto(Student student){
        return StudentDto.builder().id(student.getId())
                .names(student.getNames())
                .age(student.getAge()).address(student.getAddress())
                .subjects(student.getSubjects())
                .build();
    }*/

   /* @Override
    public Marks map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new Marks(rs.getInt("id"),rs.getInt("mark"));
    }*/
}

/*
class StudentMapper implements RowMapper<StudentDto>{

    @Override
    public StudentDto map(ResultSet rs, StatementContext ctx) throws SQLException {
        return new StudentDto(rs.getLong("id"),rs.getString("names"),rs.getInt("age"),
        rs.getString("address"),rs.getObject("subjects",)
        );
    }
}*/
