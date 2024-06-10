package com.example.FinalProject;

import com.example.FinalProject.dto.request.AddAuctionRequest;
import com.example.FinalProject.dto.response.AuctionResponse;
import com.example.FinalProject.entity.Auction;
import com.example.FinalProject.entity.AuctionCategory;
import com.example.FinalProject.mapper.AuctionMapper;
import com.example.FinalProject.repository.AuctionRepository;
import com.example.FinalProject.service.AuctionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuctionTest {

    @Mock
    private AuctionRepository auctionRepository;

    @Mock
    private AuctionMapper auctionMapper;

    @InjectMocks
    private AuctionServiceImpl auctionService;





    @Test
    public void testCreatedAuction(){
        AddAuctionRequest addAuctionRequest = new AddAuctionRequest();

        addAuctionRequest.setTitle("Mona Lisa");
        addAuctionRequest.setCategory(AuctionCategory.ART);
        addAuctionRequest.setPromoted(false);
        addAuctionRequest.setDescription("Beautiful");
        addAuctionRequest.setMinAmount(50.00);
        addAuctionRequest.setBinAmount(100.00);

        Auction auction = new Auction();

        AuctionResponse auctionResponse = new AuctionResponse();

        auctionResponse.setTitle("Mona Lisa");
        auctionResponse.setCategory(AuctionCategory.ART);
        auctionResponse.setDescription("Beautiful");
        auctionResponse.setBinAmount(100.00);
        auctionResponse.setMinAmount(50.00);


        when(auctionMapper.fromAddAuctionRequest(any())).thenReturn(auction);
        when(auctionRepository.save(any())).thenReturn(auction);
        when(auctionMapper.fromAuction(any())).thenReturn(auctionResponse);

        var response=this.auctionService.createAuction(addAuctionRequest);


        Assertions.assertNotNull(response);
        Assertions.assertEquals(auctionResponse.getTitle(),response.getTitle());
        Assertions.assertEquals(auctionResponse.getCategory(),response.getCategory());
    }
}
