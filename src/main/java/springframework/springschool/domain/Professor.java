package springframework.springschool.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "person_id")
public class Professor extends Person{

    @ManyToOne(cascade =  {
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.PERSIST,
    })
    @JoinTable(name = "subject_professor",
               joinColumns = @JoinColumn(name = "subject_id"),
               inverseJoinColumns = @JoinColumn(name = "professor_id"),
               foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Subject subject;

}
