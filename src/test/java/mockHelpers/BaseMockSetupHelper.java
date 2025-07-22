package mockHelpers;

import dto.CarDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import service.CarService;

import java.util.List;

/**
 * Base helper class for configuring Mockito mocks for {@link CarService}.
 */
@Getter
@RequiredArgsConstructor
public abstract class BaseMockSetupHelper {
    protected final CarService carService;
    protected final List<CarDto> testData;
}
