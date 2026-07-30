//com/orderpulse/auth/infrastructure/adapter/in/web/authResource
package com.orderpulse.auth.infrastructure.adapter.in.web;

import com.orderpulse.auth.domain.model.User;
import com.orderpulse.auth.domain.port.in.GetUserProfileUseCase;
import com.orderpulse.auth.domain.port.in.LoginUserUseCase;
import com.orderpulse.auth.domain.port.in.RegisterUserUseCase;
import com.orderpulse.auth.domain.port.in.UpdateUserUseCase;
import com.orderpulse.auth.infrastructure.adapter.in.web.dto.LoginRequestDto;
import com.orderpulse.auth.infrastructure.adapter.in.web.dto.RegisterRequestDto;
import com.orderpulse.auth.infrastructure.adapter.in.web.dto.UpdateUserRequestDto;
import com.orderpulse.auth.infrastructure.adapter.in.web.response.ApiResponse;
import io.quarkus.security.Authenticated;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Map;

//Controlador REST de AUTH
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private final RegisterUserUseCase registerUseCase;
    private final LoginUserUseCase loginUseCase;
    private final GetUserProfileUseCase getProfileUseCase;
    private final UpdateUserUseCase updateUseCase;

    //Inyectar el JWT
    @Inject
    JsonWebToken jwt;

    public AuthResource(RegisterUserUseCase registerUseCase,
                        LoginUserUseCase loginUseCase,
                        GetUserProfileUseCase getProfileUseCase,
                        UpdateUserUseCase updateUseCase) {
        this.registerUseCase = registerUseCase;
        this.loginUseCase = loginUseCase;
        this.getProfileUseCase = getProfileUseCase;
        this.updateUseCase = updateUseCase;
    }
    /*
    * Registrar un nuevo empleado
    * POST: /Register
    * @REQUEST: {
    *             username: String
    *             email: String
    *             password: String
    *             roles: String
    *           }
    * @Return: Status 201
    */
    @POST
    @Path("/register")
    public Uni<Response> register(RegisterRequestDto request){
        return registerUseCase.execute(request.username, request.email, request.password, request.roles)
                .onItem().transform(user -> {
                    ApiResponse<User> response = new ApiResponse<>(201, user, "Usuario Registrado Exitosamente");
                    return Response.status(Response.Status.CREATED).entity(response).build();
                });
    }

    /*
    * Loguearse en el sistema
    * POST: /login
    * @Request: {
    *           email: String
    *           password: String
    *           }
    * @Returns: Status 200 y el token
    */
    @POST
    @Path("/login")
    public Uni<Response> login(LoginRequestDto request) {
        return loginUseCase.execute(request.email, request.password)
                .onItem().transform(token -> {
                    ApiResponse<Map<String, String>> res = ApiResponse.success(Map.of("token", token), "Login Exitoso");
                    return Response.ok(res).build();
                });
    }

    /*
    * Obtener tu perfil de usuario
    * GET: /profile/me
    * @Request: estar logueado y tener JWT
    * @Returns: 200 y tu perfil de usuario
     */
    @GET
    @Path("/profile/me")
    @Authenticated
    public Uni<Response> getMyProfile() {
        //Extraer el userId del JWT
        String userId = jwt.claim("userId").orElseThrow(() -> new SecurityException("Token Invalido")).toString();
        return getProfileUseCase.findMe(userId)
                .onItem().transform(user -> {
                    ApiResponse<User> res = ApiResponse.success(user, "Perfil Obtenido Correctamente");
                    return Response.ok(res).build();
                });
    }

    /*
    * Editar tu perfil se usuario
    * PUT: /profile/me
    * @Request: Requieres el TOKEN
    * y puedes editar {
    *           newUsername: String
    *           newEmail: String
    *          }
    * @Return: 200
    */
    @PUT
    @Path("/profile/me")
    @Authenticated
    public Uni<Response> updateMyProfile(UpdateUserRequestDto request) {
        String userId = jwt.claim("userId").orElseThrow(() -> new SecurityException("Token Invalido")).toString();

        return updateUseCase.update(userId, request.username, request.email)
                .onItem().transform(user -> {
                    ApiResponse<User> res = ApiResponse.success(user, "Perfil Actualizado Correctamente");
                    return Response.ok(res).build();
                });
    }
}
