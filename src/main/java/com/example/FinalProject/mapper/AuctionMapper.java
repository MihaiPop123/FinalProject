package com.example.FinalProject.mapper;

import com.example.FinalProject.dto.request.AddAuctionRequest;
import com.example.FinalProject.dto.request.UpdateAuctionRequest;
import com.example.FinalProject.dto.response.AuctionResponse;
import com.example.FinalProject.entity.Auction;
import org.springframework.stereotype.Component;

@Component
public class AuctionMapper {

        public AuctionResponse fromAuction(Auction auction){
            AuctionResponse auctionResponse = new AuctionResponse();

            auctionResponse.setTitle(auction.getTitle());
            auctionResponse.setDescription(auction.getDescription());
            auctionResponse.setCategory(auction.getCategory());
            auctionResponse.setMinAmount(auction.getMinAmount());
            auctionResponse.setBinAmount(auction.getBinAmount());

            return auctionResponse;
        }

        public Auction fromAddAuctionRequest(AddAuctionRequest addAuctionRequest){
            Auction auction = new Auction();

            auction.setTitle(addAuctionRequest.getTitle());
            auction.setDescription(addAuctionRequest.getDescription());
            auction.setCategory(addAuctionRequest.getCategory());
            auction.setMinAmount(addAuctionRequest.getMinAmount());
            auction.setBinAmount(addAuctionRequest.getBinAmount());
            auction.setPromoted(addAuctionRequest.getPromoted());

            return auction;
        }

        public Auction fromUpdateAuctionRequest(Auction auctionTarget, UpdateAuctionRequest updateAuctionRequest){

            auctionTarget.setTitle(updateAuctionRequest.getTitle());
            auctionTarget.setDescription(updateAuctionRequest.getDescription());
            auctionTarget.setCategory(updateAuctionRequest.getCategory());
            auctionTarget.setMinAmount(updateAuctionRequest.getMinAmount());
            auctionTarget.setBinAmount(updateAuctionRequest.getBinAmount());
            auctionTarget.setPromoted(updateAuctionRequest.getPromoted());

            return auctionTarget;
        }

}
