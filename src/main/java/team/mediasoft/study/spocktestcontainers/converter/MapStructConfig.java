package team.mediasoft.study.spocktestcontainers.converter;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public class MapStructConfig {
}
