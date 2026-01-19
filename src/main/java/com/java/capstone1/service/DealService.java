package com.java.capstone1.service;

import com.java.capstone1.dto.DealRequest;
import com.java.capstone1.exception.ResourceNotFoundException;
import com.java.capstone1.kafka.DealProducer;
import com.java.capstone1.model.Deal;
import com.java.capstone1.model.DealNote;
import com.java.capstone1.repository.DealRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
@Service
@RequiredArgsConstructor
public class DealService {

    private final DealRepository dealRepository;
    private final DealProducer dealProducer;

    public Deal createDeal(DealRequest req, String loggedInUsername) {

        Deal deal = new Deal();
        deal.setClientName(req.getClientName());
        deal.setDealType(req.getDealType());
        deal.setSector(req.getSector());
        deal.setSummary(req.getSummary());
        deal.setCurrentStage("Prospect");
        deal.setCreatedBy(loggedInUsername.toUpperCase());

        Deal savedDeal = dealRepository.save(deal);

        // Kafka – Deal Created
        dealProducer.sendDealCreatedEvent(savedDeal);

        return savedDeal;
    }

    public List<Deal> getAllDeals() {
        return dealRepository.findAll();
    }

    public Deal updateStage(String id, String stage) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        deal.setCurrentStage(stage);
        deal.setUpdatedAt(Instant.now());

        Deal updated = dealRepository.save(deal);

        // Kafka – Stage Updated
        dealProducer.sendDealStageUpdatedEvent(updated);

        return updated;
    }

    public Deal updateValue(String id, Long value) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        deal.setDealValue(value);

        Deal updated = dealRepository.save(deal);

        // Kafka – Value Updated
        dealProducer.sendDealValueUpdatedEvent(updated);

        return updated;
    }

    public Deal addNote(String dealId, DealNote note) {

        Deal deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new RuntimeException("Deal not found"));

        note.setUserId(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        note.setTimestamp(Instant.now());
        deal.getNotes().add(note);

        Deal updated = dealRepository.save(deal);

        // Kafka – Note Added
        dealProducer.sendDealNoteAddedEvent(updated);

        return updated;
    }

    public void deleteDeal(String id) {

        dealRepository.deleteById(id);

        // Kafka – Deal Deleted
        dealProducer.sendDealDeletedEvent(id);
    }

    public Deal updateType(String id, String type) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        deal.setDealType(type);
        deal.setUpdatedAt(Instant.now());

        Deal updated = dealRepository.save(deal);

        // Kafka – Type Updated
        dealProducer.sendDealTypeUpdatedEvent(updated);

        return updated;
    }

    public Deal updateSector(String id, String sector) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        deal.setSector(sector);
        deal.setUpdatedAt(Instant.now());

        Deal updated = dealRepository.save(deal);

        // Kafka – Sector Updated
        dealProducer.sendDealSectorUpdatedEvent(updated);

        return updated;
    }

    public Deal updateSummary(String id, String summary) {
        Deal deal = dealRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Deal not found"));

        deal.setSummary(summary);
        deal.setUpdatedAt(Instant.now());

        Deal updated = dealRepository.save(deal);

        // Kafka – Summary Updated
        dealProducer.sendDealSummaryUpdatedEvent(updated);

        return updated;
    }
}
