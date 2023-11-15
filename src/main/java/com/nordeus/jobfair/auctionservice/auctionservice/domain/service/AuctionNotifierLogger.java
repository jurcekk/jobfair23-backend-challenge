package com.nordeus.jobfair.auctionservice.auctionservice.domain.service;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.Auction;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.Bid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
public class AuctionNotifierLogger implements AuctionNotifier {

    @Override
    public void auctionFinished(Auction auction) {
        log.info("Auction {} finished. {} ", auction.getAuctionId().getId(), auction.getHighestBidUserId() == null ? "Nobody has won the auction." : "User " + auction.getHighestBidUserId() + " has won the auction.");
    }

    @Override
    public void bidPlaced(Bid bid) {
        log.info("User {} placed a bid on auction {}.", bid.getUserID().getId(), bid.getAuctionId());
    }

    @Override
    public void activeAuctionsRefreshed(Collection<Auction> activeAuctions) {
        log.info("Active auctions are refreshed: {}", activeAuctions);
    }
}
