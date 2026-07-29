//com/orderpulse/auth/application/useCases/RegisterUserUseCaseImpl
package com.orderpulse.auth.application.useCases;

import com.orderpulse.auth.domain.model.User;
import com.orderpulse.auth.domain.port.in.RegisterUserUseCase;
import com.orderpulse.auth.domain.port.out.PasswordEncoderPort;
import com.orderpulse.auth.domain.port.out.UserRepositoryPort;
import io.smallrye.mutiny.Uni;

import java.util.HashSet;
import java.util.Set;

//Implementacion del caso de uso de registrar usuarios
public class RegisterUserUseCaseImpl implements RegisterUserUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordEncoderPort passwordEncoder;

    public RegisterUserUseCaseImpl(UserRepositoryPort userRepository, PasswordEncoderPort passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    //Ejecutar registro
    public Uni<User> execute(String username, String email, String password, Set<String> roles){
        return userRepository.findByEmail(email)
                .onItem().transformToUni(existingUser -> {
                    if(existingUser.isPresent()) return Uni.createFrom().failure(new IllegalArgumentException("El correo ya esta registrado"));

                    String encodedPassword = passwordEncoder.encode(password);
                    Set<String> assignedRoles = (roles != null && !roles.isEmpty()) ? roles : new HashSet<>(Set.of("user"));

                    User newUser = new User(null, username, email, encodedPassword, assignedRoles);
                    return userRepository.save(newUser);
                });
    }
}
