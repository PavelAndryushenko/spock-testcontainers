package team.mediasoft.study.spocktestcontainers.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ClientBookListItem {

    private final Long bookId;
    private final String bookName;
}
