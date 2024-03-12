package team.mediasoft.study.spocktestcontainers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import team.mediasoft.study.spocktestcontainers.entity.Book;
import team.mediasoft.study.spocktestcontainers.entity.Client;
import team.mediasoft.study.spocktestcontainers.model.ClientDebt;
import team.mediasoft.study.spocktestcontainers.repository.ClientStatisticRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Testcontainers
@JdbcTest
@Import(ClientStatisticRepository.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClientStatisticRepositoryTest {

    private static final Book PUSHKIN = new Book(1L, "123456789012", "Евгений Онегин", "Пушкин", 2023);

    private static final Client IVANOV = new Client(1L, "Иванов");

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:14-alpine")
            .withDatabaseName("rentals")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private JdbcTemplate template;

    @Autowired
    private ClientStatisticRepository statisticRepository;

    @BeforeEach
    void setUp() {
        template.execute("truncate rentals, books, clients");
        template.execute(String.format("insert into books(id, isbn, title, author, publishing_year) values(%d, '%s', '%s', '%s', %d)",
                PUSHKIN.getId(), PUSHKIN.getIsbn(), PUSHKIN.getTitle(), PUSHKIN.getAuthor(), PUSHKIN.getPublishingYear()));
        template.execute(String.format("insert into clients(id, fullname) values(%d, '%s')", IVANOV.getId(), IVANOV.getFullname()));
    }

    @Test
    void shouldFindDebtors() {
        template.execute(String.format("insert into rentals(client_id, book_id, issue_time, return_time) values(%d, %d, '%3$tF %3tT', null)",
                IVANOV.getId(), PUSHKIN.getId(), LocalDateTime.now().minusDays(3)));
        ClientDebt expected = new ClientDebt(IVANOV.getId(), IVANOV.getFullname(), 1, LocalDate.now().minusDays(3));
        List<ClientDebt> actualList = statisticRepository.findDebtors(LocalDate.now());
        Assertions.assertEquals(1, actualList.size());
        Assertions.assertEquals(expected, actualList.get(0));
    }
}
