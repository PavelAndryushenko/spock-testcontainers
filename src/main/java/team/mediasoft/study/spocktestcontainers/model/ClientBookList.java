package team.mediasoft.study.spocktestcontainers.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ClientBookList {

    private final Long clientId;
    private final String clientFullname;
    private final List<ClientBookListItem> books;
}
