package com.example.FinalProject.service;

import com.example.FinalProject.dto.request.AddAuctionRequest;
import com.example.FinalProject.dto.request.UpdateAuctionRequest;
import com.example.FinalProject.dto.response.AuctionResponse;
import com.example.FinalProject.entity.Auction;
import com.example.FinalProject.entity.AuctionCategory;
import com.example.FinalProject.exceptions.AuctionNotFoundException;
import com.example.FinalProject.mapper.AuctionMapper;
import com.example.FinalProject.repository.AuctionRepository;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuctionServiceImpl implements AuctionService{

    AuctionRepository auctionRepository;

    AuctionMapper auctionMapper;

    public AuctionServiceImpl(AuctionRepository auctionRepository, AuctionMapper auctionMapper) {
        this.auctionRepository = auctionRepository;
        this.auctionMapper = auctionMapper;
    }


    @Override
    public List<AuctionResponse> getAllAuctions() {

        List<Auction> auctions = auctionRepository.findAll();

        List<AuctionResponse> auctionResponseList = auctions.stream().map(element -> auctionMapper.fromAuction(element)).collect(Collectors.toList());

        return auctionResponseList;

    }

    @Override
    public AuctionResponse getAuctionById(Integer id) {
        Optional<Auction> optionalAuction = auctionRepository.findById(id);
        if (optionalAuction.isPresent()) {
            Auction auction = optionalAuction.get();
            AuctionResponse auctionResponse = auctionMapper.fromAuction(auction);
            return auctionResponse;
        } else {
            throw new AuctionNotFoundException("Auction with id " + id + " not found");
        }
    }

    @Transactional
    @Override
    public AuctionResponse createAuction(AddAuctionRequest addAuctionRequest) {
        Auction auction = auctionMapper.fromAddAuctionRequest(addAuctionRequest);

        auction.setEndDate(LocalDateTime.now().plusDays(30L));

        auctionRepository.save(auction);

        AuctionResponse auctionResponse = auctionMapper.fromAuction(auction);

        return auctionResponse;
    }

    @Transactional
    @Override
    public void updateAuction(Integer id, UpdateAuctionRequest updateAuctionRequest) {
        Optional<Auction> optionalAuction = auctionRepository.findById(id);

        if (optionalAuction.isPresent()) {
            Auction auction = auctionMapper.fromUpdateAuctionRequest(optionalAuction.get(), updateAuctionRequest);
            auctionRepository.save(auction);
        } else {
            throw new AuctionNotFoundException("Auction with id " + id + " not found");
        }
    }


    @Transactional
    @Override
    public void deleteAuction(Integer id) {
        Optional<Auction> optionalAuction = auctionRepository.findById(id);

        if (optionalAuction.isPresent()) {
            auctionRepository.deleteById(id);
        } else {
            throw new AuctionNotFoundException("Auction with id " + id + " not found");
        }
    }

    @Override
    public List<AuctionResponse> getLatestAuctions(Integer maxCount) {

        List<Auction> auctionList = auctionRepository.findAll();

        List<Auction> sortedAuctionList = auctionList.stream()
                .sorted(Comparator.comparing(Auction::getDateOfIssue).reversed())
                .limit(maxCount)
                .collect(Collectors.toList());

        List<AuctionResponse> auctionResponseList = sortedAuctionList.stream()
                .map(element -> auctionMapper.fromAuction(element))
                .collect(Collectors.toList());
        return auctionResponseList;
    }


    @Override
    public List<AuctionResponse> getAuctionsThatAreEndingSoon(Integer maxCount) {

        List<Auction> auctionList = auctionRepository.findAll();

        List<Auction> sortedAuctionList = auctionList.stream()
                .sorted(Comparator.comparing(Auction::getDateOfIssue))
                .limit(maxCount)
                .collect(Collectors.toList());

        List<AuctionResponse> auctionResponseList = sortedAuctionList.stream()
                .map(element -> auctionMapper.fromAuction(element))
                .collect(Collectors.toList());
        return auctionResponseList;
    }

    @Override
    public List<AuctionResponse> getAuctionsThatJustEnded(Integer maxCount) {

        List<Auction> auctionList = auctionRepository.findAll();

        List<Auction> sortedAuctionList = auctionList.stream()
                .filter(element -> LocalDateTime.now().isAfter(element.getEndDate()))
                .limit(maxCount)
                .collect(Collectors.toList());

        List<AuctionResponse> auctionResponseList = sortedAuctionList.stream()
                .map(element -> auctionMapper.fromAuction(element))
                .collect(Collectors.toList());

        return auctionResponseList;
    }


    @Override
    public List<AuctionResponse> getAuctionsByCategory(AuctionCategory auctionCategory) {

        List<Auction> auctionList = auctionRepository.findAll().stream()
                .filter(element -> auctionCategory.equals(element.getCategory()))
                .collect(Collectors.toList());

        List<AuctionResponse> auctionResponseList = auctionList.stream()
                .map(element -> auctionMapper.fromAuction(element))
                .collect(Collectors.toList());

        return auctionResponseList;
    }

    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void removeExpiredAuctions(){
        System.out.println("removing expired auctions");
        List<Auction> auctions = auctionRepository.findExpiredAuctions();
        auctionRepository.deleteAll(auctions);
    }

}
