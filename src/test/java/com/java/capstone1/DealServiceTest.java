package com.java.capstone1;

import com.java.capstone1.dto.DealRequest;
import com.java.capstone1.exception.ResourceNotFoundException;
import com.java.capstone1.kafka.DealProducer;
import com.java.capstone1.model.Deal;
import com.java.capstone1.model.DealNote;
import com.java.capstone1.repository.DealRepository;
import com.java.capstone1.service.DealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
class DealServiceTest {

    @Mock
    private DealRepository dealRepository;

    @Mock
    private DealProducer dealProducer;

    @InjectMocks
    private DealService dealService;

    private Deal deal;

    @BeforeEach
    void setup() {
        deal = new Deal();
        deal.setId("1");
        deal.setDealType("New");
        deal.setSector("IT");
        deal.setSummary("Test deal");
        deal.setCurrentStage("Prospect");
        deal.setNotes(new ArrayList<>());
    }

    // ================= CREATE DEAL =================
    @Test
    void createDeal_success() {
        DealRequest req = new DealRequest();
        req.setClientName("Client A");
        req.setDealType("New");
        req.setSector("IT");
        req.setSummary("Deal summary");

        when(dealRepository.save(any(Deal.class))).thenReturn(deal);

        Deal result = dealService.createDeal(req, "user1");

        assertNotNull(result);
        verify(dealRepository).save(any(Deal.class));
        verify(dealProducer).sendDealCreatedEvent(any(Deal.class));
    }

    // ================= GET ALL =================
    @Test
    void getAllDeals_success() {
        when(dealRepository.findAll()).thenReturn(List.of(deal));

        List<Deal> deals = dealService.getAllDeals();

        assertEquals(1, deals.size());
        verify(dealRepository).findAll();
    }

    // ================= UPDATE STAGE =================
    @Test
    void updateStage_success() {
        when(dealRepository.findById("1")).thenReturn(Optional.of(deal));
        when(dealRepository.save(any())).thenReturn(deal);

        Deal updated = dealService.updateStage("1", "Negotiation");

        assertEquals("Negotiation", updated.getCurrentStage());
        verify(dealProducer).sendDealStageUpdatedEvent(any());
    }

    @Test
    void updateStage_notFound() {
        when(dealRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> dealService.updateStage("1", "Negotiation"));
    }

    // ================= UPDATE VALUE =================
    @Test
    void updateValue_success() {
        when(dealRepository.findById("1")).thenReturn(Optional.of(deal));
        when(dealRepository.save(any())).thenReturn(deal);

        Deal updated = dealService.updateValue("1", 100000L);

        assertEquals(100000L, updated.getDealValue());
        verify(dealProducer).sendDealValueUpdatedEvent(any());
    }

    // ================= ADD NOTE =================
    @Test
    void addNote_success() {
        DealNote note = new DealNote();

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication())
                .thenReturn(new UsernamePasswordAuthenticationToken("testUser", null));
        SecurityContextHolder.setContext(securityContext);

        when(dealRepository.findById("1")).thenReturn(Optional.of(deal));
        when(dealRepository.save(any())).thenReturn(deal);

        Deal updated = dealService.addNote("1", note);

        assertEquals(1, updated.getNotes().size());
        verify(dealProducer).sendDealNoteAddedEvent(any());
    }

    // ================= DELETE =================
    @Test
    void deleteDeal_success() {
        doNothing().when(dealRepository).deleteById("1");

        dealService.deleteDeal("1");

        verify(dealRepository).deleteById("1");
        verify(dealProducer).sendDealDeletedEvent("1");
    }

    // ================= UPDATE TYPE =================
    @Test
    void updateType_success() {
        when(dealRepository.findById("1")).thenReturn(Optional.of(deal));
        when(dealRepository.save(any())).thenReturn(deal);

        Deal updated = dealService.updateType("1", "Renewal");

        assertEquals("Renewal", updated.getDealType());
        verify(dealProducer).sendDealTypeUpdatedEvent(any());
    }

    // ================= UPDATE SECTOR =================
    @Test
    void updateSector_success() {
        when(dealRepository.findById("1")).thenReturn(Optional.of(deal));
        when(dealRepository.save(any())).thenReturn(deal);

        Deal updated = dealService.updateSector("1", "Finance");

        assertEquals("Finance", updated.getSector());
        verify(dealProducer).sendDealSectorUpdatedEvent(any());
    }

    // ================= UPDATE SUMMARY =================
    @Test
    void updateSummary_success() {
        when(dealRepository.findById("1")).thenReturn(Optional.of(deal));
        when(dealRepository.save(any())).thenReturn(deal);

        Deal updated = dealService.updateSummary("1", "Updated summary");

        assertEquals("Updated summary", updated.getSummary());
        verify(dealProducer).sendDealSummaryUpdatedEvent(any());
    }
}
