package plugin.acc.auth.perository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.*;

import plugin.acc.auth.entity.*;

public interface RolesRepository extends JpaRepository<Role, Long>, PagingAndSortingRepository<Role, Long> {

}
