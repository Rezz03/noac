package com.dal.noac.widgets;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class CriteriaEditorControl extends CustomComponent {
	private String subCriteria;
	
	public CriteriaEditorControl(String criteria){
		subCriteria = criteria;
		VerticalLayout layout = new VerticalLayout();
		Label label = new Label(criteria);
		Button editButton = new Button("Edit");
		Button deleteButton = new Button("Delete");
		Button addButton = new Button("Add");
		
	}
	
	

}
