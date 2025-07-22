package filter;

import enums.DateFilterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Represents a filter condition for date-based fields
 */
@Getter
@Builder
@AllArgsConstructor
public class DateFilterCondition {
    private DateFilterType type;
    private LocalDate value;
}
