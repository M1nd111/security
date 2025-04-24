package plugin.acc.auth.perository;

import java.util.*;

import org.springframework.data.jpa.repository.*;

import plugin.acc.auth.entity.*;

public interface RefreshTokensRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByRefreshJti(UUID jti);

    void deleteByRefreshJti(UUID jti);

    void deleteByUserId(Long userId);

}
