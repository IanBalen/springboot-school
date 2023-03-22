package springframework.springschool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;
    private int age;

    @JsonIgnore
    @ManyToMany(cascade =  {
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.PERSIST,
    })
    @JoinTable(
            name = "person_book",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id",
                    foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
            ))
    private List<Book> bookList;
}
