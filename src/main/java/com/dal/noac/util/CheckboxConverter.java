package com.dal.noac.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import com.vaadin.data.util.converter.Converter;

public class CheckboxConverter implements Converter<Object, String> {

	@Override
	public String convertToModel(Object value,
			Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		String result = "";
		Set<String> valueSet = (Set<String>) value;
		Iterator<String> valueIterator = valueSet.iterator();
		while(valueIterator.hasNext()){
			result += valueIterator.next();
			if(valueIterator.hasNext())
				result += ",";
		}
		return result;
	}

	@Override
	public Object convertToPresentation(String value,
			Class<? extends Object> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		String[] tokens = value.split(",");
		ArrayList<String> tokenList = new ArrayList<String>(Arrays.asList(tokens));
		return new HashSet(tokenList);
	}

	@Override
	public Class<String> getModelType() {
		return String.class;
	}

	@Override
	public Class<Object> getPresentationType() {
		return Object.class;
		
	}

}
