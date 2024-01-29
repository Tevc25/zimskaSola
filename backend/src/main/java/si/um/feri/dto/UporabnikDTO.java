package si.um.feri.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record UporabnikDTO (
        Long id,
        String email,
        String username,
        String geslo
) {}
