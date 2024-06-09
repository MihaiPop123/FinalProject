package com.example.FinalProject.security;

import com.example.FinalProject.dto.request.AddUsersAccountRequest;
import com.example.FinalProject.dto.request.SignInRequest;
import com.example.FinalProject.dto.response.SignInResponse;
import com.example.FinalProject.entity.AccountType;
import com.example.FinalProject.entity.UsersAccount;
import com.example.FinalProject.exceptions.AuthenticationException;
import com.example.FinalProject.exceptions.TypeNotFoundException;
import com.example.FinalProject.exceptions.UserAlreadyTakenException;
import com.example.FinalProject.mapper.UsersAccountMapper;
import com.example.FinalProject.repository.AccountTypeRepository;
import com.example.FinalProject.repository.UsersAccountRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UsersAccountRepository usersAccountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AuthenticationManager authenticationManager;

    private final UsersAccountMapper usersAccountMapper;

    public AuthService(UsersAccountRepository userRepository, AccountTypeRepository roleRepository, AuthenticationManager authenticationManager, UsersAccountMapper usersAccountMapper) {
        this.usersAccountRepository = userRepository;
        this.accountTypeRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.usersAccountMapper = usersAccountMapper;
    }


    public UsersAccount getUserByEmail(String email)
    {
        Optional<UsersAccount> optionalUsersAccount = usersAccountRepository.findByAccountEmail(email);

        if(optionalUsersAccount.isPresent())
        {
            throw new UserAlreadyTakenException("Email is already in use");
        }
        return null;
    }

    public void registerUser(AddUsersAccountRequest addUsersAccountRequest) {

        UsersAccount usersAccount = usersAccountMapper.fromUsersAccountRequest(addUsersAccountRequest);
        Optional<AccountType> optionalAccountType = accountTypeRepository.findAccountTypeByName(addUsersAccountRequest.getAccountType());
        if(optionalAccountType.isPresent())
        {
            usersAccount.addType(optionalAccountType.get());
        }
        else
        {
            throw new TypeNotFoundException("Type with name "+addUsersAccountRequest.getAccountName()+" is not in the db");
        }
        usersAccountRepository.save(usersAccount);
    }

    public SignInResponse signIn(SignInRequest signInRequest)
    {
        String username = signInRequest.getAccountName();
        String password = signInRequest.getPassword();


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UsersAccountDetailsImpl userDetails = (UsersAccountDetailsImpl) authentication.getPrincipal();

        return usersAccountMapper. fromUserDetailsImpl(userDetails);
    }

    public UsersAccountDetailsImpl getLoggedInUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null){
            return (UsersAccountDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }

        throw new AuthenticationException("User not logged in");

    }

}
