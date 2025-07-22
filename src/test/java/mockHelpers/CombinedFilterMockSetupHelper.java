package mockHelpers;

import dto.CarDto;
import enums.EngineType;
import enums.SteeringWheelSide;
import filter.CarFilter;
import service.CarService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Helper class for configuring Mockito mocks for {@link CarService} to simulate combined filter behavior.
 */
public class CombinedFilterMockSetupHelper extends BaseMockSetupHelper {
    public CombinedFilterMockSetupHelper(CarService carService, List<CarDto> testData) {
        super(carService, testData);
    }

    public void mockCombinedFilters() {
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> EngineType.GAS == car.getEngineType() &&
                        SteeringWheelSide.LEFT == car.getSteeringWheelSide())
                .toList());
    }
}
