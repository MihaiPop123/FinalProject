package com.example.FinalProject;

import com.example.FinalProject.dto.request.AddUsersAccountRequest;
import com.example.FinalProject.dto.response.UsersAccountResponse;
import com.example.FinalProject.entity.UsersAccount;
import com.example.FinalProject.mapper.UsersAccountMapper;
import com.example.FinalProject.repository.UsersAccountRepository;
import com.example.FinalProject.service.UsersAccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserAccountTest {
    @Mock
    private UsersAccountRepository usersAccountRepository;//obiect fake

    @Mock
    private UsersAccountMapper usersAccountMapper; //obiect fake

    @InjectMocks//injecteaza orice dependinta fake(mockuita)in obiectul real
    private UsersAccountServiceImpl usersAccountService;


    @Test
    public void testCreatedUserAccount(){

        AddUsersAccountRequest addNewUsersAccountRequest=new AddUsersAccountRequest();

        addNewUsersAccountRequest.setAccountName("user1");
        addNewUsersAccountRequest.setEmail("user1@yahoo.com");
        addNewUsersAccountRequest.setPassword("1234");
        addNewUsersAccountRequest.setAccountType("ADMIN");
        addNewUsersAccountRequest.setProvince("Bucuresti");
        addNewUsersAccountRequest.setCountry("romania");
        addNewUsersAccountRequest.setAddress("Str.Scolii nr 16");

        UsersAccount usersAccount=new UsersAccount();
        UsersAccountResponse usersAccountResponse= new UsersAccountResponse();
        usersAccountResponse.setAccountName("user1");
        usersAccountResponse.setEmail("user1@yahoo.com");
        usersAccountResponse.setProvince("Bucuresti");
        usersAccountResponse.setCountry("romania");
        usersAccountResponse.setAddress("Str.Scolii nr 16");


        when(usersAccountMapper.fromUsersAccountRequest(any())).thenReturn(usersAccount);
        when(usersAccountRepository.save(any())).thenReturn(usersAccount);
        when(usersAccountMapper.fromUsersAccount(any())).thenReturn(usersAccountResponse);

        var response=this.usersAccountService.addNewUserAccount(addNewUsersAccountRequest);


        Assertions.assertNotNull(response);
        Assertions.assertEquals(usersAccountResponse.getAccountName(),response.getAccountName());
        Assertions.assertEquals(usersAccountResponse.getEmail(),response.getEmail());
    }

}
