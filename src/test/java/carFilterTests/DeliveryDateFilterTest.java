package carFilterTests;

import dto.CarDto;
import enums.DateFilterType;
import filter.CarFilter;
import filter.DateFilterCondition;
import mockHelpers.DeliveryDateMockSetupHelper;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DeliveryDateFilterTest extends BaseCarFilterTest<DeliveryDateMockSetupHelper> {
    @Override
    protected DeliveryDateMockSetupHelper createMockSetupHelper() {
        return new DeliveryDateMockSetupHelper(carService, testData);
    }

    @Test(
            testName = "Verify EQUALS filter for delivery_date=2024-07-01 returns correct cars",
            description = "Verify that the EQUALS filter for delivery_date returns the correct cars",
            groups = {"delivery_date", "positive"})
    public void deliveryDateEqualsFilterTest() {
        mockSetupHelper.mockDeliveryDateEqualsMock();
        LocalDate equalsDate = LocalDate.parse("2024-07-01");
        List<CarDto> expectedCars = testData.stream()
                .filter(car -> equalsDate.equals(car.getDeliveryDate()))
                .toList();
        CarFilter filter = CarFilter.builder()
                .deliveryDateFilter(DateFilterCondition.builder()
                        .type(DateFilterType.EQUALS)
                        .value(equalsDate)
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d cars for delivery date %s", expectedCars.size(), equalsDate)
                .hasSize(expectedCars.size());
        assertThat(result)
                .as("All cars should have delivery date %s", equalsDate)
                .extracting(CarDto::getDeliveryDate)
                .containsOnly(equalsDate);
    }

    @Test(
            testName = "Verify GREATER_THAN filter for delivery_date>2022-07-01 returns correct cars",
            description = "Verify that the GREATER_THAN filter for delivery_date returns the correct cars",
            groups = {"delivery_date", "positive"})
    public void deliveryDateGreaterThanFilterTest() {
        mockSetupHelper.mockDeliveryDateGreaterThan();
        LocalDate greaterThanDate = LocalDate.parse("2022-07-01");
        testData.forEach(car -> assertThat(car.getDeliveryDate()).as(
                "Delivery date should not be null").isNotNull());
        List<CarDto> expectedCars = testData.stream()
                .filter(car -> car.getDeliveryDate().isAfter(greaterThanDate))
                .toList();
        CarFilter filter = CarFilter.builder()
                .deliveryDateFilter(DateFilterCondition.builder()
                        .type(DateFilterType.GREATER_THAN)
                        .value(greaterThanDate)
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d cars with delivery date after %s", expectedCars.size(), greaterThanDate)
                .hasSize(expectedCars.size());

        assertThat(result)
                .as("All cars should have delivery date after %s", greaterThanDate)
                .allMatch(car -> car.getDeliveryDate() != null && car.getDeliveryDate().isAfter(greaterThanDate));
    }

    @Test(
            testName = "Verify LESS_THAN filter for delivery_date<2024-07-01 returns correct cars",
            description = "Verify that the LESS_THAN filter for delivery_date returns the correct cars",
            groups = {"delivery_date", "positive"})
    public void deliveryDateLessThanFilterTest() {
        mockSetupHelper.mockDeliveryDateCreatedLessThan();
        LocalDate lessThanDate = LocalDate.parse("2024-07-01");
        List<CarDto> expectedCars = testData.stream()
                .filter(car -> car.getDeliveryDate() != null && car.getDeliveryDate().isBefore(lessThanDate))
                .toList();
        CarFilter filter = CarFilter.builder()
                .deliveryDateFilter(DateFilterCondition.builder()
                        .type(DateFilterType.LESS_THAN)
                        .value(lessThanDate)
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d cars with delivery date before %s", expectedCars.size(), lessThanDate)
                .hasSize(expectedCars.size());
        assertThat(result)
                .as("All cars should have delivery date before %s", lessThanDate)
                .allMatch(car -> car.getDeliveryDate().isBefore(lessThanDate));
    }
}
