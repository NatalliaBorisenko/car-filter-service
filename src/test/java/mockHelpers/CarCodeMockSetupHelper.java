package mockHelpers;

import dto.CarDto;
import enums.CarCode;
import filter.CarFilter;
import service.CarService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Helper class for configuring Mockito mocks for {@link CarService} to simulate car_code filtering behavior
 */
public class CarCodeMockSetupHelper extends BaseMockSetupHelper {
    public CarCodeMockSetupHelper(CarService carService, List<CarDto> testData) {
        super(carService, testData);
    }

    public void mockCarCodeEqualsCode1() {
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> CarCode.CODE1 == car.getCarCode())
                .collect(Collectors.toList()));
    }

    public void mockCarCodeNotEqualsCode3() {
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> CarCode.CODE3 != car.getCarCode())
                .collect(Collectors.toList()));
    }

    public void mockCarCodeContainsCode() {
        String containsString = "CODE";
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> car.getCarCode() != null && car.getCarCode().getValue().contains(containsString))
                .collect(Collectors.toList()));
    }

    public void mockCarCodeNotContainsCarCodes() {
        String notContains = "car_codes";
        when(carService.filterCars((any(CarFilter.class)))).thenReturn(testData.stream()
                .filter(car -> car.getCarCode() == null || !car.getCarCode().getValue().contains(notContains))
                .collect(Collectors.toList()));
    }

    public void mockCarCodeInCode1Code3() {
        List<String> carCodesIn = Arrays.asList(CarCode.CODE1.getValue(), CarCode.CODE3.getValue());
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> carCodesIn.contains(car.getCarCode().getValue()))
                .collect(Collectors.toList()));
    }
}
