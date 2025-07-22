package mockHelpers;

import dto.CarDto;
import filter.CarFilter;
import service.CarService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Helper class for configuring Mockito mocks for {@link CarService} to simulate delivery_date filtering behavior.
 */
public class DeliveryDateMockSetupHelper extends BaseMockSetupHelper {
    public DeliveryDateMockSetupHelper(CarService carService, List<CarDto> testData) {
        super(carService, testData);
    }

    public void mockDeliveryDateEqualsMock() {
        LocalDate equalsDate = LocalDate.parse("2024-07-01");
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> car.getDeliveryDate().equals(equalsDate))
                .collect(Collectors.toList()));
    }

    public void mockDeliveryDateGreaterThan() {
        LocalDate greaterThanDate = LocalDate.parse("2022-07-01");
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> car.getDeliveryDate() != null && car.getDeliveryDate().isAfter(greaterThanDate))
                .collect(Collectors.toList()));
    }

    public void mockDeliveryDateCreatedLessThan() {
        LocalDate equalsDate = LocalDate.parse("2024-07-01");
        when(carService.filterCars(any(CarFilter.class))).thenReturn(testData.stream()
                .filter(car -> car.getDeliveryDate().isBefore(equalsDate))
                .collect(Collectors.toList()));
    }
}
