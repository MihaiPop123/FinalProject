package com.example.FinalProject.controller;

import com.example.FinalProject.dto.request.AddAuctionRequest;
import com.example.FinalProject.dto.request.UpdateAuctionRequest;
import com.example.FinalProject.dto.response.AuctionResponse;
import com.example.FinalProject.service.AuctionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/final_project/v1/auction/")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

//    public AuctionController(AuctionService auctionService) {
//        this.auctionService = auctionService;
//    }



    @GetMapping
    public ResponseEntity<List<AuctionResponse>> getAllAuctions(){
        List<AuctionResponse> responseList = auctionService.getAllAuctions();

        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionResponse> getAuctionById(@PathVariable Integer id){
        AuctionResponse auctionResponse = auctionService.getAuctionById(id);

        return ResponseEntity.status(HttpStatus.FOUND).body(auctionResponse);
    }

    @DeleteMapping
    public ResponseEntity<AuctionResponse> deleteAuctionById(@RequestParam Integer id){
        auctionService.deleteAuction(id);

        return ResponseEntity.status(HttpStatus.GONE).build();
    }

    @PostMapping
    public ResponseEntity<AuctionResponse> createAuction(@RequestBody @Valid AddAuctionRequest addAuctionRequest){
        AuctionResponse auctionResponse = auctionService.createAuction(addAuctionRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(auctionResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateAuction(@PathVariable Integer id, @RequestBody @Valid UpdateAuctionRequest updateAuctionRequest){
        auctionService.updateAuction(id, updateAuctionRequest);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("get-latest-auctions/{count}")
    public ResponseEntity<List<AuctionResponse>> getLatestAuctions(@PathVariable Integer count){


        return ResponseEntity.status(HttpStatus.OK).body(auctionService.getLatestAuctions(count));
    }
}
