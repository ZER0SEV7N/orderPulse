//src/main/java/com/orderpulse/auth/domain/port/out/PasswordEncoder
package com.orderpulse.auth.domain.port.out;

//Interfaz del codificador de contraseñas (codifica y verifica)
public interface PasswordEncoderPort {
    String encode (String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
