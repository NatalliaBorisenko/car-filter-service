package carFilterTests;

import dto.CarDto;
import enums.CarCode;
import enums.FilterType;
import filter.CarFilter;
import filter.FilterCondition;
import mockHelpers.CarCodeMockSetupHelper;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CarCodeFilterTest extends BaseCarFilterTest<CarCodeMockSetupHelper> {
    @Override
    protected CarCodeMockSetupHelper createMockSetupHelper() {
        return new CarCodeMockSetupHelper(carService, testData);
    }

    @Test(
            testName = "Verify EQUALS filter for car_code=CODE1 returns correct car",
            description = "Verify that the EQUALS filter for car_code returns the correct car",
            groups = {"car_code", "positive"})
    public void carCodeEqualsFilterTest() {
        mockSetupHelper.mockCarCodeEqualsCode1();
        CarDto expectedCar = testData.stream()
                .filter(car -> CarCode.CODE1 == car.getCarCode())
                .findFirst()
                .orElseThrow(() ->
                        new AssertionError(
                                "Test data error: car with code " + CarCode.CODE1.getValue() + " not found"));
        CarFilter filter = CarFilter.builder()
                .carCodeFilter(FilterCondition.builder()
                        .type(FilterType.EQUALS)
                        .value(CarCode.CODE1.getValue())
                        .build())
                .build();


        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return exactly one car for code %s", CarCode.CODE1.getValue())
                .hasSize(1);

        CarDto actualCar = result.get(0);
        assertThat(actualCar.getCarCode())
                .as("Car code should match expected value")
                .isEqualTo(CarCode.CODE1);

        assertThat(actualCar.getCarName())
                .as("Car name should match expected value")
                .isEqualTo(expectedCar.getCarName());
    }

    @Test(
            testName = "Verify NOT_EQUALS filter for car_code=CODE3 excludes correct car",
            description = "Verify that the NOT_EQUALS filter for car_code excludes the specified car",
            groups = {"car_code", "positive"})
    public void carCodeNotEqualsFilterTest() {
        mockSetupHelper.mockCarCodeNotEqualsCode3();
        CarFilter filter = CarFilter.builder()
                .carCodeFilter(FilterCondition.builder()
                        .type(FilterType.NOT_EQUALS)
                        .value(CarCode.CODE3.getValue())
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return 4 cars after excluding code %s", CarCode.CODE3.getValue())
                .hasSize(4);
        assertThat(result)
                .as("Should not contain car with code %s", CarCode.CODE3.getValue())
                .noneMatch(car -> CarCode.CODE3 == car.getCarCode());
    }

    @Test(
            testName = "Verify CONTAINS filter for 'CODE' returns all matching cars",
            description = "Verify that the CONTAINS filter for car_code returns all matching cars",
            groups = {"car_code", "positive"})
    public void carCodeContainsFilterTest() {
        mockSetupHelper.mockCarCodeContainsCode();
        String contains = "CODE";
        int expectedCount = (int) testData.stream()
                .filter(car -> car.getCarCode() != null && car.getCarCode().getValue().contains(contains))
                .count();
        CarFilter filter = CarFilter.builder()
                .carCodeFilter(FilterCondition.builder()
                        .type(FilterType.CONTAINS)
                        .value(contains)
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d cars containing '%s' in code", expectedCount, contains)
                .hasSize(expectedCount);
        assertThat(result)
                .as("All cars should have car_code containing '%s'", contains)
                .allMatch(car -> car.getCarCode() != null && car.getCarCode().getValue().contains(contains));
    }

    @Test(
            testName = "Verify NOT_CONTAINS filter for 'car_codes' excludes matching cars",
            description = "Verify that the NOT_CONTAINS filter for car_code returns all non-matching cars",
            groups = {"car_code", "positive"})
    public void carCodeNotContainsFilterTest() {
        mockSetupHelper.mockCarCodeNotContainsCarCodes();
        String notContains = "car_codes";
        int expectedCount = (int) testData.stream()
                .filter(car -> car.getCarCode() == null || !car.getCarCode().getValue().contains(notContains))
                .count();
        CarFilter filter = CarFilter.builder()
                .carCodeFilter(FilterCondition.builder()
                        .type(FilterType.NOT_CONTAINS)
                        .value(notContains)
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d cars when none contain '%s'", expectedCount, notContains)
                .hasSize(expectedCount);
        assertThat(result)
                .as("No cars should have car_code containing '%s'", notContains)
                .allMatch(car -> car.getCarCode() == null || !car.getCarCode().getValue().contains(notContains));
    }

    @Test(
            testName = "Verify IN filter for car_code in [CODE1, CODE3] returns correct cars",
            description = "Verify that the IN filter for car_code returns the specified cars",
            groups = {"car_code", "positive"})
    public void carCodeInFilterTest() {
        mockSetupHelper.mockCarCodeInCode1Code3();
        List<String> carCodesIn = Arrays.asList(CarCode.CODE1.getValue(), CarCode.CODE3.getValue());
        List<CarDto> expectedCars = testData.stream()
                .filter(car -> carCodesIn.contains(car.getCarCode().getValue()))
                .toList();
        CarFilter filter = CarFilter.builder()
                .carCodeFilter(FilterCondition.builder()
                        .type(FilterType.IN)
                        .values(carCodesIn)
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d matching cars", expectedCars.size())
                .hasSize(expectedCars.size());

        assertThat(result)
                .as("Should contain only cars with codes %s", carCodesIn)
                .extracting(car -> car.getCarCode().getValue())
                .containsExactlyInAnyOrderElementsOf(carCodesIn);
    }
}
