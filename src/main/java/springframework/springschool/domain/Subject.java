package springframework.springschool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String classroom;
    private String subjectType;

    @OneToMany(cascade =  {
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.PERSIST,
    })
    @JoinTable(
            name = "subject_professor",
            inverseJoinColumns = @JoinColumn(name = "subject_id"),
            joinColumns = @JoinColumn(name = "professor_id"),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
    )
    private List<Professor> professorList;

    @ManyToMany(cascade =  {
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(
            name = "subject_student",
            inverseJoinColumns =  @JoinColumn(name = "subject_id"),
            joinColumns = @JoinColumn(name = "student_id",
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
    ))
    private List<Student> studentList;


}
