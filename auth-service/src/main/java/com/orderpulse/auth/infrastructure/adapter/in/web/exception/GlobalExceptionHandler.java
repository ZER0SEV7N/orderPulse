//com/orderpulse/auth/infrastructure/adapter/in/web/exception/GlobalExceptionHandler
package com.orderpulse.auth.infrastructure.adapter.in.web.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;
import java.util.Map;

//Clase para manejo global de exceptions
@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    //Metodo para estructurar la respuesta de los errores
    @Override
    public Response toResponse(Throwable ex) {
        int statusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
        String message = "Error interno en el servidor : " + ex;

        //Personalizar los exceptions que atrapemos
        if(ex instanceof SecurityException || ex.getMessage().contains("Credenciales")) {
            statusCode = Response.Status.UNAUTHORIZED.getStatusCode();
            message = ex.getMessage();
        } else if (ex instanceof IllegalArgumentException) {
            statusCode = Response.Status.BAD_REQUEST.getStatusCode();
            message = ex.getMessage();
        } else {
            ex.printStackTrace();
        }

        //Estructurar con el Estandar RFC
        Map<String, Object> errorResponse = Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status", statusCode,
                "error", message
        );

        return Response.status(statusCode)
                .entity(errorResponse)
                .build();
    }
}
