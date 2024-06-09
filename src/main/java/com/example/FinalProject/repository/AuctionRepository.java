package com.example.FinalProject.repository;


import com.example.FinalProject.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;



public interface AuctionRepository extends JpaRepository<Auction, Integer> {

    @Query("SELECT a FROM Auction a WHERE a.title = ?1 AND a.endDate < CURRENT_TIMESTAMP AND a.purchase IS NULL")
    Optional<Auction> findByTitle(String title);

    @Query("SELECT a FROM Auction a WHERE a.id = ?1 AND a.endDate < CURRENT_TIMESTAMP AND a.purchase IS NULL")
    Optional<Auction> findById(Integer id);

    @Query("SELECT a FROM Auction a WHERE a.endDate < CURRENT_TIMESTAMP AND a.purchase IS NULL")
    List<Auction> findAll();

        @Query("SELECT a " +
                "FROM Auction a " +
                "LEFT JOIN a.auctionBiddings b " +
                "WHERE b.id IS NULL AND a.endDate < CURRENT_TIMESTAMP AND a.purchase IS null")
        List<Auction> findExpiredAuctions();
}
