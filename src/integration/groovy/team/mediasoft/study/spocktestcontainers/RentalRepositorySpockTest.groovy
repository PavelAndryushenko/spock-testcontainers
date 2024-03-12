package team.mediasoft.study.spocktestcontainers


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification
import team.mediasoft.study.spocktestcontainers.entity.Book
import team.mediasoft.study.spocktestcontainers.entity.Client
import team.mediasoft.study.spocktestcontainers.entity.Rental
import team.mediasoft.study.spocktestcontainers.repository.BookRepository
import team.mediasoft.study.spocktestcontainers.repository.ClientRepository
import team.mediasoft.study.spocktestcontainers.repository.RentalRepository

import java.time.LocalDateTime

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class RentalRepositorySpockTest extends Specification {

    private static final Book PUSHKIN = new Book(1L, "123456789012", "Евгений Онегин", "Пушкин", 2023)
    private static final Book GOGOL = new Book(2L, "012345678901", "Невский Проспект", "Гоголь", 2020)

    private static final Client IVANOV = new Client(1L, "Иванов")

    @Autowired
    private RentalRepository rentalRepository

    @Autowired
    private BookRepository bookRepository

    @Autowired
    private ClientRepository clientRepository

    @Shared
    PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("rentals")
            .withUsername("postgres")
            .withPassword("postgres")

    def setupSpec() {
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl())
        System.setProperty("spring.datasource.username", postgres.getUsername())
        System.setProperty("spring.datasource.password", postgres.getPassword())
    }

    def setup() {
        bookRepository.save(PUSHKIN)
        bookRepository.save(GOGOL)
        clientRepository.save(IVANOV)
        rentalRepository.save(new Rental(1L, PUSHKIN, IVANOV, LocalDateTime.now(), LocalDateTime.now()))
        rentalRepository.save(new Rental(2L, GOGOL, IVANOV, LocalDateTime.now(), null))
    }

    def cleanup() {
        rentalRepository.deleteAll()
        bookRepository.deleteAll()
        clientRepository.deleteAll()
    }

    def "should find By bookId and returnTime not null" () {
        when:
        def actual = rentalRepository.findByBook_IdAndReturnTimeNotNull(PUSHKIN.getId()).orElseThrow()

        then:
        actual.getClient() == IVANOV
    }
}
