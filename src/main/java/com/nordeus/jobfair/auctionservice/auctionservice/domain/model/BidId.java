package com.nordeus.jobfair.auctionservice.auctionservice.domain.model;

import lombok.Getter;

@Getter

public class BidId {
    private static int count = 0;
    private final Integer id;
    public BidId() {
        this.id = count;
        count++;
    }
}
