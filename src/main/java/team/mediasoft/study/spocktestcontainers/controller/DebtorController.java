package team.mediasoft.study.spocktestcontainers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.mediasoft.study.spocktestcontainers.model.ClientDebt;
import team.mediasoft.study.spocktestcontainers.service.ClientStatisticService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/debtors")
public class DebtorController {

    private final ClientStatisticService clientStatisticService;

    @GetMapping(path = "/all/{forDays}")
    public List<ClientDebt> findDebtors(@PathVariable Integer forDays) {
        return clientStatisticService.findDebtors(forDays);
    }
}
