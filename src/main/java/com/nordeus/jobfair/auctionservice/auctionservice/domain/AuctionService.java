package com.nordeus.jobfair.auctionservice.auctionservice.domain;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.AuctionId;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.Response;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.UserId;

import java.util.Collection;

public interface AuctionService {

    Collection<Auction> getAllActive();

    Auction getAuction(Integer auctionId);

    Response join(Integer auctionId, UserId userId);

    Response bid(Integer auctionId, UserId userId);

    void createAuction();
}
