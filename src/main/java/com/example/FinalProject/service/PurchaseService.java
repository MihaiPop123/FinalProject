package com.example.FinalProject.service;

import com.example.FinalProject.dto.request.UpdatePurchaseRequest;
import com.example.FinalProject.dto.response.PurchaseResponse;

import java.util.List;

public interface PurchaseService {
    List<PurchaseResponse> getAllPurchases();

    PurchaseResponse getPurchaseById(Integer id);

    void deletePurchaseById(Integer id);

    void updatePurchase(Integer id, UpdatePurchaseRequest updatePurchaseRequest);
}