package team.mediasoft.study.spocktestcontainers.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team.mediasoft.study.spocktestcontainers.entity.Client;
import team.mediasoft.study.spocktestcontainers.entity.Rental;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ClientBookListMappingContainer {

    private final Client client;
    private final List<Rental> rentals;
}
