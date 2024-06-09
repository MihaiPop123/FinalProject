package com.example.FinalProject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersAccountResponse {
    private String accountName;
    private String province;
    private String country;
    private String address;
    private String email;
}
