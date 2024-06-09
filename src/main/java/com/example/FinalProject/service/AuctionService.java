package com.example.FinalProject.service;

import com.example.FinalProject.dto.request.AddAuctionRequest;
import com.example.FinalProject.dto.request.UpdateAuctionRequest;
import com.example.FinalProject.dto.response.AuctionResponse;

import java.util.List;

public interface AuctionService {

    List<AuctionResponse> getAllAuctions();

    AuctionResponse getAuctionById(Integer id);

    AuctionResponse createAuction(AddAuctionRequest addAuctionRequest);

    void updateAuction(Integer id, UpdateAuctionRequest updateOrderRequest);

    void deleteAuction(Integer id);

    List<AuctionResponse> getLatestAuctions(Integer maxCount);
}
