package com.example.FinalProject.service;


import com.example.FinalProject.dto.request.UpdatePurchaseRequest;
import com.example.FinalProject.dto.response.PurchaseResponse;
import com.example.FinalProject.entity.Purchase;
import com.example.FinalProject.exceptions.PurchaseNotFoundException;
import com.example.FinalProject.mapper.PurchaseMapper;
import com.example.FinalProject.repository.AuctionRepository;
import com.example.FinalProject.repository.PurchaseRepository;
import com.example.FinalProject.repository.UsersAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private AuctionRepository auctionRepository;

    private UsersAccountRepository usersAccountRepository;

    private PurchaseRepository purchaseRepository;

    private PurchaseMapper purchaseMapper;

    public PurchaseServiceImpl(AuctionRepository auctionRepository, UsersAccountRepository userRepository, PurchaseRepository purchaseRepository, PurchaseMapper purchaseMapper) {
        this.auctionRepository = auctionRepository;
        this.usersAccountRepository = userRepository;
        this.purchaseRepository = purchaseRepository;
        this.purchaseMapper = purchaseMapper;
    }

    @Override
    public List<PurchaseResponse> getAllPurchases() {
        List<Purchase> purchases = purchaseRepository.findAll();

        List<PurchaseResponse> purchaseResponseList = purchases.stream().map(element->purchaseMapper.fromPurchase(element)).collect(Collectors.toList());

        return purchaseResponseList;

    }

    @Override
   public PurchaseResponse getPurchaseById(Integer id) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(id);
        if(purchaseOptional.isPresent()){
            Purchase purchase = purchaseOptional.get();
            PurchaseResponse purchaseResponse = purchaseMapper.fromPurchase(purchase);
            return purchaseResponse;
        }else{
            throw new PurchaseNotFoundException("Purchase with id " + id + " not found");
        }
    }

//    @Override
//    @Transactional
//    public PurchaseResponse createPurchase(AddPurchaseRequest addPurchaseRequest) {
//
//        Purchase purchase = purchaseMapper.fromAddPurchaseRequest(addPurchaseRequest);
//
//        Optional<Auction> auctionOptional = auctionRepository.findByTitle(addPurchaseRequest.getAuctionTitle());
//        Optional<UsersAccount> usersAccountOptional= usersAccountRepository.findByAccountName(addPurchaseRequest.
//                getUsersAccountName());
//
//        if(auctionOptional.isPresent() && usersAccountOptional.isPresent())
//        {
//            purchase.setAuction(auctionOptional.get());
//            purchase.setUsersAccount(usersAccountOptional.get());
//        }
//        else {
//            throw new NotFoundException("The user or the auction is not yet created");
//        }
//
//        purchaseRepository.save(purchase);
//
//        PurchaseResponse purchaseResponse = purchaseMapper.fromPurchase(purchase);
//
//        return purchaseResponse;
//    }

    @Override
    @Transactional
    public void deletePurchaseById(Integer id) {
        Optional<Purchase> optionalPurchase = purchaseRepository.findById(id);

        if(optionalPurchase.isPresent()) {
            purchaseRepository.deleteById(id);
        } else{
            throw new PurchaseNotFoundException("Purchase with id " + id + " not found");
        }

    }

    @Override
    @Transactional
    public void updatePurchase(Integer id, UpdatePurchaseRequest updatePurchaseRequest) {
        Optional<Purchase> optionalPurchase = purchaseRepository.findById(id);

        if(optionalPurchase.isPresent()) {
            Purchase purchase= purchaseMapper.fromUpdatePurchaseRequest(optionalPurchase.get(), updatePurchaseRequest);
            purchaseRepository.save(purchase);
        } else{
            throw new PurchaseNotFoundException("Purchase with id " + id + " not found");
        }

    }
}