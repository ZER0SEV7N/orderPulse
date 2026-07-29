//src/main/java/com.orderpulse.auth.domain.model
//Class model
package com.orderpulse.auth.domain.model;

import java.util.Set; //Set para almacenar elementos unicos


public record User (
    String id,
    String username,
    String email,
    String password,
    Set<String> roles
) {

}
