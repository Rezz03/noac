package com.dal.noac.view;

import java.util.List;

import com.dal.noac.model.Noacform;
import com.dal.noac.model.Section;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;

public class NOACFormBuilderLayout extends FormLayout {

	private TextField name = new TextField("Name");
	
	public NOACFormBuilderLayout(){
		Noacform form = new Noacform();
		BeanItem<Noacform> formItem = new BeanItem<Noacform>(form);
		formItem.addNestedProperty("drug.name");
		formItem.addNestedProperty("province.key");
		formItem.addNestedProperty("province.name");
		BeanFieldGroup<Noacform> fieldgroup = new BeanFieldGroup<Noacform>(Noacform.class);
		fieldgroup.setItemDataSource(formItem);
		addComponent(fieldgroup.buildAndBind("Title", "title"));
		addComponent(fieldgroup.buildAndBind("Drug Name", "drug.name"));
		addComponent(fieldgroup.buildAndBind("Province Key", "province.key"));
		addComponent(fieldgroup.buildAndBind("Province Name", "province.name"));
		List<Section> formSections = form.getSections();
		BeanItemContainer<Section> sectionContainer = new BeanItemContainer<Section>(Section.class);
		Table sectionTable = new Table();
		fieldgroup.bind(sectionTable, "sections");
	}
}
