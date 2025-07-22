package mockHelpers;

import dto.CarDto;
import enums.CarName;
import filter.CarFilter;
import service.CarService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Helper class for configuring Mockito mocks for {@link CarService} to simulate car_name filtering behavior
 */
public class CarNameMockSetupHelper extends BaseMockSetupHelper {
    public CarNameMockSetupHelper(CarService carService, List<CarDto> testData) {
        super(carService, testData);
    }

    public void mockCarNameEqualsFordFocus() {
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> CarName.FORD_FOCUS == car.getCarName())
                .collect(Collectors.toList()));
    }

    public void mockCarNameNotEqualsFordFocus() {
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> CarName.FORD_FOCUS != car.getCarName())
                .collect(Collectors.toList()));
    }

    public void mockCarNameContainsToyota() {
        String contains = "Toyota";
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> car.getCarName() != null && car.getCarName().getValue().contains(contains))
                .collect(Collectors.toList()));
    }

    public void mockCarNameNotContainsGeely() {
        String notContains = "Geely";
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> car.getCarName() == null || !car.getCarName().getValue().contains(notContains))
                .collect(Collectors.toList()));
    }

    public void mockCarNameInToyotaCamryBMWFordFocus() {
        List<String> carNamesIn = Arrays.asList(CarName.TOYOTA_CAMRY.getValue(), CarName.BMW_X5.getValue(),
                CarName.FORD_FOCUS.getValue());
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> carNamesIn.contains(car.getCarName().getValue()))
                .collect(Collectors.toList()));
    }
}