package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing steering wheel side used in test data.
 */
@Getter
@AllArgsConstructor
public enum SteeringWheelSide {
    LEFT("L"),
    RIGHT("R");

    private final String value;
}
