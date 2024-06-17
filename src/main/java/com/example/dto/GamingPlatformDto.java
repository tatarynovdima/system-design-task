package com.example.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class GamingPlatformDto {
    private Long id;
    @NonNull private String name;
    private String password;
}