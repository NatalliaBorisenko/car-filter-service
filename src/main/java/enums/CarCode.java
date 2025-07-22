package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum representing car codes used in test data.
 */
@Getter
@AllArgsConstructor
public enum CarCode {
    CODE1("CODE1"),
    CODE2("CODE2"),
    CODE3("CODE3"),
    CODE4("CODE4"),
    CODE5("CODE5");

    private final String value;
}
