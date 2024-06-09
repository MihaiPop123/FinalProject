package com.example.FinalProject.service;


import com.example.FinalProject.dto.request.AddUsersAccountRequest;
import com.example.FinalProject.dto.request.UpdateUsersAccountRequest;
import com.example.FinalProject.dto.response.UsersAccountResponse;
import com.example.FinalProject.entity.Auction;
import com.example.FinalProject.entity.Bidding;
import com.example.FinalProject.entity.Purchase;
import com.example.FinalProject.entity.UsersAccount;
import com.example.FinalProject.exceptions.*;
import com.example.FinalProject.mapper.UsersAccountMapper;
import com.example.FinalProject.repository.AuctionRepository;
import com.example.FinalProject.repository.UsersAccountRepository;
import com.example.FinalProject.security.AuthService;
import com.example.FinalProject.security.UsersAccountDetailsImpl;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersAccountServiceImpl implements UsersAccountService {

    private final UsersAccountRepository usersAccountRepository;
    private final UsersAccountMapper usersAccountMapper;

    private final AuctionRepository auctionRepository;

    private final AuthService authService;


    public UsersAccountServiceImpl(UsersAccountRepository usersAccountRepository, UsersAccountMapper usersAccountMapper, AuctionRepository auctionRepository, AuthService authService) {
        this.usersAccountRepository = usersAccountRepository;
        this.usersAccountMapper = usersAccountMapper;
        this.auctionRepository = auctionRepository;
        this.authService = authService;
    }

    @Override
    public List<UsersAccountResponse> getAllUsersAccounts() {
        List<UsersAccount> users = usersAccountRepository.findAll();
        List<UsersAccountResponse> usersAccountList = users.stream().
                map(element -> usersAccountMapper.fromUsersAccount(element)).collect(Collectors.toList());
        return usersAccountList;
    }

    @Override
    public UsersAccountResponse getUsersAccountsById(Integer id) {
        Optional<UsersAccount> usersAccountOptional = usersAccountRepository.findById(id);
        if (usersAccountOptional.isPresent()) {
            UsersAccount usersAccount = usersAccountOptional.get();
            UsersAccountResponse usersAccountResponse = usersAccountMapper.fromUsersAccount(usersAccount);
            return usersAccountResponse;
        } else {
            throw new UserAccountNotFoundException("User account with id " + id + " not found");
        }
    }

    @Override
    public UsersAccountResponse addNewUserAccount(AddUsersAccountRequest addUserAccountRequest) {
        UsersAccount usersAccount = usersAccountMapper.fromUsersAccountRequest(addUserAccountRequest);
        usersAccountRepository.save(usersAccount);
        UsersAccountResponse usersAccountResponse = usersAccountMapper.fromUsersAccount(usersAccount);
        return usersAccountResponse;
    }

    @Override
    public void updateUsersAccount(Integer id, UpdateUsersAccountRequest updateUsersAccountRequest) {

        Optional<UsersAccount> usersAccountOptional = usersAccountRepository.findById(id);
        if (usersAccountOptional.isPresent()) {

            UsersAccount usersAccount = usersAccountOptional.get();
            UsersAccount usersAccountToBeUpdated = usersAccountMapper.fromUsersAccountUpdateRequest(usersAccount, updateUsersAccountRequest);
            usersAccountRepository.save(usersAccountToBeUpdated);
        } else {
            throw new UserAccountNotFoundException("User account with id " + id + " not found");
        }
    }

    @Override
    public void deleteUsersAccount(Integer id) {

        usersAccountRepository.deleteById(id);
    }

    @Override
    public List<Auction> getWatchlist() {

        UsersAccountDetailsImpl usersAccountDetails = authService.getLoggedInUser();

        return usersAccountDetails.getUsersAccount().getWatchedAuctions();

    }

    @Transactional
    @Override
    public void addAuctionToWatchlist(Integer id) {
        Optional<Auction> optionalAuction = auctionRepository.findById(id);
        UsersAccountDetailsImpl loggedInUser = authService.getLoggedInUser();
        if (optionalAuction.isPresent()) {
            UsersAccount usersAccount = usersAccountRepository.findById(loggedInUser.getUsersAccount().getId())
                    .orElseThrow(() -> new UserAccountNotFoundException("User not found"));
            usersAccount.addAuctionToWatchList(optionalAuction.get());
        }
    }

    public List<Bidding> getBiddingsOfUser() {
        UsersAccountDetailsImpl loggedInUser = authService.getLoggedInUser();
        return loggedInUser.getUsersAccount().getUsersBiddings();
    }

    @Transactional
    public void buyItNow(Integer auctionId) {
        UsersAccountDetailsImpl loggedInUser = authService.getLoggedInUser();
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new AuctionNotFoundException("Auction was not found"));
        UsersAccount usersAccount = usersAccountRepository.findById(loggedInUser.getUsersAccount().getId())
                .orElseThrow(() -> new UserAccountNotFoundException("User was not found"));
        if(LocalDateTime.now().isBefore(auction.getDateOfIssue().plusHours(24))){
            Purchase purchase = new Purchase();

            purchase.setAuction(auction);
            purchase.setUsersAccount(usersAccount);
            purchase.setAmount(auction.getBinAmount());

            usersAccount.addPurchase(purchase);

            auction.setPurchase(purchase);
        }

        throw new InvalidPurchaseException("You are out of the Buy Now period");
    }
    @Transactional
    public Auction bidOnAuction(Integer auctionId, Double newBid){
        UsersAccountDetailsImpl loggedInUser = authService.getLoggedInUser();
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new AuctionNotFoundException("Auction was not found"));
        UsersAccount usersAccount = usersAccountRepository.findById(loggedInUser.getUsersAccount().getId())
                .orElseThrow(() -> new UserAccountNotFoundException("User was not found"));
        LocalDateTime biddingStartTime = auction.getDateOfIssue().plusHours(24);
        LocalDateTime now = LocalDateTime.now();
        if(now.isAfter(biddingStartTime) && now.isBefore(auction.getEndDate())){
            return placeBid(usersAccount, auction, newBid);
        }

        throw new BiddingNotFoundException("Bid can not be placed on this auction");
    }

    private Auction placeBid(UsersAccount usersAccount, Auction auction, Double newBidAmount) {
       if (!isNewBiddingValid(auction, newBidAmount)) {
           throw new BiddingNotValidException("New bid value must be larger than the last one");
       }

        Bidding newBidding = new Bidding();
        newBidding.setUsersAccount(usersAccount);
        newBidding.setAuction(auction);
        newBidding.setAmount(newBidAmount);

        auction.addBidding(newBidding);
        return auction;
    }

    private boolean isNewBiddingValid(Auction auction, Double newBidding) {
        List<Bidding> biddings = auction.getAuctionBiddings();
        if (biddings.isEmpty()) {
            return Double.compare(newBidding, auction.getMinAmount()) == 1;
        }

        Double lastHighestBidding = biddings.get(biddings.size() - 1).getAmount();
        return Double.compare(newBidding, lastHighestBidding) == 1;
    }
}
