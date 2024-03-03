package team.mediasoft.study.spocktestcontainers.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CreateRentalRequestDto {

    private final Long bookId;
    private final Long clientId;

    @JsonCreator
    public CreateRentalRequestDto(@JsonProperty Long bookId, @JsonProperty Long clientId) {
        this.bookId = bookId;
        this.clientId = clientId;
    }
}
