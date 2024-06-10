package com.example.FinalProject.controller;

import com.example.FinalProject.dto.request.AddUsersAccountRequest;
import com.example.FinalProject.dto.request.UpdateUsersAccountRequest;
import com.example.FinalProject.dto.response.AuctionResponse;
import com.example.FinalProject.dto.response.UsersAccountResponse;
import com.example.FinalProject.mapper.AuctionMapper;
import com.example.FinalProject.service.UsersAccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/final_project/v1/usersAccount/")
public class UsersAccountController {

    private final UsersAccountService usersAccountService;

    private final AuctionMapper auctionMapper;


    public UsersAccountController(UsersAccountService usersAccountService, AuctionMapper auctionMapper) {
        this.usersAccountService = usersAccountService;
        this.auctionMapper = auctionMapper;
    }

    @GetMapping
    public ResponseEntity<List<UsersAccountResponse>> getAllUsersAccounts() {
        List<UsersAccountResponse> responseBody = usersAccountService.getAllUsersAccounts();

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersAccountResponse> getUsersAccountById(@PathVariable Integer id) {
        UsersAccountResponse responseBody = usersAccountService.getUsersAccountsById(id);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUsersAccountById(@RequestParam Integer id) {
        usersAccountService.deleteUsersAccount(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping()
    public ResponseEntity<UsersAccountResponse> addNewUsersAccount(@RequestBody @Valid AddUsersAccountRequest addUsersAccountRequest) {
        UsersAccountResponse responseBody = usersAccountService.addNewUserAccount(addUsersAccountRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateUsersAccount(@PathVariable Integer id, @RequestBody @Valid UpdateUsersAccountRequest updateUsersAccountRequest) {
        usersAccountService.updateUsersAccount(id, updateUsersAccountRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/add-to-watchlist/{id}")
    public ResponseEntity<List<AuctionResponse>> addAuctionToWatchlist(@PathVariable Integer id) {


        usersAccountService.addAuctionToWatchlist(id);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/get-watchlist/")
    public ResponseEntity<List<AuctionResponse>> getAuctionsFromWatchlist() {

        List<AuctionResponse> auctionResponseList = usersAccountService.getWatchlist().stream().map(auction -> auctionMapper.fromAuction(auction)).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(auctionResponseList);
    }
}
