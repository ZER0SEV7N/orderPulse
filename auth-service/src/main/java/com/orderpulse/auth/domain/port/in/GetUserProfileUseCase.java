//com/orderpulse/auth/domain/port/in/GetUserProfileUseCase
package com.orderpulse.auth.domain.port.in;

import com.orderpulse.auth.domain.model.User;
import io.smallrye.mutiny.Uni;

//Interzaz del caso de uso para obtener tu perfil
public interface GetUserProfileUseCase {
    Uni<User> findMe(String userId);
}
