//com/orderpulse/auth/application/useCases/UpdateUserUseCaseImpl
package com.orderpulse.auth.application.useCases;

import com.orderpulse.auth.domain.model.User;
import com.orderpulse.auth.domain.port.in.UpdateUserUseCase;
import com.orderpulse.auth.domain.port.out.UserRepositoryPort;
import io.smallrye.mutiny.Uni;

//Implementacion del caso de uso para actualizar los datos del propio usuario
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserRepositoryPort userRepository;

    public UpdateUserUseCaseImpl(UserRepositoryPort userRepository) {
        this.userRepository = userRepository;
    }

    //Metodo para actualizar el usuario
    public Uni<User> update (String userId, String newUsername, String newEmail){
        return userRepository.findUserById(userId)
                .onItem().transformToUni(optUser -> {
                    if(optUser.isEmpty()) return Uni.createFrom().failure(new IllegalArgumentException("Usuario no encontrado"));

                    User existingUser = optUser.get();

                    //Crear un nuevo record con los datos actualizados
                    User updatedUser = new User(
                            existingUser.id(),
                            newUsername != null ? newUsername : existingUser.username(),
                            newEmail != null ? newEmail : existingUser.email(),
                            existingUser.password(),
                            existingUser.roles()
                    );

                    return userRepository.update(updatedUser);
                });
    }
}
