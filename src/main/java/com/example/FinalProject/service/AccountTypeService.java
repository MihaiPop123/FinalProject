package com.example.FinalProject.service;

import com.example.FinalProject.dto.request.TypeRequest;
import com.example.FinalProject.dto.request.UpdateTypeRequest;
import com.example.FinalProject.dto.response.TypeResponse;
import com.example.FinalProject.exceptions.AccountTypeNotFoundException;

import java.util.List;

public interface AccountTypeService {

    List<TypeResponse> getAllAccountTypes();

    TypeResponse getAccountTypeById(Integer id) throws AccountTypeNotFoundException;

    TypeResponse createAccountType(TypeRequest typeRequest);

    void updateAccountType(Integer id, UpdateTypeRequest updateTypeRequest);

    void deleteAccountType(Integer id);
}
