package com.dal.noac.util;

import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import com.vaadin.data.util.converter.Converter;

public class CrclConverter implements Converter<Object, Integer> {

	@Override
	public Integer convertToModel(Object value,
			Class<? extends Integer> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		int crcl = 0;
		Set<String> valueSet = (Set<String>) value;
		Iterator<String> valueIterator = valueSet.iterator();
		while(valueIterator.hasNext()){
			try{
				crcl += Integer.parseInt(valueIterator.next());
			} catch(Exception e){
				throw new ConversionException(e.getMessage());
			}
		}
		return crcl;
	}

	@Override
	public Object convertToPresentation(Integer value,
			Class<? extends Object> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class<Integer> getModelType() {
		// TODO Auto-generated method stub
		return Integer.class;
	}

	@Override
	public Class<Object> getPresentationType() {
		// TODO Auto-generated method stub
		return Object.class;
	}

}
