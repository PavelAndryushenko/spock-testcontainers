package team.mediasoft.study.spocktestcontainers.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import team.mediasoft.study.spocktestcontainers.converter.ClientDebtRowMapper;
import team.mediasoft.study.spocktestcontainers.model.ClientDebt;

import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClientStatisticRepository {

    private static final String DEBT_START_DATE_PARAMETER = "debt_start_date";
    public static final String CLIENT_ID_FIELD = "client_id";
    public static final String CLIENT_NAME_FIELD = "client_name";
    public static final String BOOK_COUNT_FIELD = "book_count";
    public static final String DEBT_DATE_FIELD = "debt_date";

    private final static String FIND_DEBTORS_SQL = "select distinct "
            + "c.id as " + CLIENT_ID_FIELD + ", "
            + "c.fullname as " + CLIENT_NAME_FIELD + ", "
            + "count(*) over debt as " + BOOK_COUNT_FIELD + ", "
            + "first_value(r.issue_time) over debt as " + DEBT_DATE_FIELD + " "
            + "from clients c "
            + "inner join rentals r on c.id = r.client_id "
            + "where r.return_time is null and r.issue_time < :debt_start_date "
            + "window debt as (partition by c.id order by r.issue_time)";

    private final NamedParameterJdbcTemplate template;

    public List<ClientDebt> findDebtors(LocalDate debtStartDate) {
        List<Map<String, Object>> rows = template.queryForList(FIND_DEBTORS_SQL,
                new MapSqlParameterSource().addValue(DEBT_START_DATE_PARAMETER, debtStartDate, Types.DATE));
        return rows.stream().map(ClientDebtRowMapper::mapRow).collect(Collectors.toList());
    }
}
