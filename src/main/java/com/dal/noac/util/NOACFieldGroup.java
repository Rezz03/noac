package com.dal.noac.util;

import java.util.Iterator;
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
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
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
	
	private Set<Section> sections;
	private FormLayout layout;
	private PropertysetItem itemSet;
	private int formId;
	
	public NOACFieldGroup(Noacform form){
		sections = form.getSections();
		layout = new FormLayout();
		itemSet = new PropertysetItem();
		formId = form.getId();
		
		Iterator<Section> formIterator = sections.iterator();
		while(formIterator.hasNext()){
			Section section = formIterator.next();
			addProperties(section.getNoacfields());			
		}
		this.setItemDataSource(itemSet);
		formIterator = sections.iterator();
		while(formIterator.hasNext()){
			Section section = formIterator.next();
			Set<Noacfield> sectionFields = section.getNoacfields();
			Panel sectionPanel = new Panel(section.getTitle());
			FormLayout panelLayout = new FormLayout();
			Iterator<Noacfield> fieldIterator = sectionFields.iterator();
			while(fieldIterator.hasNext()){
				Noacfield currentField = fieldIterator.next();
				panelLayout.addComponent(buildAndBindControl(currentField));
			}
			sectionPanel.setContent(panelLayout);
			layout.addComponent(sectionPanel);
		}
	}
	
	private void addProperties(Set<Noacfield> fields){
		Iterator<Noacfield> fieldIterator = fields.iterator();
		while(fieldIterator.hasNext()){
			Noacfield field = fieldIterator.next();
			String cleanFieldName = "NF" + field.getId();
			if(field.getValue() != null)
				itemSet.addItemProperty(cleanFieldName, new ObjectProperty<String>(field.getValue()));
			else
				itemSet.addItemProperty(cleanFieldName, new ObjectProperty<String>(""));
		}
	}
	
	private Field buildAndBindControl(Noacfield nfield){
		String inputType = nfield.getInputFormat();
		String cleanFieldName = "NF" + nfield.getId();
		if(inputType.equals("multiselect") || inputType.equals("select")){
			OptionGroup optiongroup = new OptionGroup(nfield.getLabel());
			Set<Noacfieldselectoptions> options = nfield.getNoacfieldselectoptionses();
			Iterator<Noacfieldselectoptions> optionIterator = options.iterator();
			while(optionIterator.hasNext()){
				optiongroup.addItem(optionIterator.next().getLabel());
			}
			if(inputType.equals("multiselect"))
				optiongroup.setMultiSelect(true);
			this.bind(optiongroup, cleanFieldName);
			return optiongroup;
		}
		else{
			TextField text = new TextField(nfield.getLabel());
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
