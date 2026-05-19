package com.SmartLearningPlatform.Platform.response;

import com.SmartLearningPlatform.Platform.datatypes.ROLE;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

//      private String jwt;

//    private RefreshToken refreshToken;

    private String jwt;

    private String message;

    private ROLE role;

}

