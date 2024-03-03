package team.mediasoft.study.spocktestcontainers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.mediasoft.study.spocktestcontainers.dto.CreateRentalRequestDto;
import team.mediasoft.study.spocktestcontainers.model.ClientBookList;
import team.mediasoft.study.spocktestcontainers.service.RentalService;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/rental")
public class RentalController {

    private final RentalService rentalService;

    @PostMapping
    public void rentBook(@RequestBody CreateRentalRequestDto createRentalRequestDto) {
        rentalService.rentBook(createRentalRequestDto.getBookId(), createRentalRequestDto.getClientId());
    }

    @GetMapping(path = "/{clientId}")
    public ClientBookList getClientActiveBooks(@PathVariable Long clientId) {
        return rentalService.getClientActiveBooks(clientId);
    }
}
