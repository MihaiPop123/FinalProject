package com.example.FinalProject.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUsersAccountRequest {

    private String accountName;
    private String province;
    private String country;
    private String address;
    private String accountType;
    @NotEmpty(message = "Please enter an email")
    private String email;

    @NotEmpty(message = "Please enter a password")
    private String password;
}
