package team.mediasoft.study.spocktestcontainers.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class ClientDebt {

    private final Long clientId;
    private final String clientName;
    private final Integer bookCount;
    private final LocalDate debtDate;
}
