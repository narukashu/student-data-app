package org.gl.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.gl.entity.Subjects;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentGrpcDto {
    @JsonIgnore
    private Long id;
    private String names;
    private int age;
    private String address;
    private List<Subjects> subjects;
}