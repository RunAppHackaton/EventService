package com.runapp.eventservice.service.impl;

import java.util.List;

import com.runapp.eventservice.exception.NoEntityFoundException;
import com.runapp.eventservice.repository.PrizeRepository;
import com.runapp.eventservice.model.Prize;
import com.runapp.eventservice.service.PrizeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PrizeServiceImpl implements PrizeService {
    private final PrizeRepository prizeRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PrizeServiceImpl.class);

    @Override
    @Caching(put = {@CachePut(value = "prizes", key = "#prize.id")},
            evict = {@CacheEvict(value = "prizes", allEntries = true)})
    public Prize add(Prize prize) {
        LOGGER.info("Prize add: {}", prize);
        return prizeRepository.save(prize);
    }

    @Override
    @Cacheable(value = "prizes", key = "#id")
    public Prize getById(Long id) {
        LOGGER.info("Prize get by id: {}", id);
        return prizeRepository.findById(id).orElseThrow(() -> {
            throw new NoEntityFoundException("Prize with id: " + id + " doesn't exist");
        });
    }

    @Override
    @Cacheable("prizes")
    public List<Prize> getAll() {
        LOGGER.info("Prize get all}");
        return prizeRepository.findAll();
    }

    @Override
    @CacheEvict(value = "prizes", key = "#id")
    public void deleteById(Long id) {
        LOGGER.info("Prize delete by id: {}", id);
        if (!prizeRepository.existsById(id)) {
            throw new NoEntityFoundException("Prize with id: " + id + " doesn't exist");
        }
        prizeRepository.deleteById(id);
    }

    @Override
    @Caching(put = {@CachePut(value = "prizes", key = "#prize.id")},
            evict = {@CacheEvict(value = "prizes", allEntries = true)})
    public Prize update(Prize prize) {
        LOGGER.info("Prize update: {}", prize);
        if (!prizeRepository.existsById(prize.getId())) {
            throw new NoEntityFoundException("Prize " + prize + " doesn't exist");
        }
        return prizeRepository.save(prize);
    }
}
