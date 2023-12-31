package com.runapp.eventservice.service.impl;

import java.util.List;

import com.runapp.eventservice.exception.NoEntityFoundException;
import com.runapp.eventservice.repository.ParticipationInEventsRepository;
import com.runapp.eventservice.model.ParticipationInEvents;
import com.runapp.eventservice.service.ParticipationInEventsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ParticipationInEventsServiceImpl implements ParticipationInEventsService {
    private final ParticipationInEventsRepository participationInEventsRepository;

    @Override
    public ParticipationInEvents add(ParticipationInEvents participationInEvents) {
        return participationInEventsRepository.save(participationInEvents);
    }

    @Override
    public ParticipationInEvents getById(Long id) {
        return participationInEventsRepository.findById(id).orElseThrow(() -> {
            throw new NoEntityFoundException("ParticipationInEvents with id: " + id + " doesn't exist");
        });
    }

    @Override
    public List<ParticipationInEvents> getAll() {
        return participationInEventsRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        if (!participationInEventsRepository.existsById(id)){
            participationInEventsRepository.deleteById(id);
        }
        throw new NoEntityFoundException("ParticipationInEvents with id: " + id + " doesn't exist");
    }

    @Override
    public ParticipationInEvents update(ParticipationInEvents participationInEvents) {
        if (!participationInEventsRepository.existsById(participationInEvents.getId())) {
            throw new NoEntityFoundException("ParticipationInEvents " + participationInEvents + " doesn't exist");
        }
        return participationInEventsRepository.save(participationInEvents);
    }
}
