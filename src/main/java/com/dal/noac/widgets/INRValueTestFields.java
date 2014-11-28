package com.dal.noac.widgets;

import java.util.ArrayList;

import com.dal.noac.util.INRValueTester;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class INRValueTestFields extends CustomField<INRValueTester> {
	
	private FieldGroup fieldgroup;

	@Override
	protected Component initContent() {
		HorizontalLayout container = new HorizontalLayout();
		
		fieldgroup = new BeanFieldGroup<INRValueTester>(INRValueTester.class);
		TextField inrField1 = new TextField("Result 1");
		TextField inrField2 = new TextField("Result 2");
		TextField inrField3 = new TextField("Result 3");
		TextField inrField4 = new TextField("Result 4");
		TextField inrField5 = new TextField("Result 5");
		TextField inrField6 = new TextField("Result 6");
		TextField inrField7 = new TextField("Result 7");
		TextField inrField8 = new TextField("Result 8");
		
		container.addComponent(inrField1);
		container.addComponent(inrField2);
		container.addComponent(inrField3);
		container.addComponent(inrField4);
		container.addComponent(inrField5);
		container.addComponent(inrField6);
		container.addComponent(inrField7);
		container.addComponent(inrField8);
		
		fieldgroup.bind(inrField1, "inrValue1");
		fieldgroup.bind(inrField2, "inrValue2");
		fieldgroup.bind(inrField3, "inrValue3");
		fieldgroup.bind(inrField4, "inrValue4");
		fieldgroup.bind(inrField5, "inrValue5");
		fieldgroup.bind(inrField6, "inrValue6");
		fieldgroup.bind(inrField7, "inrValue7");
		fieldgroup.bind(inrField8, "inrValue8");
		return container;
	}

	@Override
	public Class<INRValueTester> getType() {
		// TODO Auto-generated method stub
		return INRValueTester.class;
	}

}
