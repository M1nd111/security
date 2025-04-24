package plugin.acc.auth.entity;

import java.util.*;

import jakarta.persistence.*;
import lombok.*;

import static lombok.AccessLevel.*;

@Data
@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class User {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String login;

    @ManyToMany
    @JoinTable(name = "user_role",
        joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

}
