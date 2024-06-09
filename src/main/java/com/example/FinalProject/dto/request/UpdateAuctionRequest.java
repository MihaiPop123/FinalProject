package com.example.FinalProject.dto.request;

import com.example.FinalProject.entity.AuctionCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateAuctionRequest {

    private String title;
    private String description;
    private AuctionCategory category;
    private Double minAmount;
    private Double binAmount;
    private Boolean promoted;
}
