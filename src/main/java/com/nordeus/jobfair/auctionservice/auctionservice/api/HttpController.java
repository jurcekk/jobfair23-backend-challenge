package com.nordeus.jobfair.auctionservice.auctionservice.api;

import com.nordeus.jobfair.auctionservice.auctionservice.domain.AuctionService;
import com.nordeus.jobfair.auctionservice.auctionservice.domain.model.*;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/auctions")
public class HttpController {

    private AuctionService auctionService;

    @GetMapping("/active")
    public Collection<Auction> getAllActive() {
        return auctionService.getAllActive();
    }


    @GetMapping("/{auctionId}")
    public Auction getAuction(@PathVariable("auctionId") Integer auctionId) {
        return auctionService.getAuction(auctionId);
    }

    @PostMapping("/join/{auctionId}")
    public Response join(@PathVariable("auctionId") Integer auctionId, @RequestBody UserId userId) {
        return auctionService.join(auctionId, userId);
    }

    @PostMapping("/bid/{auctionId}")
    public Response bid(@PathVariable("auctionId") Integer auctionId, @RequestBody UserId userId) {
       return auctionService.bid(auctionId, userId);
    }
}