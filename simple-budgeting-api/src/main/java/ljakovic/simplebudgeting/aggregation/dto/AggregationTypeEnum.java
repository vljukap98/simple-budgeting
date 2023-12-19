package ljakovic.simplebudgeting.aggregation.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AggregationTypeEnum {
    MONTHLY("Monthly"),
    YEARLY("Yearly");

    private final String label;

    AggregationTypeEnum(String label) {
        this.label = label;
    }

    @JsonValue
    public String getLabel() {
        return label;
    }
}
