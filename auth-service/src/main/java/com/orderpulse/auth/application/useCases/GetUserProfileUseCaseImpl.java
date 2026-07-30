//com/orderpulse/auth/application/useCases/GetUserProfileUseCaseImpl
package com.orderpulse.auth.application.useCases;

import com.orderpulse.auth.domain.model.User;
import com.orderpulse.auth.domain.port.in.GetUserProfileUseCase;
import com.orderpulse.auth.domain.port.out.UserRepositoryPort;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

//Implementacion del caso de uso para obtener tu perfil de usuario
@ApplicationScoped
public class GetUserProfileUseCaseImpl implements GetUserProfileUseCase {

    private final UserRepositoryPort userRepository;

    public GetUserProfileUseCaseImpl(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    //Metodo para obtener tu perfil de usuario
    public Uni<User> findMe(String userId){
        return userRepository.findUserById(userId)
                .onItem().transformToUni(optUser ->
                        optUser.map(user -> Uni.createFrom().item(user))
                                .orElseGet(() -> Uni.createFrom().failure(new IllegalArgumentException("Usuario no encontrado")))
                );
    }
}
