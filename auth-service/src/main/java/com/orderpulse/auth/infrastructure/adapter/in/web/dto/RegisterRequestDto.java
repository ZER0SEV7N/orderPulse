//com/orderpulse/auth/infrastructure/adapter/in/web/dto/RegisterRequestDto
package com.orderpulse.auth.infrastructure.adapter.in.web.dto;

import java.util.Set;

public class RegisterRequestDto {
    public String username;
    public String email;
    public String password;
    public Set<String> roles;
}
