package tr.com.huseyinaydin.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
/*import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;*/
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByActivationToken(String token);
    Page<User> findByIdNot(long id, Pageable page);

    /*@Query(value="Select u from User u")
    Page<UserProjection> getAllUserRecords(Pageable page);*/
}