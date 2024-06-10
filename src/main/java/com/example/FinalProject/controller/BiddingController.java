package com.example.FinalProject.controller;

import com.example.FinalProject.dto.request.UpdateBiddingRequest;
import com.example.FinalProject.dto.response.BiddingResponse;
import com.example.FinalProject.service.BiddingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/final_project/v1/bidding/")

public class BiddingController {

    private final BiddingService biddingService;

    public BiddingController(BiddingService biddingService) {
        this.biddingService = biddingService;
    }

    @GetMapping()
    public ResponseEntity<List<BiddingResponse>> getAllBiddings() {
        List<BiddingResponse> responseBody = biddingService.getAllBiddings();

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

//    @PostMapping
//    public ResponseEntity<BiddingResponse> createBidding(@RequestBody AddBiddingRequest addBiddingRequest) {
//        BiddingResponse responseBody = biddingService.createBidding(addBiddingRequest);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
//    }



    @GetMapping("/{id}")
    public ResponseEntity<BiddingResponse> getBiddingById(@PathVariable Integer id) {
        BiddingResponse responseBody = biddingService.getBiddingById(id);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }


    @PatchMapping("/{id}")
    public ResponseEntity<BiddingResponse> updateBidding(@PathVariable Integer id, @RequestBody @Valid UpdateBiddingRequest updateBiddingRequest){
        biddingService.updateBidding(id, updateBiddingRequest);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBiddingById(@RequestParam Integer id) {
        biddingService.deleteBidding(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
