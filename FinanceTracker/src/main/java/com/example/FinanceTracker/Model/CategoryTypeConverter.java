package com.example.FinanceTracker.Model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)  // automatically applied to all CategoryType fields
public class CategoryTypeConverter implements AttributeConverter<CategoryType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(CategoryType attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public CategoryType convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : CategoryType.fromCode(dbData);
    }
}