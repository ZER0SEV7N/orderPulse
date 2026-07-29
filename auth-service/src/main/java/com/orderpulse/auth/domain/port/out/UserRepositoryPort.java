//com/orderpulse/auth/domain/port/out/UserRepositoryPort
package com.orderpulse.auth.domain.port.out;

import com.orderpulse.auth.domain.model.User;
import io.smallrye.mutiny.Uni;

import java.util.Optional;

//Interzaz de los metodos de usuario (save, findByEmail, findById)
public interface UserRepositoryPort {
    Uni<Optional<User>> findByEmail(String email);
    Uni<User> save(User user);
    Uni<Optional<User>> findUserById(String id);
}
