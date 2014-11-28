package com.dal.noac.layout;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.dal.noac.model.Noacfield;
import com.dal.noac.model.Noacfieldselectoptions;
import com.dal.noac.model.Noacform;
import com.dal.noac.model.Section;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;

public class NOACFormSectionLayout extends Panel {
	
	private Section section;
	
	public NOACFormSectionLayout(Section aSection){
		section = aSection;
		this.setCaption(section.getTitle());
		build();
	}
	
	public void build(){
		FormLayout form = new FormLayout();
		List<Noacfield> fields = section.getNoacfields();
		Iterator<Noacfield> fieldIterator = fields.iterator();
		while(fieldIterator.hasNext()){
			Noacfield field = fieldIterator.next();
			String inputType = field.getInputFormat();
			if(inputType.equals("text")){
				TextField textfield = new TextField(field.getLabel());
				form.addComponent(textfield);
			} else{
				OptionGroup optiongroup = new OptionGroup(field.getLabel());
				List<Noacfieldselectoptions> options = field.getNoacfieldselectoptionses();
				Iterator<Noacfieldselectoptions> optionIterator = options.iterator();
				while(optionIterator.hasNext()){
					optiongroup.addItem(optionIterator.next().getLabel());
				}
				if(inputType.equals("checkbox"))
					optiongroup.setMultiSelect(true);
				form.addComponent(optiongroup);
			}
		}
		this.setContent(form);
	}
}
