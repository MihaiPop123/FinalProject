package com.example.FinalProject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponse {

    private String accountName;
    private String email;
    private List<String> type;
}
