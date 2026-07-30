//com/orderpulse/auth/domain/port/in/UpdateUserUseCase
package com.orderpulse.auth.domain.port.in;

import com.orderpulse.auth.domain.model.User;
import io.smallrye.mutiny.Uni;

//Interfaz para actualizar los datos del usuario
public interface UpdateUserUseCase {
    Uni<User> update(String userId, String newUsername, String newEmail);
}
