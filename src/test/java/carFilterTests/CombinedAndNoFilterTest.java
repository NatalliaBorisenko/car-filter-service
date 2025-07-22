package carFilterTests;

import dto.CarDto;
import enums.EngineType;
import enums.FilterType;
import enums.SteeringWheelSide;
import filter.CarFilter;
import filter.FilterCondition;
import mockHelpers.BaseMockSetupHelper;
import mockHelpers.CombinedFilterMockSetupHelper;
import mockHelpers.NoFilterMockSetupHelper;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CombinedAndNoFilterTest extends BaseCarFilterTest<BaseMockSetupHelper> {
    @Override
    protected BaseMockSetupHelper createMockSetupHelper() {
        return new CombinedFilterMockSetupHelper(carService, testData);
    }

    @Test(
            testName = "Verify combined EQUALS filters for engine_type=GAS and steering_wheel_side=LEFT return correct cars",
            description = "Verify that combined filters for engine_type and steering_wheel_side return the correct cars",
            groups = {"combined", "positive"})
    public void combinedFilterTest() {
        ((CombinedFilterMockSetupHelper) mockSetupHelper).mockCombinedFilters();
        List<CarDto> expectedCars = testData.stream()
                .filter(car -> EngineType.GAS == car.getEngineType() &&
                        SteeringWheelSide.LEFT == car.getSteeringWheelSide())
                .toList();
        CarFilter filter = CarFilter.builder()
                .engineTypeFilter(FilterCondition.builder()
                        .type(FilterType.EQUALS)
                        .value(EngineType.GAS.getValue())
                        .build())
                .steeringWheelSideFilter(FilterCondition.builder()
                        .type(FilterType.EQUALS)
                        .value(SteeringWheelSide.LEFT.getValue())
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d cars for combined filter", expectedCars.size())
                .hasSize(expectedCars.size());
        assertThat(result)
                .as("All cars should have engine type %s and steering wheel side %s",
                        EngineType.GAS.getValue(), SteeringWheelSide.LEFT.getValue())
                .allMatch(car -> EngineType.GAS == car.getEngineType() &&
                        SteeringWheelSide.LEFT == car.getSteeringWheelSide());
    }

    @Test(
            testName = "Verify empty filter returns all cars",
            description = "Verify that no filters return all cars",
            groups = {"no_filter", "positive"})
    public void noFilterTest() {
        NoFilterMockSetupHelper noFilterHelper = new NoFilterMockSetupHelper(carService, testData);
        noFilterHelper.mockNoFilter();

        CarFilter filter = CarFilter.builder().build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return all %d cars when no filters are applied", testData.size())
                .hasSize(testData.size());
        assertThat(result)
                .as("Should contain all cars")
                .containsExactlyInAnyOrderElementsOf(testData);
    }
}


