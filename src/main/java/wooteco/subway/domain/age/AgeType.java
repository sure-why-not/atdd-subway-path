package wooteco.subway.domain.age;

import java.util.Arrays;

public enum AgeType {

    BABY(1, 5),
    KIDS(6, 12),
    TEENAGER(13, 19),
    ADULT(20, Integer.MAX_VALUE);

    private final int minValue;
    private final int maxValue;

    AgeType(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public static AgeType from(int age) {
        return Arrays.stream(values())
                .filter(it -> it.isType(age))
                .findFirst()
                .orElseThrow();
    }

    private boolean isType(int age) {
        return age >= minValue && age <= maxValue;
    }
}
