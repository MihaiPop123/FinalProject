package com.example.FinalProject.service;

import com.example.FinalProject.dto.request.TypeRequest;
import com.example.FinalProject.dto.request.UpdateTypeRequest;
import com.example.FinalProject.dto.response.TypeResponse;
import com.example.FinalProject.entity.AccountType;
import com.example.FinalProject.exceptions.AccountTypeNotFoundException;
import com.example.FinalProject.exceptions.AuctionNotFoundException;
import com.example.FinalProject.mapper.TypeMapper;
import com.example.FinalProject.repository.AccountTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountTypeServiceImpl implements AccountTypeService{

    public final AccountTypeRepository accountTypeRepository;

    public final TypeMapper typeMapper;

    public AccountTypeServiceImpl(AccountTypeRepository accountTypeRepository, TypeMapper typeMapper) {
        this.accountTypeRepository = accountTypeRepository;
        this.typeMapper = typeMapper;
    }

    @Override
    public List<TypeResponse> getAllAccountTypes() {

        List<AccountType> accountTypes = accountTypeRepository.findAll();

        List<TypeResponse> accountTypeResponseList = accountTypes.stream().map(element->typeMapper.fromType(element)).collect(Collectors.toList());

        return accountTypeResponseList;

    }

    @Override
    public TypeResponse getAccountTypeById(Integer id) {
        Optional<AccountType> optionalAccountType = accountTypeRepository.findById(id);
        if(optionalAccountType.isPresent()){
            AccountType accountType = optionalAccountType.get();
            TypeResponse accountTypeResponse = typeMapper.fromType(accountType);
            return accountTypeResponse;
        }else{
            throw new AccountTypeNotFoundException("Account type with id " + id + " not found");
        }
    }

    @Transactional
    @Override
    public TypeResponse createAccountType(TypeRequest typeRequest) {
        AccountType accountType = typeMapper.fromTypeRequest(typeRequest);

        accountTypeRepository.save(accountType);

        TypeResponse typeResponse = typeMapper.fromType(accountType);

        return typeResponse;
    }

    @Transactional
    @Override
    public void updateAccountType(Integer id, UpdateTypeRequest updateTypeRequest) {
        Optional<AccountType> optionalAccountType = accountTypeRepository.findById(id);

        if(optionalAccountType.isPresent()) {
            AccountType accountType = typeMapper.fromUpdateType(optionalAccountType.get(), updateTypeRequest);
            accountTypeRepository.save(accountType);
        } else{
            throw new AuctionNotFoundException("Auction with id " + id + " not found");
        }
    }



    @Transactional
    @Override
    public void deleteAccountType(Integer id) {
        Optional<AccountType> optionalAccountType = accountTypeRepository.findById(id);

        if(optionalAccountType.isPresent()) {
            accountTypeRepository.deleteById(id);
        } else{
            throw new AccountTypeNotFoundException("Account type with id " + id + " not found");
        }
    }
}

