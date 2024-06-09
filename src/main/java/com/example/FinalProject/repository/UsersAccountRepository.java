package com.example.FinalProject.repository;

import com.example.FinalProject.entity.UsersAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersAccountRepository extends JpaRepository<UsersAccount, Integer> {

    Optional<UsersAccount> findByAccountName(String username);
    Optional<UsersAccount> findByAccountEmail(String email);
}
