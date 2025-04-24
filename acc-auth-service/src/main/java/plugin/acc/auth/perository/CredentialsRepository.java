package plugin.acc.auth.perository;

import org.springframework.data.jpa.repository.*;

import plugin.acc.auth.entity.*;

public interface CredentialsRepository extends JpaRepository<Credential, Long> {

    String findSaltByUserId(Long userId);

    @Query(value = """
        select
            c.id
        from credential c
        where c.user_id = :userId
        and c.hash = :hash
        """, nativeQuery = true)
    Long findId(Long userId, String hash);

}
