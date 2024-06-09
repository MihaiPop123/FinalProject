package com.example.FinalProject.service;


import com.example.FinalProject.dto.request.AddUsersAccountRequest;
import com.example.FinalProject.dto.request.UpdateUsersAccountRequest;
import com.example.FinalProject.dto.response.UsersAccountResponse;
import com.example.FinalProject.entity.Auction;

import java.util.List;

public interface UsersAccountService {

    List<UsersAccountResponse> getAllUsersAccounts();

    UsersAccountResponse getUsersAccountsById(Integer id);

    UsersAccountResponse  addNewUserAccount(AddUsersAccountRequest addUserAccountRequest);

    void updateUsersAccount(Integer id, UpdateUsersAccountRequest updateUsersAccountRequest);

    void deleteUsersAccount(Integer id);

    void addAuctionToWatchlist(Integer id);

    List<Auction> getWatchlist();
}
