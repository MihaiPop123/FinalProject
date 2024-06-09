package com.example.FinalProject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPurchaseRequest {
    private String auctionTitle;
    private String usersAccountName;
    private Double amount;
}
