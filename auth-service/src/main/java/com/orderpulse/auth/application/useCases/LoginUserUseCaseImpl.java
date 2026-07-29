//com/orderpulse/auth/application/useCases/LoginUserUseCaseImpl
package com.orderpulse.auth.application.useCases;

import com.orderpulse.auth.domain.port.in.LoginUserUseCase;
import com.orderpulse.auth.domain.port.out.PasswordEncoderPort;
import com.orderpulse.auth.domain.port.out.TokenGeneratorPort;
import com.orderpulse.auth.domain.port.out.UserRepositoryPort;
import io.smallrye.mutiny.Uni;

//Implementacion del caso de uso del login
public class LoginUserUseCaseImpl implements LoginUserUseCase {

    //Inyeccion de dependencias
    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenGeneratorPort tokenGenerator;

    public LoginUserUseCaseImpl( UserRepositoryPort userRepository, PasswordEncoderPort passwordEncoder, TokenGeneratorPort tokenGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenGenerator = tokenGenerator;
    }

    //Ejecutar el caso de uso del login
    public Uni<String> execute(String email, String password) {
        return userRepository.findByEmail(email)
                .onItem().transformToUni(optUser -> {
                    if (optUser.isEmpty()) return Uni.createFrom().failure(new SecurityException("Crendeciales Invalidas"));

                    var user = optUser.get();
                    if(!passwordEncoder.matches(password, user.password())) return Uni.createFrom().failure(new SecurityException("Password Invalidas"));

                    String token = tokenGenerator.generateToken(user);
                    return Uni.createFrom().item(token);
                });
    }
}
