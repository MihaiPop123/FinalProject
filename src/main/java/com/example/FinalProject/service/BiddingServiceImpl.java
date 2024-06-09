package com.example.FinalProject.service;

import com.example.FinalProject.dto.request.UpdateBiddingRequest;
import com.example.FinalProject.dto.response.BiddingResponse;
import com.example.FinalProject.entity.Bidding;
import com.example.FinalProject.exceptions.BiddingNotFoundException;
import com.example.FinalProject.mapper.BiddingMapper;
import com.example.FinalProject.repository.AuctionRepository;
import com.example.FinalProject.repository.BiddingRepository;
import com.example.FinalProject.repository.UsersAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class BiddingServiceImpl implements BiddingService {


    private final BiddingRepository biddingRepository;
    private final AuctionRepository auctionRepository;
    private final UsersAccountRepository usersAccountRepository;

    private final BiddingMapper biddingMapper;

    public BiddingServiceImpl(BiddingRepository biddingRepository, AuctionRepository auctionRepository, UsersAccountRepository usersAccountRepository, BiddingMapper biddingMapper) {
        this.biddingRepository = biddingRepository;
        this.auctionRepository = auctionRepository;
        this.usersAccountRepository = usersAccountRepository;
        this.biddingMapper = biddingMapper;
    }


    @Override
    public List<BiddingResponse> getAllBiddings() {
        List<Bidding> biddings = biddingRepository.findAll();

        List<BiddingResponse> biddingResponseList = biddings.stream().map(element->biddingMapper.fromBidding(element)).collect(Collectors.toList());

        return biddingResponseList;

    }

    @Override
    public BiddingResponse getBiddingById(Integer id) {
        Optional<Bidding> optionalBidding = biddingRepository.findById(id);
        if(optionalBidding.isPresent()){
            Bidding bidding = optionalBidding.get();
            BiddingResponse biddingResponse = biddingMapper.fromBidding(bidding);
            return biddingResponse;
        }else{
            throw new BiddingNotFoundException("Auction with id " + id + " not found");
        }
    }
//    @Transactional
//    public BiddingResponse createBidding(AddBiddingRequest addBiddingRequest) {
//
//        Bidding bidding =biddingMapper.fromAddBiddingRequest(addBiddingRequest);
//
//        Optional<Auction> auctionOptional = auctionRepository.findByTitle(addBiddingRequest.getAuctionTitle());
//        Optional<UsersAccount> usersAccountOptional= usersAccountRepository.findByAccountName(addBiddingRequest.
//                getUsersAccountName());
//
//        if(auctionOptional.isPresent() && usersAccountOptional.isPresent())
//        {
//            bidding.setAuction(auctionOptional.get());
//            bidding.setUsersAccount(usersAccountOptional.get());
//        }
//        else {
//            throw new NotFoundException("The user or the auction is not yet created");
//        }
//
//        biddingRepository.save(bidding);
//
//        BiddingResponse biddingResponse = biddingMapper.fromBidding(bidding);
//
//        return biddingResponse;
//    }
    @Transactional
    @Override
    public void updateBidding(Integer id, UpdateBiddingRequest updateBiddingRequest) {
        Optional<Bidding> optionalBidding = biddingRepository.findById(id);

        if(optionalBidding.isPresent()) {
            Bidding bidding = biddingMapper.fromUpdateBiddingRequest(optionalBidding.get(), updateBiddingRequest);
            biddingRepository.save(bidding);
        } else{
            throw new BiddingNotFoundException("Bidding with id " + id + " not found");
        }

    }

    @Transactional
    public void deleteBidding(Integer id) {
        Optional<Bidding> optionalBidding = biddingRepository.findById(id);

        if(optionalBidding.isPresent()) {
            biddingRepository.deleteById(id);
        } else{
            throw new BiddingNotFoundException("Bidding with id " + id + " not found");
        }

    }


}
