package com.example.FinalProject.controller;

import com.example.FinalProject.dto.request.TypeRequest;
import com.example.FinalProject.dto.request.UpdateTypeRequest;
import com.example.FinalProject.dto.response.TypeResponse;
import com.example.FinalProject.service.AccountTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/final_project/v1/account_type/")

public class AccountTypeController {
    private final AccountTypeService accountTypeService;

    public AccountTypeController(AccountTypeService accountTypeService) {
        this.accountTypeService = accountTypeService;
    }

    @GetMapping()
    public ResponseEntity<List<TypeResponse>> getAllAccountTypes() {
        List<TypeResponse> responseBody = (List<TypeResponse>) accountTypeService.getAllAccountTypes();

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeResponse> getAccountTypeById(@PathVariable Integer id) {
        TypeResponse responseBody = accountTypeService.getAccountTypeById(id);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAccountTypeById(@RequestParam Integer id) {
        accountTypeService.deleteAccountType(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping()
    public ResponseEntity<TypeResponse> addAccountType(@RequestBody @Valid TypeRequest typeRequest){

        TypeResponse responseBody = accountTypeService.createAccountType(typeRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TypeResponse> updateAccountType(@PathVariable Integer id, @RequestBody @Valid UpdateTypeRequest updateTypeRequest){
        accountTypeService.updateAccountType(id, updateTypeRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
