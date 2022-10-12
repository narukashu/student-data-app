package org.gl.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Subjects{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long subId;

    @NotBlank
    @Size(min = 3,max = 20)
    @Schema(example = "Maths")
    private String subjectName;

    @OneToOne(targetEntity = Marks.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "marks_fk",referencedColumnName = "marksId")
    private Marks marks;
    
}
