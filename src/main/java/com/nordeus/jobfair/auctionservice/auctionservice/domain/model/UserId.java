package com.nordeus.jobfair.auctionservice.auctionservice.domain.model;

import lombok.Getter;

@Getter
public class UserId {
    private static int count = 0;
    private final Integer id;
    public UserId() {
        this.id = count;
        count++;
    }
}
