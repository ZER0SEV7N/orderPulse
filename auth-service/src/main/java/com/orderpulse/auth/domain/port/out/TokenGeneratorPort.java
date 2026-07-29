//com/orderpulse/auth/domain/port/out/TokenGeneratorPort
package com.orderpulse.auth.domain.port.out;
import com.orderpulse.auth.domain.model.User;

//Interfaz para generar el token
public interface TokenGeneratorPort {
    String generateToken(User user);
}
