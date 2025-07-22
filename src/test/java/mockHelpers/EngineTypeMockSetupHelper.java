package mockHelpers;

import dto.CarDto;
import enums.EngineType;
import filter.CarFilter;
import service.CarService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Helper class for configuring Mockito mocks for {@link CarService} to simulate engine_type filtering behavior
 */
public class EngineTypeMockSetupHelper extends BaseMockSetupHelper {
    public EngineTypeMockSetupHelper(CarService carService, List<CarDto> testData) {
        super(carService, testData);
    }

    public void mockEngineTypeEqualsBenzin() {
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> EngineType.BENZIN == car.getEngineType())
                .collect(Collectors.toList()));
    }

    public void mockEngineTypeNotEqualsDiesel() {
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> EngineType.DIESEL != car.getEngineType())
                .collect(Collectors.toList()));
    }

    public void mockEngineTypeContainsElectro() {
        String contains = "Электро";
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> car.getEngineType() != null && car.getEngineType().getValue().contains(contains))
                .collect(Collectors.toList()));
    }

    public void mockEngineTypeNotContainsNewType() {
        String notContains = "Новый тип";
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> car.getEngineType() == null || !car.getEngineType().getValue().contains(notContains))
                .collect(Collectors.toList()));
    }

    public void mockEngineTypeInElectroGasDiesel() {
        List<String> engineTypesIn = Arrays.asList(EngineType.ELECTRO.getValue(), EngineType.GAS.getValue(),
                EngineType.DIESEL.getValue());
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> engineTypesIn.contains(car.getEngineType().getValue()))
                .collect(Collectors.toList()));
    }
}