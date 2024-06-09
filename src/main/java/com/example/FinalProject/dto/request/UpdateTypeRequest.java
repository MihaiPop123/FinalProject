package com.example.FinalProject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTypeRequest {
    private String typeName;

    private List<String> userAccounts;

//    public void setUserAccounts(List<String> userAccounts) {
//        this.userAccounts = userAccounts;
//    }
}
