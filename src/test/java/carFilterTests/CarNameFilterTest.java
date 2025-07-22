package carFilterTests;

import dto.CarDto;
import enums.CarName;
import enums.FilterType;
import filter.CarFilter;
import filter.FilterCondition;
import mockHelpers.CarNameMockSetupHelper;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CarNameFilterTest extends BaseCarFilterTest<CarNameMockSetupHelper> {
    @Override
    protected CarNameMockSetupHelper createMockSetupHelper() {
        return new CarNameMockSetupHelper(carService, testData);
    }

    @Test(
            testName = "Verify EQUALS filter for car_name=FORD_FOCUS returns correct car",
            description = "Verify that the EQUALS filter for car_name returns the correct car",
            groups = {"car_name", "positive"})
    public void carNameEqualsFilterTest() {
        mockSetupHelper.mockCarNameEqualsFordFocus();
        CarDto expectedCar = testData.stream()
                .filter(car -> CarName.FORD_FOCUS == car.getCarName())
                .findFirst()
                .orElseThrow(() ->
                        new AssertionError(
                                "Test data error: car with name " + CarName.FORD_FOCUS.getValue() + " not found"));
        CarFilter filter = CarFilter.builder()
                .carNameFilter(FilterCondition.builder()
                        .type(FilterType.EQUALS)
                        .value(CarName.FORD_FOCUS.getValue())
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return exactly one car for name %s", CarName.FORD_FOCUS.getValue())
                .hasSize(1);
        CarDto actualCar = result.get(0);
        assertThat(actualCar.getCarName())
                .as("Car name should match expected value")
                .isEqualTo(CarName.FORD_FOCUS);
        assertThat(actualCar.getCarCode())
                .as("Car code should match expected value")
                .isEqualTo(expectedCar.getCarCode());
    }

    @Test(
            testName = "Verify NOT_EQUALS filter for car_name=FORD_FOCUS excludes correct car",
            description = "Verify that the NOT_EQUALS filter for car_name excludes the specified car",
            groups = {"car_name", "positive"})
    public void carNameNotEqualsFilterTest() {
        mockSetupHelper.mockCarNameNotEqualsFordFocus();
        CarFilter filter = CarFilter.builder()
                .carNameFilter(FilterCondition.builder()
                        .type(FilterType.NOT_EQUALS)
                        .value(CarName.FORD_FOCUS.getValue())
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return 4 cars after excluding name %s", CarName.FORD_FOCUS.getValue())
                .hasSize(4);
        assertThat(result)
                .as("Should not contain car with name %s", CarName.FORD_FOCUS.getValue())
                .noneMatch(car -> CarName.FORD_FOCUS == car.getCarName());
    }

    @Test(
            testName = "Verify CONTAINS filter for 'Toyota' returns all matching cars",
            description = "Verify that the CONTAINS filter for car_name returns all matching cars",
            groups = {"car_name", "positive"})
    public void carNameContainsFilterTest() {
        mockSetupHelper.mockCarNameContainsToyota();
        String contains = "Toyota";
        int expectedCount = (int) testData.stream()
                .filter(car -> car.getCarName() != null && car.getCarName().getValue().contains(contains))
                .count();
        CarFilter filter = CarFilter.builder()
                .carNameFilter(FilterCondition.builder()
                        .type(FilterType.CONTAINS)
                        .value(contains)
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d cars containing '%s' in name", expectedCount, contains)
                .hasSize(expectedCount);
        assertThat(result)
                .as("All cars should have car_name containing '%s'", contains)
                .allMatch(car -> car.getCarName() != null && car.getCarName().getValue().contains(contains));
    }

    @Test(
            testName = "Verify NOT_CONTAINS filter for 'Geely' excludes matching cars",
            description = "Verify that the NOT_CONTAINS filter for car_name returns all non-matching cars",
            groups = {"car_name", "positive"})
    public void carNameNotContainsFilterTest() {
        mockSetupHelper.mockCarNameNotContainsGeely();
        String notContains = "Geely";
        int expectedCount = (int) testData.stream()
                .filter(car -> car.getCarName() == null || !car.getCarName().getValue().contains(notContains))
                .count();
        CarFilter filter = CarFilter.builder()
                .carNameFilter(FilterCondition.builder()
                        .type(FilterType.NOT_CONTAINS)
                        .value(notContains)
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d cars when none contain '%s'", expectedCount, notContains)
                .hasSize(expectedCount);
        assertThat(result)
                .as("No cars should have car_name containing '%s'", notContains)
                .allMatch(car -> car.getCarName() == null || !car.getCarName().getValue().contains(notContains));
    }

    @Test(
            testName = "Verify IN filter for car_name in [TOYOTA_CAMRY, BMW_X5, FORD_FOCUS] returns correct cars",
            description = "Verify that the IN filter for car_name returns the specified cars",
            groups = {"car_name", "positive"})
    public void carNameInFilterTest() {
        mockSetupHelper.mockCarNameInToyotaCamryBMWFordFocus();
        List<String> carNamesIn = Arrays.asList(
                CarName.TOYOTA_CAMRY.getValue(), CarName.BMW_X5.getValue(), CarName.FORD_FOCUS.getValue());
        List<CarDto> expectedCars = testData.stream()
                .filter(car -> carNamesIn.contains(car.getCarName().getValue()))
                .toList();
        CarFilter filter = CarFilter.builder()
                .carNameFilter(FilterCondition.builder()
                        .type(FilterType.IN)
                        .values(carNamesIn)
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d matching cars", expectedCars.size())
                .hasSize(expectedCars.size());

        assertThat(result)
                .as("Should contain only cars with names %s", carNamesIn)
                .extracting(car -> car.getCarName().getValue())
                .containsExactlyInAnyOrderElementsOf(carNamesIn);
    }
}
