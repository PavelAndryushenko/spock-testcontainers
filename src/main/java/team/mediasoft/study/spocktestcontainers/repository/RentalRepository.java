package team.mediasoft.study.spocktestcontainers.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.mediasoft.study.spocktestcontainers.entity.Rental;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Long> {

    Optional<Rental> findByBook_IdAndReturnTimeNotNull(Long bookId);

    List<Rental> findByClient_IdAndReturnTimeIsNull(Long clientId);
}
