package filter;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a collection of filter conditions for filtering cars
 */
@Getter
@Builder
public class CarFilter {
    private FilterCondition carCodeFilter;
    private FilterCondition carNameFilter;
    private FilterCondition engineTypeFilter;
    private FilterCondition steeringWheelSideFilter;
    private DateFilterCondition deliveryDateFilter;
}
