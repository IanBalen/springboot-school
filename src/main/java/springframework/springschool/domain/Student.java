package springframework.springschool.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "person_id")
public class Student extends Person{

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