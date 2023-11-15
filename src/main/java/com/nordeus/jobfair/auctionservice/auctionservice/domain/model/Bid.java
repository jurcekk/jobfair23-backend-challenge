package com.nordeus.jobfair.auctionservice.auctionservice.domain.model;

import lombok.Getter;

@Getter
public class Bid {
    private final BidId bidId;
    private final Integer auctionId;
    private final UserId userID;

    public Bid(Integer auctionId, UserId userID) {
        this.bidId = new BidId();
        this.auctionId = auctionId;
        this.userID = userID;
    }
}
