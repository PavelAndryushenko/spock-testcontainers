package team.mediasoft.study.spocktestcontainers.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.mediasoft.study.spocktestcontainers.converter.RentalMapper;
import team.mediasoft.study.spocktestcontainers.entity.Book;
import team.mediasoft.study.spocktestcontainers.entity.Client;
import team.mediasoft.study.spocktestcontainers.entity.Rental;
import team.mediasoft.study.spocktestcontainers.exception.BookNotAvailableException;
import team.mediasoft.study.spocktestcontainers.exception.NotFoundException;
import team.mediasoft.study.spocktestcontainers.model.ClientBookList;
import team.mediasoft.study.spocktestcontainers.model.ClientBookListMappingContainer;
import team.mediasoft.study.spocktestcontainers.repository.BookRepository;
import team.mediasoft.study.spocktestcontainers.repository.ClientRepository;
import team.mediasoft.study.spocktestcontainers.repository.RentalRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalService {

    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;
    private final ClientRepository clientRepository;
    private final RentalMapper rentalMapper;

    public void rentBook(Long bookId, Long clientId) {
        rentalRepository.findByBook_IdAndReturnTimeNotNull(bookId).ifPresent(r -> {
            throw new BookNotAvailableException("Book is not available");
        });

        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException("Book not found"));
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NotFoundException("Client not found"));
        Rental rental = new Rental();
        rental.setBook(book);
        rental.setClient(client);
        rental.setIssueTime(LocalDateTime.now());

        rentalRepository.save(rental);
    }

    public ClientBookList getClientActiveBooks(Long clientId) {
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new NotFoundException("Client not found"));
        List<Rental> rentals = rentalRepository.findByClient_IdAndReturnTimeIsNull(clientId);
        return rentalMapper.rentalsToClientBookList(new ClientBookListMappingContainer(client, rentals));
    }
}
