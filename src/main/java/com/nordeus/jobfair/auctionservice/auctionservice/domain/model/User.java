package com.nordeus.jobfair.auctionservice.auctionservice.domain.model;

import lombok.Getter;

@Getter

public class User {

    private final UserId userId;
    private Integer tokens;

    public User() {
        this.userId = new UserId();
        this.tokens = 5;
    }

    public void setTokens(int i) {
        this.tokens = i;
    }
}