//com/orderpulse/auth/infrastructure/adapter/out/security/BcryptPasswordEncoderAdapter
package com.orderpulse.auth.infrastructure.adapter.out.security;

import com.orderpulse.auth.domain.port.out.PasswordEncoderPort;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;

//Adaptador del codificador de contraseñas
@ApplicationScoped
public class BcryptPasswordEncoderAdapter implements PasswordEncoderPort {

    //Metodo para hashear la contraseña
    public String encode(String rawPassword) {
        return BcryptUtil.bcryptHash(rawPassword);
    }

    //Metodo para matchear las contraseñas
    public boolean matches(String rawPassword, String encodedPassword) {
        return BcryptUtil.matches(rawPassword, encodedPassword);
    }
}