package team.mediasoft.study.spocktestcontainers.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import team.mediasoft.study.spocktestcontainers.entity.Rental;
import team.mediasoft.study.spocktestcontainers.model.ClientBookList;
import team.mediasoft.study.spocktestcontainers.model.ClientBookListItem;
import team.mediasoft.study.spocktestcontainers.model.ClientBookListMappingContainer;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface RentalMapper {

    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookName", source = "book.title")
    ClientBookListItem rentalToBookListItem(Rental rental);

    List<ClientBookListItem> rentalListToBookList(List<Rental> rentals);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientFullname", source = "client.fullname")
    @Mapping(target = "books", source = "rentals")
    ClientBookList rentalsToClientBookList(ClientBookListMappingContainer source);
}
