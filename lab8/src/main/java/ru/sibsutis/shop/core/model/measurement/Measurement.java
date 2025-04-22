package ru.sibsutis.shop.core.model.measurement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Measurement {
    private String name;
    private String symbol;
}
