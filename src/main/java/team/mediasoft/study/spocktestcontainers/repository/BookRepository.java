package team.mediasoft.study.spocktestcontainers.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team.mediasoft.study.spocktestcontainers.entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
