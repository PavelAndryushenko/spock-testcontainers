package team.mediasoft.study.spocktestcontainers.configuration;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import team.mediasoft.study.spocktestcontainers.repository.BookRepository;
import team.mediasoft.study.spocktestcontainers.repository.ClientRepository;
import team.mediasoft.study.spocktestcontainers.repository.RentalRepository;

@Configuration
public class DatabaseConfiguration {

    @Bean
    @Primary
    public RentalRepository mockRentalRepository() {
        return Mockito.mock(RentalRepository.class);
    }

    @Bean
    @Primary
    public BookRepository mockBookRepository() {
        return Mockito.mock(BookRepository.class);
    }

    @Bean
    @Primary
    public ClientRepository mockClientRepository() {
        return Mockito.mock(ClientRepository.class);
    }
}
