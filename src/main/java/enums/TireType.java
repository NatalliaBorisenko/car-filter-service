package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing tire types used in test data.
 */
@Getter
@AllArgsConstructor
public enum TireType {
    SUMMER("Лето"),
    WINTER("Зима"),
    ALL_SEASON("Всесезонная");

    private final String value;
}
