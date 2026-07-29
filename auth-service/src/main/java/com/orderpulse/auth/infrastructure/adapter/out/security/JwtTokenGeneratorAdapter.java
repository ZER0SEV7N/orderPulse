//com/orderpulse/auth/infrastructure/adapter/out/security/JwtTokenGeneratorAdapter
package com.orderpulse.auth.infrastructure.adapter.out.security;

import com.orderpulse.auth.domain.model.User;
import com.orderpulse.auth.domain.port.out.TokenGeneratorPort;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

//Adaptador del generador JWT
@ApplicationScoped
public class JwtTokenGeneratorAdapter implements TokenGeneratorPort {

    //Metodo para generar el token
    @Override
    public String generateToken(User user) {
        return Jwt.issuer("https://orderpulse.com/issuer")
                .upn(user.email())
                .groups(user.roles())
                .claim("userId", user.id())
                .claim("username", user.username())
                .expiresIn(3600) //1hora de validez
                .sign();
    }
}
