package team.mediasoft.study.spocktestcontainers

import org.assertj.core.api.Assertions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.JdbcTemplate
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Shared
import spock.lang.Specification
import team.mediasoft.study.spocktestcontainers.entity.Book
import team.mediasoft.study.spocktestcontainers.entity.Client
import team.mediasoft.study.spocktestcontainers.model.ClientDebt
import team.mediasoft.study.spocktestcontainers.repository.ClientStatisticRepository

import java.time.LocalDate
import java.time.LocalDateTime

@Testcontainers
@JdbcTest
@Import(ClientStatisticRepository)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientStatisticRepositorySpockTest extends Specification {

    private static final Book PUSHKIN = new Book(1L, "123456789012", "Евгений Онегин", "Пушкин", 2023)
    private static final Client IVANOV = new Client(1L, "Иванов")
    private static final ClientDebt IVANOV_DEBT = new ClientDebt(IVANOV.getId(), IVANOV.getFullname(), 1, LocalDate.now().minusDays(3))

    @Autowired
    private JdbcTemplate template

    @Autowired
    private ClientStatisticRepository statisticRepository

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
        template.execute(String.format("insert into books(id, isbn, title, author, publishing_year) values(%d, '%s', '%s', '%s', %d)",
                PUSHKIN.getId(), PUSHKIN.getIsbn(), PUSHKIN.getTitle(), PUSHKIN.getAuthor(), PUSHKIN.getPublishingYear()))
        template.execute(String.format("insert into clients(id, fullname) values(%d, '%s')", IVANOV.getId(), IVANOV.getFullname()))
    }

    def cleanup() {
        template.execute("truncate rentals, books, clients")
    }

    def "should find debtors"() {
        given:
        def sql = String.format("insert into rentals(client_id, book_id, issue_time, return_time) values(%d, %d, '%3\$tF %3\$tT', null)",
                IVANOV.getId(), PUSHKIN.getId(), LocalDateTime.now().minusDays(3))

        when:
        template.execute(sql)
        def actualList = statisticRepository.findDebtors(debtStartDate)

        then:
        Assertions.assertThat(result).containsExactlyInAnyOrderElementsOf(actualList)

        where:
        debtStartDate                       | result
        LocalDate.now()                     | List.of(IVANOV_DEBT)
        LocalDate.now().minusDays(4)        | List.of()
    }
}
