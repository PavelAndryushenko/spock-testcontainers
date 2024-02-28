package team.mediasoft.study.spocktestcontainers.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.mediasoft.study.spocktestcontainers.entity.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {
}
