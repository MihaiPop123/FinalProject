package com.example.FinalProject.repository;

import com.example.FinalProject.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountTypeRepository extends JpaRepository<AccountType, Integer> {

    Optional<AccountType> findAccountTypeByName(String name);
}
