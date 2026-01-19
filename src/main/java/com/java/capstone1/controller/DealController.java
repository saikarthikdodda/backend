package com.java.capstone1.controller;
import com.java.capstone1.dto.DealRequest;
import com.java.capstone1.model.Deal;
import com.java.capstone1.model.DealNote;
import com.java.capstone1.service.DealService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@RestController
@RequestMapping("/api/deals")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class DealController {
    private final DealService dealService;
    @PostMapping
    public Deal create(@RequestBody DealRequest req, Principal principal) {
        return dealService.createDeal(req, principal.getName());
    }
    @GetMapping
    public List<Deal> all() {
        return dealService.getAllDeals();
    }
    @PatchMapping("/{id}/stage")
    //@PreAuthorize("hasRole('ADMIN')")
    public Deal stage(@PathVariable String id, @RequestParam String stage) {
        return dealService.updateStage(id, stage);
    }

    @PatchMapping("/{id}/value")
    @PreAuthorize("hasRole('ADMIN')")
    public Deal value(@PathVariable String id, @RequestParam Long value) {
        return dealService.updateValue(id, value);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable String id) {
        dealService.deleteDeal(id);
    }

    @PostMapping("/{id}/notes")
    public Deal addNote(@PathVariable String id, @RequestBody DealNote note) {
        return dealService.addNote(id, note);
    }
    @PatchMapping("/{id}/type")
    public Deal updateType(@PathVariable String id,
                           @RequestParam String type) {
       return dealService.updateType(id, type);
    }

    @PatchMapping("/{id}/sector")
    public Deal updateSector(@PathVariable String id,
                             @RequestParam String sector) {
       return dealService.updateSector(id, sector);
    }

    @PatchMapping("/{id}/summary")
    public Deal updateSummary(@PathVariable String id,
                              @RequestParam String summary) {
        return dealService.updateSummary(id, summary);
    }




}
