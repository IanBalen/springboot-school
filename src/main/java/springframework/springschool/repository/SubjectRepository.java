package springframework.springschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springframework.springschool.domain.Subject;

import java.util.List;
import java.util.Optional;

@Repository
public  interface SubjectRepository extends JpaRepository<Subject, Long>{

    List<Subject> findByClassroom(String classroom);

    Optional<Subject> findBySubjectType(String SubjectType);

    boolean existsBySubjectType(String subjectType);


}