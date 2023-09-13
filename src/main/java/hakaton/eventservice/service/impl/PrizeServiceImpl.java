package hakaton.eventservice.service.impl;

import hakaton.eventservice.exception.NoEntityException;
import hakaton.eventservice.model.Prize;
import hakaton.eventservice.repository.PrizeRepository;
import hakaton.eventservice.service.PrizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PrizeServiceImpl implements PrizeService {
    private final PrizeRepository prizeRepository;


    @Override
    public Prize add(Prize prize) {
        return prizeRepository.save(prize);
    }

    @Override
    public Prize getById(Long id) {
        return prizeRepository.findById(id).orElseThrow(() -> {
            throw new NoEntityException("Prize with id: " + id + " doesn't exist");
        });
    }

    @Override
    public List<Prize> getAll() {
        return prizeRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        prizeRepository.deleteById(id);
    }

    @Override
    public Prize update(Prize prize) {
        if (!prizeRepository.existsById(prize.getId())) {
            throw new NoEntityException("Prize " + prize + " doesn't exist");
        }
        return prizeRepository.save(prize);
    }
}
