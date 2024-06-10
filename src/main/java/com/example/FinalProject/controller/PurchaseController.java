package com.example.FinalProject.controller;

import com.example.FinalProject.dto.request.UpdatePurchaseRequest;
import com.example.FinalProject.dto.response.PurchaseResponse;
import com.example.FinalProject.service.PurchaseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/final_project/v1/purchase/")

public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }


    @GetMapping()
    public ResponseEntity<List<PurchaseResponse>> getAllPurchases() {
        List<PurchaseResponse> responseBody = (List<PurchaseResponse>) purchaseService.getAllPurchases();

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseResponse> getPurchaseById(@PathVariable Integer id) {
        PurchaseResponse responseBody = purchaseService.getPurchaseById(id);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePurchaseById(@RequestParam Integer id) {
        purchaseService.deletePurchaseById(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

//    @PostMapping()
//    public ResponseEntity<PurchaseResponse> addPurchase(@RequestBody @Valid AddPurchaseRequest addPurchaseRequest){
//
//        PurchaseResponse responseBody = purchaseService.createPurchase(addPurchaseRequest);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
//    }

    @PatchMapping("/{id}")
    public ResponseEntity<PurchaseResponse> updatePurchase(@PathVariable Integer id, @RequestBody @Valid UpdatePurchaseRequest updatePurchaseRequest){
        purchaseService.updatePurchase(id, updatePurchaseRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
