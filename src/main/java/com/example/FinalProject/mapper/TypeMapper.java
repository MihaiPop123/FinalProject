package com.example.FinalProject.mapper;

import com.example.FinalProject.dto.request.TypeRequest;
import com.example.FinalProject.dto.request.UpdateTypeRequest;
import com.example.FinalProject.dto.response.TypeResponse;
import com.example.FinalProject.entity.AccountType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TypeMapper {
    public static AccountType fromTypeRequest(TypeRequest typeRequest)
    {
        AccountType accountType = new AccountType();
        accountType.setName(typeRequest.getTypeName());
        accountType.setUsersAccounts(List.of());

        return accountType;
    }

    public static TypeResponse fromType(AccountType accountType)
    {
        TypeResponse typeResponse = new TypeResponse();
        typeResponse.setTypeName(accountType.getName());
        typeResponse.setUserAccounts(accountType.getUsersAccounts().stream().map(r->r.getAccountName()).toList());

        return typeResponse;
    }

    public static AccountType fromUpdateType(AccountType accountType, UpdateTypeRequest updateTypeRequest)
    {
        accountType.setName(updateTypeRequest.getTypeName());
//        accountType.setUsersAccounts(updateTypeRequest.getUserAccounts());

        return accountType;
    }
}
