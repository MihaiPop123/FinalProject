package com.example.FinalProject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "usersAccount")
@Table(name="Purchase")

public class Purchase implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double amount;
    @OneToOne
    @JoinColumn(name = "auction_id")
    private Auction auction;
    @ManyToOne
    @JoinColumn(name = "usersAccount_id")
    private UsersAccount usersAccount;


}
