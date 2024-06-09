package com.example.FinalProject.dto.response;

import com.example.FinalProject.entity.AuctionCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuctionResponse {

    private String title;
    private String description;
    private AuctionCategory category;
    private Double minAmount;
    private Double binAmount;
}
