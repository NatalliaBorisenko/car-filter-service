package dto;

import enums.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Data Transfer Object (DTO) for representing car data from the {@code car} and {@code car_equipment} tables
 */
@Getter
@Builder
@AllArgsConstructor
public class CarDto {
    private final CarCode carCode;
    private final CarName carName;
    private final EngineType engineType;
    private final TireType tireType;
    private final SteeringWheelSide steeringWheelSide;
    private final LocalDate deliveryDate;
}
