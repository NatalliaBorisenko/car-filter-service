package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing engine types used in test data.
 */
@Getter
@AllArgsConstructor
public enum EngineType {
    BENZIN("Бензин"),
    DIESEL("Дизель"),
    ELECTRO("Электро"),
    GAS("Газ");

    private final String value;
}
