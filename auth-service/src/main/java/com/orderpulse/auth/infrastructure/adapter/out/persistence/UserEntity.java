//com/orderpulse/auth/infrastructure/adapter/out/persistence/UserEntity
package com.orderpulse.auth.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;
import java.util.Set;

//Entidad persistente de usuario
@Entity
@Table(name = "users")
public class UserEntity {

    //ID
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    //username
    @Column(nullable = false)
    public String username;

    //email
    @Column(nullable = false, unique = true)
    public String email;

    //password
    @Column(nullable = false)
    public String password;

    //Roles
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    public Set<String> roles;
}
