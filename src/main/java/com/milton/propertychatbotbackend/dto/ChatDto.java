package com.milton.propertychatbotbackend.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private @NonNull String message;
}
