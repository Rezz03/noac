package com.dal.noac.widgets;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.dal.noac.dao.NOACFieldDAO;
import com.dal.noac.model.Noacfield;
import com.dal.noac.model.Noacfieldselectoptions;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class CriteriaEditor extends CustomField<String> {
	
	private GridLayout grid = new GridLayout();

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
	
	public class CriteriaEditorPopup extends Window{
		private VerticalLayout mainLayout;
		private ListSelect joinSelect = new ListSelect();
		private Table ruleTable;
		private Noacfield defaultField = new Noacfield();
		private ListSelect fieldSelect = new ListSelect();
		private ListSelect operators = new ListSelect();
		private IndexedContainer container = new IndexedContainer();
		public class CriteriaRemoveButtonListener implements Button.ClickListener{
			private Object itemId;
			public CriteriaRemoveButtonListener(Object anitemid){
				itemId = anitemid;
			}
			@Override
			public void buttonClick(ClickEvent event) {
				ruleTable.removeItem(itemId);
			}
			
		}
		public class CriteriaSelectOptionValueChangeListener implements Property.ValueChangeListener{
			private Object itemId;
			public CriteriaSelectOptionValueChangeListener(Object anitemid){
				itemId = anitemid;
			}

			@Override
			public void valueChange(
					com.vaadin.data.Property.ValueChangeEvent event) {
				NOACFieldDAO nfdao = new NOACFieldDAO();
				String fieldReference = (String)event.getProperty().getValue();
				String fieldId = fieldReference.substring(2);
				Noacfield selectedField = nfdao.getField(Integer.parseInt(fieldId));
				AbstractComponent valueControl = buildValueControl(selectedField);
				Item newItem = container.getItem(itemId);
				newItem.getItemProperty("valuecontrol").setValue(valueControl);
			}
			
		}
		public class CriteriaEditorCloseListener implements CloseListener {
			
			private int insertionIndex = 0;
			private int endInsertionIndex = -1;
			public CriteriaEditorCloseListener(int anInsertionIndex){
				insertionIndex = anInsertionIndex;
			}
			
			public CriteriaEditorCloseListener(int anInsertionIndex, int anEndInsertionIndex){
				insertionIndex = anInsertionIndex;
				endInsertionIndex = anEndInsertionIndex;
			}

			@Override
			public void windowClose(CloseEvent e) {
				String subcriteriaresult = "(";
				for (Iterator i = ruleTable.getItemIds().iterator(); i.hasNext();) {
					int iid = (Integer) i.next();
				    
				    // Now get the actual item from the table.
				    Item item = ruleTable.getItem(iid);
				    subcriteriaresult += (String)item.getItemProperty("fieldselect").getValue() + " ";
				    subcriteriaresult += (String)item.getItemProperty("operationselect").getValue() + " ";
				    subcriteriaresult += "'" + (String)item.getItemProperty("valuecontrol").getValue() + "'";
				    
				    if(!i.hasNext()){
				    	subcriteriaresult += ")";
				    } else{
				    	subcriteriaresult += " " + joinSelect.getValue() + " ";
				    }
				}
				if(endInsertionIndex < 0){
					String criteriaPrefix = getInternalValue().substring(0, insertionIndex);
					String criteriaSuffix = getInternalValue().substring(insertionIndex);
					setInternalValue(criteriaPrefix + subcriteriaresult + criteriaSuffix);
				} else {
					String criteriaPrefix = getInternalValue().substring(0, insertionIndex);
					String criteriaSuffix = getInternalValue().substring(endInsertionIndex);
					setInternalValue(criteriaPrefix + subcriteriaresult + criteriaSuffix);
				}
				
			}
			
		}
		public CriteriaEditorPopup(String label, int formId, int anInsertionIndex){
			super(label);
			ruleTable = new Table();
			NOACFieldDAO nfdao = new NOACFieldDAO();
			List<Noacfield> formFields = nfdao.getNoacfieldList(formId);
			formFields.size();
			mainLayout = new VerticalLayout();
			final int insertionIndex = anInsertionIndex;
			this.addCloseListener(new CloseListener(){

				@Override
				public void windowClose(CloseEvent e) {
					String subcriteriaresult = "(";
					for (Iterator i = ruleTable.getItemIds().iterator(); i.hasNext();) {
						int iid = (Integer) i.next();
					    
					    // Now get the actual item from the table.
					    Item item = ruleTable.getItem(iid);
					    subcriteriaresult += (String)item.getItemProperty("fieldselect").getValue() + " ";
					    subcriteriaresult += (String)item.getItemProperty("operationselect").getValue() + " ";
					    subcriteriaresult += "'" + (String)item.getItemProperty("valuecontrol").getValue() + "'";
					    
					    if(!i.hasNext()){
					    	subcriteriaresult += ")";
					    } else{
					    	subcriteriaresult += " " + joinSelect.getValue() + " ";
					    }
					}
					String criteriaPrefix = getInternalValue().substring(0, insertionIndex);
					String criteriaSuffix = getInternalValue().substring(insertionIndex);
					setInternalValue(criteriaPrefix + subcriteriaresult + criteriaSuffix);
				}
				
			});
			this.setContent(mainLayout);
			HorizontalLayout horiz = new HorizontalLayout();
			CheckBox match = new CheckBox("Match");
			match.setValue(true);
			
			joinSelect.addItems("AND", "OR");
			joinSelect.setItemCaption("AND", "all");
			joinSelect.setItemCaption("OR", "any");
			joinSelect.setNullSelectionAllowed(false);
			joinSelect.setRows(1);
			joinSelect.setVisible(false);
			Label restOfSentence = new Label("the following rule.");
			horiz.addComponent(match);
			horiz.addComponent(joinSelect);
			horiz.addComponent(restOfSentence);
			mainLayout.addComponent(horiz);
			
			fieldSelect.setNullSelectionAllowed(false);
			fieldSelect.setRows(1);
			Iterator<Noacfield> fieldIterator = formFields.iterator();
			
			while(fieldIterator.hasNext()){
				Noacfield currentListField = fieldIterator.next();
				if(defaultField.getLabel() == null)
					defaultField = currentListField;					
				fieldSelect.addItem("NF"+ currentListField.getId());
				fieldSelect.setItemCaption("NF"+ currentListField.getId(), currentListField.getLabel());
			}
			
			operators.addItems("==", ">", ">=", "<", "<=");
			AbstractComponent valueControl = buildValueControl(defaultField);
			if(defaultField.getInputFormat()!= null && !defaultField.getInputFormat().isEmpty()){
				String defaultInputFormat = defaultField.getInputFormat();
				if(defaultInputFormat.equals("select") || defaultInputFormat.equals("multiselect")){
					ListSelect fieldOptionSelect = new ListSelect();
					List<Noacfieldselectoptions> defaultFieldOptions = defaultField.getNoacfieldselectoptionses();
					Iterator<Noacfieldselectoptions> defaultFieldOptionItertor = defaultFieldOptions.iterator();
					while(defaultFieldOptionItertor.hasNext()){
						Noacfieldselectoptions defaultFieldOption = defaultFieldOptionItertor.next();
						fieldOptionSelect.addItem(defaultFieldOption.getValue());
						fieldOptionSelect.setItemCaption(defaultFieldOption.getValue(), defaultFieldOption.getLabel());
					}
					if(defaultInputFormat.equals("select")){
						fieldOptionSelect.setRows(1);
					}
					else if(defaultInputFormat.equals("multiselect")){
						fieldOptionSelect.setMultiSelect(true);
						fieldOptionSelect.setRows(5);						
					}
					valueControl = fieldOptionSelect;
				}
				else if(!defaultInputFormat.equals("inr")){
					TextField defaultTextfield = new TextField();
					defaultTextfield.setMaxLength(5);
					valueControl = defaultTextfield;
				}
			}
			container.addContainerProperty("fieldselect", ListSelect.class, fieldSelect);
			container.addContainerProperty("operationselect", ListSelect.class, operators);
			container.addContainerProperty("valuecontrol", AbstractComponent.class, valueControl);
			container.addContainerProperty("removebutton", Button.class, new Button("Remove"));
			Button addItemButton = new Button("Add");
			addItemButton.addClickListener(new Button.ClickListener() {
				
				@Override
				public void buttonClick(ClickEvent event) {
					if(ruleTable.size() <= 1){
						joinSelect.setVisible(true);
						// adjust label
					}
					Item newItem = container.getItem(container.addItem());
					Button newRemoveButton = (Button) newItem.getItemProperty("removebutton").getValue();
					newRemoveButton.addClickListener(new CriteriaRemoveButtonListener(newItem));
					newItem.getItemProperty("removebutton").setValue(newRemoveButton);
				}
			});
			container.addContainerProperty("addbutton", Button.class, addItemButton);
			container.addItem();
			ruleTable.setContainerDataSource(container);
			mainLayout.addComponent(ruleTable);
		}
		
		public AbstractComponent buildValueControl(Noacfield selectedField){
			
			if(selectedField.getInputFormat()!= null && !selectedField.getInputFormat().isEmpty()){
				String defaultInputFormat = selectedField.getInputFormat();
				if(defaultInputFormat.equals("select") || defaultInputFormat.equals("multiselect")){
					ListSelect fieldOptionSelect = new ListSelect();
					List<Noacfieldselectoptions> defaultFieldOptions = selectedField.getNoacfieldselectoptionses();
					Iterator<Noacfieldselectoptions> defaultFieldOptionItertor = defaultFieldOptions.iterator();
					while(defaultFieldOptionItertor.hasNext()){
						Noacfieldselectoptions defaultFieldOption = defaultFieldOptionItertor.next();
						fieldOptionSelect.addItem(defaultFieldOption.getValue());
						fieldOptionSelect.setItemCaption(defaultFieldOption.getValue(), defaultFieldOption.getLabel());
					}
					if(defaultInputFormat.equals("select")){
						fieldOptionSelect.setRows(1);
					}
					else if(defaultInputFormat.equals("multiselect")){
						fieldOptionSelect.setMultiSelect(true);
						fieldOptionSelect.setRows(5);						
					}
					return fieldOptionSelect;
				}
				else if(!defaultInputFormat.equals("inr")){
					TextField defaultTextfield = new TextField();
					defaultTextfield.setMaxLength(5);
					return defaultTextfield;
				}
			}
			return new TextField();
		}
	}
	protected Component initContent() {
		GridLayout grid = new GridLayout();
		VaadinSession session = VaadinSession.getCurrent();
		final Window window = new CriteriaEditorPopup("Add criteria", Integer.parseInt((String)session.getAttribute("formId")), 0);
		window.addCloseListener(new CloseListener(){

			@Override
			public void windowClose(CloseEvent e) {
				// build criteria string here
			}
			
		});
		String criteria = this.getInternalValue();		
		if(criteria != null && !criteria.isEmpty()){
			String filteredCriteria = criteria.substring(1, criteria.length() - 1);
		}
		return null;
	}

	public Class<? extends String> getType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void setInternalValue(String criteria) {
        super.setInternalValue(criteria);
    }
	
	private void buildCriteriaGrid(String criteria, int level){
		int firstAndIndex = criteria.indexOf("and");
		int firstOrIndex = criteria.indexOf("or");
		String joiner = "and";
		if(firstOrIndex > 0 && firstOrIndex < firstAndIndex)
			joiner = "or";
		Label ruleGroupLabel = new Label("Match " + ((joiner.equals("and")) ? "all" : "any") + " of the following rules:");
		Button addCriteriaButton = new Button("Add");
		addCriteriaButton.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				
				
			}
		});
		int leftBracketIndex = criteria.indexOf('(');
		String gridCriteria = "";
		int gridRowIndex = 0;
		if(leftBracketIndex > 0)
			gridCriteria = criteria.substring(0, leftBracketIndex);
		else
			gridCriteria = criteria;
		if(gridCriteria.indexOf("and") > 0){
			int currentAndIndex = gridCriteria.indexOf("and");
			String gridRowCriteria = "";
			while(currentAndIndex > 0){				
				currentAndIndex = gridCriteria.indexOf("and");
				if(gridRowIndex == 0)
					gridRowCriteria = gridCriteria.substring(0, currentAndIndex);
				else
					gridRowCriteria = gridRowCriteria.substring(currentAndIndex + 2, gridCriteria.indexOf("and"));
				grid.addComponent(new Label(gridRowCriteria), level, gridRowIndex);
				
				//add edit button
				//add add button
				//add delete button
				gridRowIndex++;
			}
		} else{
			Label criteriaLabel = new Label(criteria);
			grid.addComponent(criteriaLabel, level, gridRowIndex);
			//add edit button
			//add add button
			//add delete button
		}
		if(leftBracketIndex > 0){
			String criteriaRemainder = criteria.substring(leftBracketIndex + 1);
			int currentBracketIndex = criteriaRemainder.indexOf('(');
			String subCriteria = "";
			if(currentBracketIndex > 0){
				Stack<String> bracketStack = new Stack<String>();
				bracketStack.push("(");
				
				while(!bracketStack.isEmpty() && criteriaRemainder.indexOf(')', currentBracketIndex) > 0){
					int nextLeftIndex = criteriaRemainder.indexOf('(', currentBracketIndex);
					int nextRightIndex = criteriaRemainder.indexOf(')', currentBracketIndex);
					if(nextLeftIndex < nextRightIndex)
						bracketStack.push("(");
					else
						bracketStack.pop();
				}
				subCriteria = criteriaRemainder.substring(0, criteriaRemainder.indexOf(')', currentBracketIndex) - 1);
			}else{
				subCriteria = criteriaRemainder;
			}
			buildCriteriaGrid(subCriteria, level + 1);
		}
	}

}
