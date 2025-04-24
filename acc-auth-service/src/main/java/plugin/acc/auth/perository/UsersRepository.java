package plugin.acc.auth.perository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;
import org.springframework.stereotype.Repository;

import plugin.acc.auth.entity.*;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    User findByLogin(String login);

}
