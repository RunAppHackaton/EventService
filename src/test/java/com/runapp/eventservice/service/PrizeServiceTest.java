package com.runapp.eventservice.service;

import com.runapp.eventservice.exception.NoEntityFoundException;
import com.runapp.eventservice.model.Prize;
import com.runapp.eventservice.repository.PrizeRepository;
import com.runapp.eventservice.service.impl.PrizeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrizeServiceTest {

    @Mock
    private PrizeRepository prizeRepository;

    @InjectMocks
    private PrizeServiceImpl prizeService;

    @Test
    void testAddPrize() {
        Prize prize = new Prize();
        when(prizeRepository.save(any())).thenReturn(prize);

        Prize addedPrize = prizeService.add(prize);

        assertNotNull(addedPrize);
        verify(prizeRepository, times(1)).save(prize);
    }

    @Test
    void testGetPrizeById() {
        Long id = 1L;
        Prize prize = new Prize();
        when(prizeRepository.findById(id)).thenReturn(Optional.of(prize));

        Prize retrievedPrize = prizeService.getById(id);

        assertNotNull(retrievedPrize);
        verify(prizeRepository, times(1)).findById(id);
    }

    @Test
    void testGetPrizeByIdNotFound() {
        Long id = 1L;
        when(prizeRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoEntityFoundException.class, () -> prizeService.getById(id));
        verify(prizeRepository, times(1)).findById(id);
    }

    @Test
    void testGetAllPrizes() {
        List<Prize> prizeList = new ArrayList<>();
        when(prizeRepository.findAll()).thenReturn(prizeList);

        List<Prize> retrievedPrizes = prizeService.getAll();

        assertEquals(prizeList, retrievedPrizes);
        verify(prizeRepository, times(1)).findAll();
    }

    @Test
    void testDeletePrizeById() {
        Long id = 1L;
        when(prizeRepository.existsById(id)).thenReturn(true);

        prizeService.deleteById(id);

        verify(prizeRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeletePrizeByIdNotFound() {
        Long id = 1L;
        when(prizeRepository.existsById(id)).thenReturn(false);

        assertThrows(NoEntityFoundException.class, () -> prizeService.deleteById(id));
        verify(prizeRepository, times(0)).deleteById(id);
    }

    @Test
    void testUpdatePrize() {
        Prize prize = new Prize();
        when(prizeRepository.existsById(prize.getId())).thenReturn(true);
        when(prizeRepository.save(prize)).thenReturn(prize);

        Prize updatedPrize = prizeService.update(prize);

        assertNotNull(updatedPrize);
        verify(prizeRepository, times(1)).save(prize);
    }

    @Test
    void testUpdatePrizeNotFound() {
        Prize prize = new Prize();
        when(prizeRepository.existsById(prize.getId())).thenReturn(false);

        assertThrows(NoEntityFoundException.class, () -> prizeService.update(prize));
        verify(prizeRepository, times(0)).save(prize);
    }
}
