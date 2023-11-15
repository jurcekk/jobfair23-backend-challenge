package com.nordeus.jobfair.auctionservice.auctionservice.domain;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.*;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.service.AuctionNotifier;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;


import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.LinkedList;
import java.util.HashMap;
import javax.annotation.PostConstruct;

@Service
@AllArgsConstructor
public class AuctionServiceImpl implements AuctionService {

    private final AuctionNotifier auctionNotifier;

    private LinkedList<Auction> auctions;
    private LinkedList<Bid> bids;
    private LinkedList<User> users;
    private HashMap<Integer, Integer> userAuctions;


    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            users.add(user);
        }
    }

    @Override
    public Collection<Auction> getAllActive() {
        LinkedList<Auction> activeAuctions = new LinkedList<>();
        for (Auction auction : auctions) {
            if (auction.isActive()) {
                activeAuctions.add(auction);
            }
        }
        return activeAuctions;
    }

    @Override
    public Auction getAuction(Integer auctionId) {
        return auctions.get(auctionId);
    }

    @Override
    public Response join(Integer auctionId, UserId userId) {

        if (auctionId > auctions.size())
            return new Response(HttpStatus.BAD_REQUEST.value(), "Auction " + auctionId + " doesn't exist");

        if (userAuctions.containsKey(auctionId) && userAuctions.get(auctionId).equals(userId.getId())) {
            if (!auctions.get(auctionId).isActive()) {
                return new Response(HttpStatus.BAD_REQUEST.value(), "Auction is not active");
            }

            return new Response(HttpStatus.BAD_REQUEST.value(), "You are already in auction " + auctionId);
        }

        if (auctions.get(auctionId).isActive()) {
            userAuctions.put(auctionId, userId.getId());
            return new Response(HttpStatus.OK.value(), "You have successfully joined auction " + auctionId);
        } else {
            return new Response(HttpStatus.BAD_REQUEST.value(), "Auction is not active");
        }

    }

    @Override
    public Response bid(Integer auctionId, UserId userId) {

        if (auctionId > auctions.size())
            return new Response(HttpStatus.BAD_REQUEST.value(), "Auction " + auctionId + " doesn't exist");

        Auction auction = auctions.get(auctionId);
        User user = users.get(userId.getId());
        Duration duration = Duration.between(Instant.now(), auction.getEndTime());

        if (userAuctions.containsKey(auctionId) && userAuctions.get(auctionId).equals(userId.getId())) {

            if (auction.isActive() && user.getTokens() >= auction.getPrice()) {

                if (duration.getSeconds() < 5) {
                    auction.setEndTime(Instant.now().plusSeconds(5));
                }

                auction.setPrice(auction.getPrice() + 1);
                auction.setHighestBidUserId(userId.getId());

                user.setTokens(user.getTokens() - 1);

                Bid bid = new Bid(auctionId, userId);
                bids.add(bid);
                auctionNotifier.bidPlaced(bid);

                return new Response(HttpStatus.OK.value(), "You have successfully placed a bid on auction " + auctionId);

            } else {
                return new Response(HttpStatus.BAD_REQUEST.value(), "Auction" + auctionId + " is not active or user doesn't have enough tokens");
            }
        } else {
            return new Response(HttpStatus.BAD_REQUEST.value(), "You are not in auction " + auctionId);
        }
    }

    @Override
    public void createAuction() {
        Auction auction = new Auction();
        auctions.add(auction);
    }

    @Scheduled(fixedRate = 30000)
    public void createAuctions() {
        for (int i = 0; i < 10; i++) createAuction();
        auctionNotifier.activeAuctionsRefreshed(auctions);
    }

    @Scheduled(fixedRate = 1000)
    public void finishAuction() {
        for (Auction auction : auctions) {
            Instant time = auction.getEndTime();
            if (time.isBefore(Instant.now()) && auction.isActive()) {
                auction.setIsActive(false);
                auctionNotifier.auctionFinished(auction);
            }
        }
    }


}
