package com.nordeus.jobfair.auctionservice.auctionservice.domain.model;

import lombok.Getter;

@Getter
public class AuctionId {
    private static int count = 0;
    private final Integer id;
    public AuctionId() {
        this.id = count;
        count++;
    }
}
