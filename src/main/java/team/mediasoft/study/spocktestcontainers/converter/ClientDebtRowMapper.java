package team.mediasoft.study.spocktestcontainers.converter;

import lombok.experimental.UtilityClass;
import team.mediasoft.study.spocktestcontainers.model.ClientDebt;

import java.sql.Timestamp;
import java.util.Map;

import static team.mediasoft.study.spocktestcontainers.repository.ClientStatisticRepository.BOOK_COUNT_FIELD;
import static team.mediasoft.study.spocktestcontainers.repository.ClientStatisticRepository.CLIENT_ID_FIELD;
import static team.mediasoft.study.spocktestcontainers.repository.ClientStatisticRepository.CLIENT_NAME_FIELD;
import static team.mediasoft.study.spocktestcontainers.repository.ClientStatisticRepository.DEBT_DATE_FIELD;

@UtilityClass
public class ClientDebtRowMapper {

    public static ClientDebt mapRow(Map<String, Object> row) {
        return new ClientDebt(
                row.containsKey(CLIENT_ID_FIELD) ? (Long) row.get(CLIENT_ID_FIELD) : null,
                row.containsKey(CLIENT_NAME_FIELD) ? (String) row.get(CLIENT_NAME_FIELD) : null,
                row.containsKey(BOOK_COUNT_FIELD) ? ((Long) row.get(BOOK_COUNT_FIELD)).intValue() : null,
                row.containsKey(DEBT_DATE_FIELD)
                        ? ((Timestamp) row.get(DEBT_DATE_FIELD)).toLocalDateTime().toLocalDate()
                        : null
        );
    }
}
