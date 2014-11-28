package com.dal.noac;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.dal.noac.NOACFormViewer.NoacFormView;
import com.dal.noac.NOACFormViewer.NoacMessageView;
import com.dal.noac.dao.NOACFieldDAO;
import com.dal.noac.dao.NOACFormDAO;
import com.dal.noac.model.Noacfield;
import com.dal.noac.model.Noacfieldselectoptions;
import com.dal.noac.model.Noacform;
import com.dal.noac.model.Outcome;
import com.dal.noac.model.Section;
import com.dal.noac.view.NOACFormBuilderLayout;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class NOACFormBuilder extends UI {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static class Servlet extends VaadinServlet {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}
	  
	Navigator navigator;
    BeanItemContainer<Noacform> currentFormContainer = new BeanItemContainer<Noacform>(Noacform.class);
    final Panel mainPanel = new Panel();
    private MenuBar menubar = new MenuBar();
	public static class BuilderViews{
		public static final String FORMVIEW = "noacform";
		public static final String SECTIONVIEW = "noacsection";
		public static final String FIELDVIEW = "noacfield";
		public static final String FIELDOPTIONVIEW = "noacfieldoption";
		public static final String DRUGOVERVIEW = "drugs";
		public static final String DRUGVIEW = "drug";
		public static final String PROVINCEOVERVIEW = "provinces";
		public static final String PROVINCEVIEW = "province";
		public static final String OUTCOMEVIEW = "outcome";
	}
	protected void init(VaadinRequest request) {
		navigator = new Navigator(this, this);
		navigator.addView("", new NoacFormOverview());
		navigator.addView(BuilderViews.FORMVIEW, new NoacFormView());
		navigator.addView(BuilderViews.SECTIONVIEW, new SectionView());
		navigator.addView(BuilderViews.FIELDVIEW, new NoacFieldView());
		
		MenuItem forms = menubar.addItem("Manage Forms", null);
		
	}
	
	public class NoacFormOverview extends VerticalLayout implements View{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public NoacFormOverview(){
			addComponent(menubar);
			HorizontalLayout horiz = new HorizontalLayout();
			BeanItemContainer<Noacform> formBeans =
				    new BeanItemContainer<Noacform>(Noacform.class);
			formBeans.addNestedContainerProperty("drug.name");
			formBeans.addNestedContainerProperty("province.name");
			NOACFormDAO noacformdao = new NOACFormDAO();
			List<Noacform> formData = noacformdao.getForms();
			formBeans.addAll(formData);
			
			final Table table = new Table("Forms", formBeans);
			table.setVisibleColumns(new String[]{"drug.name", "province.name", "title"});
			final Label testLabel = new Label("Selected: ");
			table.setSelectable(true);
			table.setImmediate(true);
			table.addValueChangeListener(new Property.ValueChangeListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void valueChange(ValueChangeEvent event) {
					Property<Noacform> eventProperty = event.getProperty();
					currentFormContainer.removeAllItems();
					currentFormContainer.addItem(eventProperty.getValue());
					Noacform currentForm = (Noacform) table.getValue();
					testLabel.setValue("Selected: " + currentForm.getId());	
					
					
				}
			});
			Button addFormButton = new Button("Add Form");
			Button editFormButton = new Button("Edit Form");
			addFormButton.addClickListener(new Button.ClickListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					navigator.navigateTo(BuilderViews.FORMVIEW + "/");
					
				}
			});
			editFormButton.addClickListener(new Button.ClickListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					Noacform currentForm = (Noacform) table.getValue();
					if(currentForm != null)
						navigator.navigateTo(BuilderViews.FORMVIEW + "/" + currentForm.getId());
					
				}
			});
			VerticalLayout formControlLayout = new VerticalLayout();
			
			horiz.addComponent(table);			
			formControlLayout.addComponent(addFormButton);
			formControlLayout.addComponent(editFormButton);
			horiz.addComponent(formControlLayout);
			horiz.addComponent(testLabel);
			addComponent(horiz);
		}

		@Override
		public void enter(ViewChangeEvent event) {
			
		}
		
	}
	
	public class NoacFormView extends VerticalLayout implements View{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public NoacFormView(){
			addComponent(menubar);
			FormLayout formlayout = new FormLayout();
			String parameter = event.getParameters();
			Noacform form = new Noacform();
			if(parameter != null && !parameter.isEmpty()){
				int formId = Integer.parseInt(parameter);
				NOACFormDAO formDao = new NOACFormDAO();
				form = formDao.getForm(formId);
			}
			BeanItem<Noacform> formItem = new BeanItem<Noacform>(form);
			formItem.addNestedProperty("drug.name");
			formItem.addNestedProperty("province.key");
			formItem.addNestedProperty("province.name");
			BeanFieldGroup<Noacform> fieldgroup = new BeanFieldGroup<Noacform>(Noacform.class);
			fieldgroup.setItemDataSource(formItem);
			formlayout.addComponent(menubar);
			formlayout.addComponent(fieldgroup.buildAndBind("Title", "title"));
			formlayout.addComponent(fieldgroup.buildAndBind("Drug Name", "drug.name"));
			formlayout.addComponent(fieldgroup.buildAndBind("Province Key", "province.key"));
			formlayout.addComponent(fieldgroup.buildAndBind("Province Name", "province.name"));
			
			List<Section> formSections = form.getSections();
			BeanItemContainer<Section> sectionContainer = new BeanItemContainer<Section>(Section.class);
			sectionContainer.addAll(formSections);
			final Table sectionTable = new Table("Sections", sectionContainer);
			sectionTable.setVisibleColumns(new String[]{"title", "orderIndex"});
			fieldgroup.bind(sectionTable, "sections");
			sectionTable.setSelectable(true);
			sectionTable.setImmediate(true);
			final Label testLabel = new Label("Selected: ");
			sectionTable.addValueChangeListener(new Property.ValueChangeListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void valueChange(ValueChangeEvent event) {
					testLabel.setValue("Selected: " + testLabel.getValue());					
				}
			});
			Button addSection = new Button("Add Section");
			addSection.addClickListener(new Button.ClickListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					navigator.navigateTo(BuilderViews.SECTIONVIEW + "/");
					
				}
			});			
			Button editSection = new Button("Edit Section");
			editSection.addClickListener(new Button.ClickListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					navigator.navigateTo(BuilderViews.SECTIONVIEW + "/");
					
				}
			});	
			HorizontalLayout sectionTableLayout = new HorizontalLayout();
			VerticalLayout sectionControlLayout = new VerticalLayout();
			sectionTableLayout.addComponent(sectionTable);
			sectionTableLayout.addComponent(sectionControlLayout);
			sectionControlLayout.addComponent(addSection);
			formlayout.addComponent(sectionTableLayout);
			
			List<Outcome> formOutcomes = form.getOutcomes();
			BeanItemContainer<Outcome> outcomeContainer = new BeanItemContainer<Outcome>(Outcome.class);
			outcomeContainer.addAll(formOutcomes);
			Table outcomeTable = new Table("Outcomes", outcomeContainer);
			outcomeTable.setVisibleColumns(new String[]{"message"});
			fieldgroup.bind(outcomeTable, "outcomes");
			Button addOutcome = new Button("Add Outcome");
			addSection.addClickListener(new Button.ClickListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void buttonClick(ClickEvent event) {
					navigator.navigateTo(BuilderViews.SECTIONVIEW + "/");
					
				}
			});			
			HorizontalLayout outcomeTableLayout = new HorizontalLayout();
			VerticalLayout outcomeControlLayout = new VerticalLayout();
			outcomeTableLayout.addComponent(outcomeTable);
			outcomeTableLayout.addComponent(outcomeControlLayout);
			outcomeControlLayout.addComponent(addOutcome);
			formlayout.addComponent(outcomeTableLayout);
			addComponent(formlayout);
		}

		@Override
		public void enter(ViewChangeEvent event) {
			
		}
		
	}
	
	public class SectionView extends VerticalLayout implements View{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		protected final BeanItem<Section> sectionItem;
		
		public SectionView(){
			addComponent(menubar);
			FormLayout formlayout = new FormLayout();
			Section section = new Section();
			sectionItem = new BeanItem<Section>(section);
						
			BeanFieldGroup<Section> fieldgroup = new BeanFieldGroup<Section>(Section.class);
			fieldgroup.setItemDataSource(sectionItem);
			formlayout.addComponent(fieldgroup.buildAndBind("Title", "title"));
			formlayout.addComponent(fieldgroup.buildAndBind("Order Index", "orderIndex"));
			List<Noacfield> sectionFields = section.getNoacfields();
			BeanItemContainer<Noacfield> fieldContainer = new BeanItemContainer<Noacfield>(Noacfield.class);
			fieldContainer.addAll(sectionFields);
			Table fieldTable = new Table("Sections", fieldContainer);
			fieldgroup.bind(fieldTable, "noacfields");
			Button addSection = new Button("Add Section");
			addSection.addClickListener(new Button.ClickListener() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public void buttonClick(ClickEvent event) {
					navigator.navigateTo(BuilderViews.FIELDVIEW + "/");
					
				}
			});
			addComponent(formlayout);
		}

		@Override
		public void enter(ViewChangeEvent event) {
			String parameter = event.getParameters();
			Section section = new Section();
			if(parameter != null && !parameter.isEmpty()){
				int sectionId = Integer.parseInt(parameter);
				NOACFormDAO formDao = new NOACFormDAO();
				section = formDao.getSectionById(sectionId);
				sectionItem.
			}
		}
		
	}
	
	public class NoacFieldView extends VerticalLayout implements View{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void enter(ViewChangeEvent event) {
			String parameter = event.getParameters();
			Noacfield noacfield = new Noacfield();
			if(parameter != null && !parameter.isEmpty()){
				int fieldId = Integer.parseInt(parameter);
				NOACFieldDAO fieldDao = new NOACFieldDAO();
				noacfield = fieldDao.getField(fieldId);
			}
			BeanItem<Noacfield> fieldItem = new BeanItem<Noacfield>(noacfield);
			BeanFieldGroup<Noacfield> fieldgroup = new BeanFieldGroup<Noacfield>(Noacfield.class);
			fieldgroup.setItemDataSource(fieldItem);
			addComponent(menubar);
			addComponent(fieldgroup.buildAndBind("Label", "label"));
			ListSelect select = new ListSelect("Input Format");
			select.addItems("text", "number", "select", "multiselect", "inr", "chad");
			fieldgroup.bind(select, "inputFormat");
			
			addComponent(select);
			addComponent(fieldgroup.buildAndBind("Order", "orderIndex"));
			addComponent(fieldgroup.buildAndBind("Default Value", "value"));
			if(noacfield.getInputFormat().equals("select") || noacfield.getInputFormat().equals("multiselect")){
				List<Noacfieldselectoptions> fieldOptions = noacfield.getNoacfieldselectoptionses();
				BeanItemContainer<Noacfieldselectoptions> fieldOptionContainer = new BeanItemContainer<Noacfieldselectoptions>(Noacfieldselectoptions.class);
				fieldOptionContainer.addAll(fieldOptions);
				Table fieldOptionTable = new Table("Field Select Options", fieldOptionContainer);
				fieldgroup.bind(fieldOptionTable, "noacfieldselectoptionses");
				addComponent(fieldOptionTable);
			}
		}
		
	}

}
