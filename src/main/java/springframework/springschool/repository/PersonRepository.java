package springframework.springschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springframework.springschool.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {


}
