package com.milton.propertychatbotbackend.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RegisterDto {

    private @NonNull  String  username;

    private @NonNull String email;

    private @NonNull String password;

    private @NonNull String firstName;

    private @NonNull String lastName;

}
