package springframework.springschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springframework.springschool.domain.Professor;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

    List<Professor> findBySubject(String subject);

    List<Professor> findByNameStartingWith(String name);

}