//com/orderpulse/auth/domain/port/in/LoginUserUseCase
package com.orderpulse.auth.domain.port.in;

import io.smallrye.mutiny.Uni;

//Interface para el login
public interface LoginUserUseCase {
    Uni<String> execute(String email, String password);
}
