package ru.sibsutis.shop.core.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.sibsutis.shop.core.model.measurement.Quantity;

@Converter(autoApply = true)
public class QuantityConverter implements AttributeConverter<Quantity, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Quantity attribute) {
        return (attribute != null) ? attribute.getValue() : null;
    }

    @Override
    public Quantity convertToEntityAttribute(Integer dbData) {
        return (dbData != null) ? new Quantity(dbData) : null;
    }
}
