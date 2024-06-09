package com.example.FinalProject.mapper;

import com.example.FinalProject.dto.request.AddBiddingRequest;
import com.example.FinalProject.dto.request.UpdateBiddingRequest;
import com.example.FinalProject.dto.response.BiddingResponse;
import com.example.FinalProject.entity.Bidding;
import org.springframework.stereotype.Component;

@Component
public class BiddingMapper {
    public BiddingResponse fromBidding(Bidding bidding){
        BiddingResponse biddingResponse = new BiddingResponse();

        biddingResponse.setAuctionTitle(bidding.getAuction().getTitle());
        biddingResponse.setUsersAccountName(bidding.getUsersAccount().getAccountName());
        biddingResponse.setAmount(bidding.getAmount());

        return biddingResponse;
    }

    public Bidding fromAddBiddingRequest(AddBiddingRequest addBiddingRequest){
        Bidding bidding = new Bidding();

        bidding.setAmount(addBiddingRequest.getAmount());

        return bidding;
    }

    public Bidding fromUpdateBiddingRequest(Bidding bidding, UpdateBiddingRequest updateBiddingRequest){

        bidding.setAmount(updateBiddingRequest.getAmount());

        return bidding;
    }
}
