//com/orderpulse/auth/infrastructure/adaptar/out/persistence/UserRepositoryAdapter
package com.orderpulse.auth.infrastructure.adapter.out.persistence;

import com.orderpulse.auth.domain.model.User;
import com.orderpulse.auth.domain.port.out.UserRepositoryPort;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;

//Clase adapter para el repositorio de usuario
@ApplicationScoped
public class UserRepositoryAdapter implements UserRepositoryPort, PanacheRepositoryBase<UserEntity, String> {

    //Metodo para buscar por Email
    public Uni<Optional<User>> findByEmail(String email) {
        return find("email", email).firstResult()
                .onItem().transform(entity -> Optional.ofNullable(entity).map(this::toDomain));
    }

    //Metodo para guardar los cambios
    public Uni<User> save(User user) {
        UserEntity entity = toEntity(user);
        return Panache.withTransaction(() -> persist(entity))
                .replaceWith(() -> toDomain(entity));
    }

    //Metodo para encontrar el findUserById
    public Uni<Optional<User>> findUserById(String id) {
        return findById(id)
                .onItem().transform(entity -> Optional.ofNullable(entity).map(this::toDomain));
    }
    private User toDomain(UserEntity entity) {
        return new User(entity.id, entity.username, entity.email, entity.password, entity.roles);
    }

    private UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.username = user.username();
        entity.email = user.email();
        entity.password = user.password();
        entity.roles = user.roles();
        return entity;
    }
}
