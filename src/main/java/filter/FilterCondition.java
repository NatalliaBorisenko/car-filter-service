package filter;

import enums.FilterType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * Represents a filter condition for string-based fields
 */
@Getter
@Builder
@AllArgsConstructor
public class FilterCondition {
    private FilterType type;
    private String value;
    private List<String> values;
}
