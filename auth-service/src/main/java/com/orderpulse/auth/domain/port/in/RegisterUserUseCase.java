//com/orderpulse/auth/domain/port/in/RegisterUserUseCase
package com.orderpulse.auth.domain.port.in;

import com.orderpulse.auth.domain.model.User;
import io.smallrye.mutiny.Uni;

import java.util.Set;

//Interfaz para registrar un nuevo usuario
public interface RegisterUserUseCase {
    Uni<User> execute(String username, String email, String password, Set<String> roles);
}
