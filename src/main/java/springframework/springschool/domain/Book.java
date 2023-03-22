package springframework.springschool.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameOfBook;
    private String author;
    private int numOfBook;
    private boolean borrowed;
    @ManyToMany(cascade =  {
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.PERSIST,
    })
    @JoinTable(
            name = "person_book",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id",
                    foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT)
            ))
    private List<Person> personList;

    public void incrementNumOfBook(int num){
        numOfBook += num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return this.nameOfBook.equals(book.getNameOfBook());
    }

    @Override
    public int hashCode() {
        return nameOfBook.hashCode();
    }
}
