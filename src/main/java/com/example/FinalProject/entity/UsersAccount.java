package com.example.FinalProject.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "UsersAccount")
public class UsersAccount implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "accountName")
    private String accountName;

    @Column(name = "province")
    private String province;

    @Column(name = "country")
    private String country;

    @Column(name = "address")
    private String address;

    @CreationTimestamp
    private LocalDateTime dateOfAccountCreation;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_types", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "type_id")})
    private List<AccountType> accountTypes = new ArrayList<>();

    @Email
    private String accountEmail;

    private String password;

    @OneToMany(mappedBy = "usersAccount", fetch = FetchType.LAZY)
    private List<Bidding> usersBiddings = new ArrayList<>();

    @OneToMany(mappedBy = "usersAccount", fetch = FetchType.LAZY)
    private List<Purchase> usersPurchases = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Auction> watchedAuctions = new ArrayList<>();

    public void addType(AccountType accountType){

        this.accountTypes.add(accountType);
        accountType.getUsersAccounts().add(this);
    }
    public void addAuctionToWatchList(Auction auction){

        this.watchedAuctions.add(auction);
    }

    public void addPurchase(Purchase purchase){
        this.usersPurchases.add(purchase);
    }
}
