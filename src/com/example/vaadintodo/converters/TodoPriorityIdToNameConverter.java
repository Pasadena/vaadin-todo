package com.example.vaadintodo.converters;

import java.util.Locale;

import com.example.vaadintodo.constants.TodoPriorities;
import com.vaadin.data.util.converter.Converter;

public class TodoPriorityIdToNameConverter implements Converter<String, Integer>{

	@Override
	public Integer convertToModel(String value, Class<? extends Integer> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		return TodoPriorities.getByName(value).getId();
	}

	@Override
	public String convertToPresentation(Integer value, Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		return TodoPriorities.getById(value).getName();
	}

	@Override
	public Class<Integer> getModelType() {
		return Integer.class;
	}

	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

}
