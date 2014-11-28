package com.dal.noac.util;

import java.util.List;
import java.util.Locale;

import com.vaadin.data.util.converter.Converter;

public class INRTestValueConverter implements Converter<INRValueTester, String> {

	@Override
	public String convertToModel(INRValueTester value,
			Class<? extends String> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		if(value != null){
			List<Double> inrvalues = value.getInrValues();
			int inRangeCount = 0;
			int i = 0;
			while(i < inrvalues.size()){
				double currentValue = inrvalues.get(i);
				if(currentValue == 0.0)
					break;
				if(currentValue >= INRValueTester.minVal && currentValue <= INRValueTester.maxVal)
					inRangeCount++;
				i++;
			}
			int percentage = (int)(i*(35.0f/100.0f));
			return Boolean.toString(inRangeCount >= percentage);
		} else{
			return "";
		}
	}

	@Override
	public INRValueTester convertToPresentation(String value,
			Class<? extends INRValueTester> targetType, Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		// TODO Auto-generated method stub
		return new INRValueTester();
	}

	@Override
	public Class<String> getModelType() {
		// TODO Auto-generated method stub
		return String.class;
	}

	@Override
	public Class<INRValueTester> getPresentationType() {
		// TODO Auto-generated method stub
		return INRValueTester.class;
	}

}
