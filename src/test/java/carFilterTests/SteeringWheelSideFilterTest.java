package carFilterTests;

import dto.CarDto;
import enums.FilterType;
import enums.SteeringWheelSide;
import filter.CarFilter;
import filter.FilterCondition;
import mockHelpers.SteeringWheelSideMockSetupHelper;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SteeringWheelSideFilterTest extends BaseCarFilterTest<SteeringWheelSideMockSetupHelper> {
    @Override
    protected SteeringWheelSideMockSetupHelper createMockSetupHelper() {
        return new SteeringWheelSideMockSetupHelper(carService, testData);
    }

    @Test(
            testName = "Verify EQUALS filter for steering_wheel_side=LEFT returns correct cars",
            description = "Verify that the EQUALS filter for steering_wheel_side returns the correct cars",
            groups = {"steering_wheel_side", "positive"})
    public void steeringWheelSideEqualsFilterTest() {
        mockSetupHelper.mockSteeringWheelSideEqualsLeft();
        List<CarDto> expectedCars = testData.stream()
                .filter(car -> SteeringWheelSide.LEFT == car.getSteeringWheelSide())
                .toList();
        CarFilter filter = CarFilter.builder()
                .steeringWheelSideFilter(FilterCondition.builder()
                        .type(FilterType.EQUALS)
                        .value(SteeringWheelSide.LEFT.getValue())
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d cars for steering wheel side %s", expectedCars.size(), SteeringWheelSide.LEFT.getValue())
                .hasSize(expectedCars.size());
        assertThat(result)
                .as("All cars should have steering wheel side %s", SteeringWheelSide.LEFT.getValue())
                .extracting(CarDto::getSteeringWheelSide)
                .containsOnly(SteeringWheelSide.LEFT);
    }

    @Test(
            testName = "Verify NOT_EQUALS filter for steering_wheel_side!=RIGHT returns correct cars",
            description = "Verify that the NOT_EQUALS filter for steering_wheel_side excludes the specified cars",
            groups = {"steering_wheel_side", "positive"})
    public void steeringWheelSideNotEqualsFilterTest() {
        mockSetupHelper.mockSteeringWheelSideNotEqualsRight();
        List<CarDto> expectedCars = testData.stream()
                .filter(car -> SteeringWheelSide.RIGHT != car.getSteeringWheelSide())
                .toList();
        CarFilter filter = CarFilter.builder()
                .steeringWheelSideFilter(FilterCondition.builder()
                        .type(FilterType.NOT_EQUALS)
                        .value(SteeringWheelSide.RIGHT.getValue())
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d cars after excluding steering wheel side %s", expectedCars.size(), SteeringWheelSide.RIGHT.getValue())
                .hasSize(expectedCars.size());
        assertThat(result)
                .as("Should not contain cars with steering wheel side %s", SteeringWheelSide.RIGHT.getValue())
                .noneMatch(car -> SteeringWheelSide.RIGHT == car.getSteeringWheelSide());
    }
}
