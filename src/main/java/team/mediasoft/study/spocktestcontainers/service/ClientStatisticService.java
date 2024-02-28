package team.mediasoft.study.spocktestcontainers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.mediasoft.study.spocktestcontainers.model.ClientDebt;
import team.mediasoft.study.spocktestcontainers.repository.ClientStatisticRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientStatisticService {

    private final ClientStatisticRepository statisticRepository;

    public List<ClientDebt> findDebtors(Integer rentalDays) {
        return statisticRepository.findDebtors(LocalDate.now().minusDays(rentalDays));
    }
}
