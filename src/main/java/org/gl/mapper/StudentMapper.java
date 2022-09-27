package org.gl.mapper;

import org.gl.entity.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface StudentMapper {
    Student toStudent(Student student);
}
