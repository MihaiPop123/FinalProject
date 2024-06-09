package com.example.FinalProject.security;

import com.example.FinalProject.entity.UsersAccount;
import com.example.FinalProject.repository.UsersAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUsersAccountDetailsService implements UserDetailsService {

    private final UsersAccountRepository usersAccountRepository;

    public CustomUsersAccountDetailsService(UsersAccountRepository usersAccountRepository) {
        this.usersAccountRepository = usersAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
        Optional<UsersAccount> usersAccountOptional = usersAccountRepository.findByAccountName(accountName);
        if(usersAccountOptional.isPresent())
        {
            UsersAccount usersAccount = usersAccountOptional.get();
            return new UsersAccountDetailsImpl(usersAccount);
        }
        else
        {
            throw new UsernameNotFoundException("Invalid username");
        }
    }
}
