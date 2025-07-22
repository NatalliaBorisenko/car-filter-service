package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing car names used in test data.
 */
@Getter
@AllArgsConstructor
public enum CarName {
    TOYOTA_CAMRY("Toyota Camry"),
    HONDA_CIVIC("Honda Civic"),
    TESLA_MODEL_3("Tesla Model 3"),
    BMW_X5("BMW X5"),
    FORD_FOCUS("Ford Focus");

    private final String value;
}
