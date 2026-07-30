//com/orderpulse/auth/infrastructure/adapter/in/web/response/ApiResponse
package com.orderpulse.auth.infrastructure.adapter.in.web.response;

import java.time.LocalDateTime;

//Clase response para estructurar el json de respuesta.
public class ApiResponse<T> {
    public int status;
    public String message;
    public T data;
    public String timestamp;

    //Constructor
    public ApiResponse() {}

    public ApiResponse(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.timestamp = LocalDateTime.now().toString();
    }

    //Metodo estatico para respuestas exitosas rapidas (200)
    public static <T> ApiResponse<T> success(T data, String message){
        return new ApiResponse<>(200, data, message);
    }
}
