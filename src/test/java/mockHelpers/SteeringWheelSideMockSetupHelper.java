package mockHelpers;

import dto.CarDto;
import enums.SteeringWheelSide;
import filter.CarFilter;
import service.CarService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Helper class for configuring Mockito mocks for {@link CarService} to simulate steering_wheel_side filtering behavior
 */
public class SteeringWheelSideMockSetupHelper extends BaseMockSetupHelper {
    public SteeringWheelSideMockSetupHelper(CarService carService, List<CarDto> testData) {
        super(carService, testData);
    }

    public void mockSteeringWheelSideEqualsLeft() {
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> SteeringWheelSide.LEFT == car.getSteeringWheelSide())
                .toList());
    }

    public void mockSteeringWheelSideNotEqualsRight() {
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> SteeringWheelSide.RIGHT != car.getSteeringWheelSide())
                .toList());
    }
}
