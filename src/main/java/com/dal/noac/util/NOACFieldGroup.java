package com.dal.noac.util;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import com.dal.noac.dao.NOACFormDAO;
import com.dal.noac.model.Noacfield;
import com.dal.noac.model.Noacfieldselectoptions;
import com.dal.noac.model.Noacform;
import com.dal.noac.model.Outcome;
import com.dal.noac.model.Section;
import com.dal.noac.widgets.INRValueTestFields;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.Page;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;

public class NOACFieldGroup extends FieldGroup {
	
	private List<Section> sections;
	private FormLayout layout;
	private PropertysetItem itemSet;
	private int formId;
	
	public NOACFieldGroup(Noacform form){
		sections = form.getSections();
		sections.size();
		layout = new FormLayout();
		itemSet = new PropertysetItem();
		formId = form.getId();
		
		Iterator<Section> formIterator = sections.iterator();
		
		while(formIterator.hasNext()){
			Section section = formIterator.next();
			List<Noacfield> propNoacFields = section.getNoacfields();
			propNoacFields.size();
			addProperties(propNoacFields);			
		}
		this.setItemDataSource(itemSet);
		formIterator = sections.iterator();
		while(formIterator.hasNext()){
			Section section = formIterator.next();
			List<Noacfield> sectionFields = section.getNoacfields();
			Panel sectionPanel = new Panel(section.getTitle());
			FormLayout panelLayout = new FormLayout();
			Iterator<Noacfield> fieldIterator = sectionFields.iterator();
			while(fieldIterator.hasNext()){
				Noacfield currentField = fieldIterator.next();
				if(currentField != null)
					panelLayout.addComponent(buildAndBindControl(currentField));
			}
			sectionPanel.setContent(panelLayout);
			layout.addComponent(sectionPanel);
		}
	}
	
	private void addProperties(List<Noacfield> fields){
		Iterator<Noacfield> fieldIterator = fields.iterator();
		while(fieldIterator.hasNext()){
			Noacfield field = fieldIterator.next();
			if(field != null){
			String cleanFieldName = "NF" + field.getId();
			itemSet.addItemProperty(cleanFieldName, new ObjectProperty<String>(""));
			}
		}
	}
	
	private Field buildAndBindControl(Noacfield nfield){
		String inputType = nfield.getInputFormat();
		String cleanFieldName = "NF" + nfield.getId();
		if(inputType.equals("multiselect") || inputType.equals("select") || inputType.equals("chad")){
			OptionGroup optiongroup = new OptionGroup(nfield.getLabel());
			List<Noacfieldselectoptions> options = nfield.getNoacfieldselectoptionses();
			Iterator<Noacfieldselectoptions> optionIterator = options.iterator();
			while(optionIterator.hasNext()){
				Noacfieldselectoptions option = optionIterator.next();
				optiongroup.addItem(option.getValue());
				optiongroup.setItemCaption(option.getValue(), option.getLabel());
			}
			optiongroup.addItem("''");
			optiongroup.setItemCaption("''", "None");
			Converter converter;
			if(inputType.equals("chad")){
				converter = new CrclConverter();
			}
			else{
				converter = new CheckboxConverter();
			}
			optiongroup.setConverter(converter);
			if(inputType.equals("multiselect")){
				optiongroup.setMultiSelect(true);
				
			}
			this.bind(optiongroup, cleanFieldName);
			return optiongroup;
		}
		else if(inputType.equals("inr")){
			INRValueTestFields valueFields = new INRValueTestFields();
			INRTestValueConverter converter = new INRTestValueConverter();
			valueFields.setConverter(converter);
			return valueFields;
			
		} 
		else{
			TextField text = new TextField(nfield.getLabel());
			text.setNullRepresentation("''");
			this.bind(text, cleanFieldName);
			return text;
		}
	}
	
	public FormLayout getFormLayout(){
		return layout;
	}
	
	public int getFormId(){
		return formId;
	}

}
