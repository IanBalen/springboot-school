package springframework.springschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springframework.springschool.domain.Subject;

import java.util.List;

public  interface SubjectRepository extends JpaRepository<Subject, Long>{

    List<Subject> findByClassroom(String classroom);

    Subject findBySubjectType(String SubjectType);

    boolean existsBySubjectType(String subjectType);


}