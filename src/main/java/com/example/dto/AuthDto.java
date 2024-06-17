package com.example.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class AuthDto {
    @NonNull private String login;
    @NonNull private String password;
}
