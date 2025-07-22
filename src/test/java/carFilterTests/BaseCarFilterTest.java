package carFilterTests;

import dto.CarDto;
import enums.*;
import mockHelpers.BaseMockSetupHelper;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import service.CarService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Base test class for car filter tests, providing common setup for {@link CarService} and test data.
 */
public abstract class BaseCarFilterTest<T extends BaseMockSetupHelper> {
    protected CarService carService;
    protected List<CarDto> testData;
    protected T mockSetupHelper;

    @BeforeMethod
    public void setUp() {
        carService = Mockito.mock(CarService.class);
        testData = Arrays.asList(
                CarDto.builder()
                        .carCode(CarCode.CODE1)
                        .carName(CarName.TOYOTA_CAMRY)
                        .engineType(EngineType.BENZIN)
                        .tireType(TireType.SUMMER)
                        .steeringWheelSide(SteeringWheelSide.LEFT)
                        .deliveryDate(LocalDate.parse("2023-01-15"))
                        .build(),
                CarDto.builder()
                        .carCode(CarCode.CODE2)
                        .carName(CarName.HONDA_CIVIC)
                        .engineType(EngineType.DIESEL)
                        .tireType(TireType.WINTER)
                        .steeringWheelSide(SteeringWheelSide.RIGHT)
                        .deliveryDate(LocalDate.parse("2023-06-20"))
                        .build(),
                CarDto.builder()
                        .carCode(CarCode.CODE3)
                        .carName(CarName.TESLA_MODEL_3)
                        .engineType(EngineType.ELECTRO)
                        .tireType(TireType.ALL_SEASON)
                        .steeringWheelSide(SteeringWheelSide.LEFT)
                        .deliveryDate(LocalDate.parse("2024-03-10"))
                        .build(),
                CarDto.builder()
                        .carCode(CarCode.CODE4)
                        .carName(CarName.BMW_X5)
                        .engineType(EngineType.BENZIN)
                        .tireType(TireType.SUMMER)
                        .steeringWheelSide(SteeringWheelSide.RIGHT)
                        .deliveryDate(LocalDate.parse("2022-11-05"))
                        .build(),
                CarDto.builder()
                        .carCode(CarCode.CODE5)
                        .carName(CarName.FORD_FOCUS)
                        .engineType(EngineType.GAS)
                        .tireType(TireType.WINTER)
                        .steeringWheelSide(SteeringWheelSide.LEFT)
                        .deliveryDate(LocalDate.parse("2024-07-01"))
                        .build()
        );
        mockSetupHelper = createMockSetupHelper();
    }

    protected abstract T createMockSetupHelper();
}
