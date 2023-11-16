package com.nordeus.jobfair.auctionservice.auctionservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
//@AllArgsConstructor
public class Auction {

    private final AuctionId auctionId;
    private Integer price;
    private Instant endTime;
    private Boolean isActive;
    private Integer highestBidUserId;

    public Auction() {
        this.auctionId = new AuctionId();
        this.price = 1;
        this.endTime = Instant.now().plusSeconds(60);
        this.isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean state) {
        isActive = state;
    }

    public void setEndTime(Instant instant) {
        endTime = instant;
    }

    public void setPrice(int i) {
        price = i;
    }

    public void setHighestBidUserId(int i) {
        highestBidUserId = i;
    }
}
