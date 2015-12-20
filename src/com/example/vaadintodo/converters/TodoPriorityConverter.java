package com.example.vaadintodo.converters;

import java.util.Locale;

import com.example.vaadintodo.constants.TodoPriorities;
import com.vaadin.data.util.converter.Converter;

@SuppressWarnings("serial")
public class TodoPriorityConverter implements Converter<Object, Integer> {

	@Override
	public Integer convertToModel(Object value, Class<? extends Integer> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if(value == null) {
			return null;
		}
		if(value.getClass().equals(TodoPriorities.class)) {
			return ((TodoPriorities)value).getId();
		}
		return value == null ? null : TodoPriorities.getByName(value.toString()).getId();
	}

	@Override
	public String convertToPresentation(Integer value, Class<? extends Object> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		return value == null ? null : TodoPriorities.getById((Integer)value).getName();
	}

	@Override
	public Class<Integer> getModelType() {
		return Integer.class;
	}

	@Override
	public Class<Object> getPresentationType() {
		return Object.class;
	}

}
