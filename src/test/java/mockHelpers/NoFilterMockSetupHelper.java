package mockHelpers;

import dto.CarDto;
import filter.CarFilter;
import service.CarService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Helper class for configuring Mockito mocks for {@link CarService} to simulate no-filter behavior.
 */
public class NoFilterMockSetupHelper extends BaseMockSetupHelper {
    public NoFilterMockSetupHelper(CarService carService, List<CarDto> testData) {
        super(carService, testData);
    }

    public void mockNoFilter() {
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData);
    }
}
