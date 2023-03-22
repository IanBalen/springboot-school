package springframework.springschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springframework.springschool.domain.Student;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByFirstNameStartingWith(String firstName); //Marko    -> MAR
    List<Student> findByAgeAfterAndAgeBefore(int lower, int higher);
    List<Student> findByLastNameStartingWith(String lastName);

    List<Student> findByAcademicYear(int year);
}