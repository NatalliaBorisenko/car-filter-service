package carFilterTests;

import dto.CarDto;
import enums.EngineType;
import enums.FilterType;
import filter.CarFilter;
import filter.FilterCondition;
import mockHelpers.EngineTypeMockSetupHelper;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EngineTypeFilterTest extends BaseCarFilterTest<EngineTypeMockSetupHelper> {
    @Override
    protected EngineTypeMockSetupHelper createMockSetupHelper() {
        return new EngineTypeMockSetupHelper(carService, testData);
    }

    @Test(
            testName = "Verify EQUALS filter for engine_type=BENZIN returns correct cars",
            description = "Verify that the EQUALS filter for engine_type returns the correct cars",
            groups = {"engine_type", "positive"})
    public void engineTypeEqualsFilterTest() {
        mockSetupHelper.mockEngineTypeEqualsBenzin();
        List<CarDto> expectedCars = testData.stream()
                .filter(car -> EngineType.BENZIN == car.getEngineType())
                .toList();
        CarFilter filter = CarFilter.builder()
                .engineTypeFilter(FilterCondition.builder()
                        .type(FilterType.EQUALS)
                        .value(EngineType.BENZIN.getValue())
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d cars for engine type %s", expectedCars.size(), EngineType.BENZIN.getValue())
                .hasSize(expectedCars.size());
        assertThat(result)
                .as("All cars should have engine type %s", EngineType.BENZIN.getValue())
                .extracting(CarDto::getEngineType)
                .containsOnly(EngineType.BENZIN);
    }

    @Test(
            testName = "Verify NOT_EQUALS filter for engine_type=DIESEL excludes correct cars",
            description = "Verify that the NOT_EQUALS filter for engine_type excludes the specified cars",
            groups = {"engine_type", "positive"})
    public void engineTypeNotEqualsFilterTest() {
        mockSetupHelper.mockEngineTypeNotEqualsDiesel();
        List<CarDto> expectedCars = testData.stream()
                .filter(car -> EngineType.DIESEL != car.getEngineType())
                .toList();
        CarFilter filter = CarFilter.builder()
                .engineTypeFilter(FilterCondition.builder()
                        .type(FilterType.NOT_EQUALS)
                        .value(EngineType.DIESEL.getValue())
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d cars after excluding engine type %s", expectedCars.size(),
                        EngineType.DIESEL.getValue())
                .hasSize(expectedCars.size());
        assertThat(result)
                .as("Should not contain cars with engine type %s", EngineType.DIESEL.getValue())
                .noneMatch(car -> EngineType.DIESEL == car.getEngineType());
    }

    @Test(
            testName = "Verify CONTAINS filter for engine_type containing 'Электро' returns correct cars",
            description = "Verify that the CONTAINS filter for engine_type returns all matching cars",
            groups = {"engine_type", "positive"})
    public void engineTypeContainsFilterTest() {
        mockSetupHelper.mockEngineTypeContainsElectro();
        String contains = "Электро";
        int expectedCount = (int) testData.stream()
                .filter(car -> car.getEngineType() != null && car.getEngineType().getValue().contains(contains))
                .count();
        CarFilter filter = CarFilter.builder()
                .engineTypeFilter(FilterCondition.builder()
                        .type(FilterType.CONTAINS)
                        .value(contains)
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d cars containing '%s' in engine type", expectedCount, contains)
                .hasSize(expectedCount);
        assertThat(result)
                .as("All cars should have engine type containing '%s'", contains)
                .allMatch(car -> car.getEngineType() != null && car.getEngineType().getValue().contains(contains));
    }

    @Test(
            testName = "Verify NOT_CONTAINS filter for engine_type not containing 'Новый тип' returns correct cars",
            description = "Verify that the NOT_CONTAINS filter for engine_type returns all non-matching cars",
            groups = {"engine_type", "positive"})
    public void engineTypeNotContainsFilterTest() {
        mockSetupHelper.mockEngineTypeNotContainsNewType();
        String notContains = "Новый тип";
        int expectedCount = (int) testData.stream()
                .filter(car -> car.getEngineType() == null || !car.getEngineType().getValue().contains(notContains))
                .count();
        CarFilter filter = CarFilter.builder()
                .engineTypeFilter(FilterCondition.builder()
                        .type(FilterType.NOT_CONTAINS)
                        .value(notContains)
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d cars when none contain '%s'", expectedCount, notContains)
                .hasSize(expectedCount);
        assertThat(result)
                .as("No cars should have engine type containing '%s'", notContains)
                .allMatch(car -> car.getEngineType() == null || !car.getEngineType().getValue().contains(notContains));
    }

    @Test(
            testName = "Verify IN filter for engine_type in [ELECTRO, GAS, DIESEL] returns correct cars",
            description = "Verify that the IN filter for engine_type returns the specified cars",
            groups = {"engine_type", "positive"})
    public void engineTypeInFilterTest() {
        mockSetupHelper.mockEngineTypeInElectroGasDiesel();
        List<String> engineTypesIn = Arrays.asList(
                EngineType.ELECTRO.getValue(), EngineType.GAS.getValue(), EngineType.DIESEL.getValue());
        List<CarDto> expectedCars = testData.stream()
                .filter(car -> engineTypesIn.contains(car.getEngineType().getValue()))
                .toList();
        CarFilter filter = CarFilter.builder()
                .engineTypeFilter(FilterCondition.builder()
                        .type(FilterType.IN)
                        .values(engineTypesIn)
                        .build())
                .build();

        List<CarDto> result = carService.filterCars(filter);

        assertThat(result)
                .as("Should return %d matching cars", expectedCars.size())
                .hasSize(expectedCars.size());
        assertThat(result)
                .as("Should contain only cars with engine types %s", engineTypesIn)
                .extracting(car -> car.getEngineType().getValue())
                .containsExactlyInAnyOrderElementsOf(engineTypesIn);
    }
}