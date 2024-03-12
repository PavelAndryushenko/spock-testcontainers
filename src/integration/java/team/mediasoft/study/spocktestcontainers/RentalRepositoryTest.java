package team.mediasoft.study.spocktestcontainers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import team.mediasoft.study.spocktestcontainers.entity.Book;
import team.mediasoft.study.spocktestcontainers.entity.Client;
import team.mediasoft.study.spocktestcontainers.entity.Rental;
import team.mediasoft.study.spocktestcontainers.repository.BookRepository;
import team.mediasoft.study.spocktestcontainers.repository.ClientRepository;
import team.mediasoft.study.spocktestcontainers.repository.RentalRepository;

import java.time.LocalDateTime;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
public class RentalRepositoryTest {

    private static final Book PUSHKIN = new Book(1L, "123456789012", "Евгений Онегин", "Пушкин", 2023);
    private static final Book GOGOL = new Book(2L, "012345678901", "Невский Проспект", "Гоголь", 2020);

    private static final Client IVANOV = new Client(1L, "Иванов");

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("rentals")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        rentalRepository.deleteAll();
        bookRepository.deleteAll();
        clientRepository.deleteAll();

        bookRepository.save(PUSHKIN);
        bookRepository.save(GOGOL);
        clientRepository.save(IVANOV);
        rentalRepository.save(new Rental(1L, PUSHKIN, IVANOV, LocalDateTime.now(), LocalDateTime.now()));
        rentalRepository.save(new Rental(2L, GOGOL, IVANOV, LocalDateTime.now(), null));
    }

    @Test
    void shouldFindByBookIdAndReturnTimeNotNull() {
        Rental actual = rentalRepository.findByBook_IdAndReturnTimeNotNull(PUSHKIN.getId()).orElseThrow();
        Assertions.assertEquals(IVANOV, actual.getClient());
    }
}
