package springframework.springschool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private int age;
    private int academicYear;

    @ManyToMany(cascade =  {
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.PERSIST,
    })
    @JoinTable(
            name = "subject_student",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id",
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
    ))
    private List<Subject> subjectList;

}