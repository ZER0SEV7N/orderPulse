//com/orderpulse/auth/infrastructure/adapter/in/web/interceptor/GlobalResponseInterceptor
package com.orderpulse.auth.infrastructure.adapter.in.web.interceptor;

import com.orderpulse.auth.infrastructure.adapter.in.web.response.ApiResponse;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

//Interceptor de respuesta global
@Provider
public class GlobalResponseInterceptor implements ContainerResponseFilter {

    //Utilizar el metodo filter para sobreescribirlo
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        //Si es un error, ignorar
        if(responseContext.getStatus() >= 400) return;

        Object entity = responseContext.getEntity();

        //Verificar si el controlador ya evolvio la respuesta de ApiResponse
        if(entity instanceof ApiResponse) return;

        //Si en caso de devolver un objeto directo, envolverlo dinamicamente.
        int currentStatus = responseContext.getStatus();
        String message = currentStatus == 201 ? "Creado Exitosamente" : "Operacion Exitosa";

        ApiResponse<Object> response = new ApiResponse<>(
                currentStatus,
                entity != null ? entity : new Object(),
                message
        );

        responseContext.setEntity(response);
    }
}
