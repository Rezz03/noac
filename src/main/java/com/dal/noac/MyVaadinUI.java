package com.dal.noac;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.annotation.WebServlet;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dal.noac.dao.DrugDAO;
import com.dal.noac.dao.ProvinceDAO;
import com.dal.noac.layout.NOACFormSectionLayout;
import com.dal.noac.model.Drug;
import com.dal.noac.model.Noacform;
import com.dal.noac.model.Outcome;
import com.dal.noac.model.Province;
import com.dal.noac.model.Section;
import com.dal.noac.util.DatabaseUtil;
import com.dal.noac.util.NOACFieldGroup;
import com.dal.noac.util.NOACValidateButtonListner;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.client.ui.VFilterSelect.Select;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("mytheme")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI
{
	Navigator navigator;
    protected static final String MSGVIEW = "msg";   
    
    public class NoacFormView extends VerticalLayout implements View{
    	
    	class ButtonListener implements Button.ClickListener {

            String theMsg;
            Set<Outcome> outcomes;
            NOACFieldGroup noacfieldgroup;
            public ButtonListener(Set<Outcome> theOutcomes, NOACFieldGroup aNoacfieldgroup) {
            	outcomes = theOutcomes;
            	noacfieldgroup = aNoacfieldgroup;
            }

            @Override
            public void buttonClick(ClickEvent event) {
            	String message = "failed";
            	try {
					noacfieldgroup.commit();				
	        		Iterator<Outcome> outcomeIterator = outcomes.iterator();
	        		Pattern referencePattern = Pattern.compile("NF\\d");
	        		
	        		while(outcomeIterator.hasNext()){
	        			Outcome outcome = outcomeIterator.next();
	        			String criteria = outcome.getCriteria();
	        			Matcher referenceFinder= referencePattern.matcher(criteria);
	        			while(referenceFinder.find()){
	        				String fieldReference = referenceFinder.group();
	        				TextField currentField = (TextField)noacfieldgroup.getField(fieldReference);
	        				criteria = criteria.replaceAll(fieldReference, (String)currentField.getValue());
	        				referenceFinder= referencePattern.matcher(criteria);
	        			}
	        			ScriptEngineManager mgr = new ScriptEngineManager();
	        		    ScriptEngine engine = mgr.getEngineByName("JavaScript");
	        			try {
	        				if((Boolean)engine.eval(criteria)){
	        					message = outcome.getMessage();
	        				    break;
	        				} else{
	        					message = "Not " + outcome.getMessage();
	        				    break;
	        				}
	        			} catch (ScriptException e) {
	        				message = criteria;
	        			}
	        		}
            	} catch (Exception e1) {
					theMsg = e1.getMessage();
				}
        		theMsg = message;
                navigator.navigateTo(MSGVIEW + "/" + theMsg);
            }
        }
    	
    	public NoacFormView(){
    		final HorizontalLayout horizLayout = new HorizontalLayout();
            final Panel panelContainer = new Panel();
            
    		final BeanContainer<String, Drug> drugs =
    		        new BeanContainer<String, Drug>(Drug.class);
    		drugs.setBeanIdProperty("name");
    		DrugDAO drugDao = new DrugDAO();
    		List<Province> provinceItemList = drugDao.getProvinceList();
    		List<Drug> drugItemList = drugDao.getDrugList();
    		Iterator<Drug> drugIterator = drugItemList.iterator();
    		while(drugIterator.hasNext()){
    			drugs.addBean(drugIterator.next());
    		}
    		final ComboBox drugSelect =
    			    new ComboBox("Select a Drug", drugs);
    		drugSelect.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
    		drugSelect.setItemCaptionPropertyId("name");
    		
    		
    		BeanContainer<String, Province> provinces =
    		        new BeanContainer<String, Province>(Province.class);
    		provinces.setBeanIdProperty("name");
    		
    		final Form  countryForm  = new Form();
    		Iterator<Province> provinceIterator = provinceItemList.iterator();
    		while(provinceIterator.hasNext()){
    			provinces.addBean(provinceIterator.next());
    		}
    		final ComboBox provinceSelect =
    			    new ComboBox("Select a Province", provinces);
    		provinceSelect.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY);
    		provinceSelect.setItemCaptionPropertyId("name");
    		
    		Button getFormButton = new Button("Get Form");
    		getFormButton.addClickListener(new Button.ClickListener() {
    		    public void buttonClick(ClickEvent event) {
    		    	// Get the item to edit in the form
    		        DrugDAO dao = new DrugDAO();
    		        Drug selectedDrug = dao.getDrugByName(drugSelect.getValue().toString());
    		        Set<Noacform> forms = selectedDrug.getNoacforms();
    		        Iterator<Noacform> formIterator = forms.iterator();
    		        FormLayout formLayout = new FormLayout();		        
    		        
    		        while(formIterator.hasNext()){
    		        	Noacform currentform = formIterator.next();
    		        	if(currentform.getProvince().getName().equals(provinceSelect.getValue())){
    		        		final NOACFieldGroup noacfieldgroup = new NOACFieldGroup(currentform);
    		        		formLayout = noacfieldgroup.getFormLayout();
    		        		final Set<Outcome> outcomes = currentform.getOutcomes();
    		        		outcomes.size();
    		        		Button button = new Button("Validate");
    		        		button.addClickListener(new ButtonListener(outcomes, noacfieldgroup));
    		        		formLayout.addComponent(button);
    		        		break;
    		        	}		        	
    		        }    		        
    		        panelContainer.setContent(formLayout);    		        
    		    }
    		});
    		horizLayout.addComponent(provinceSelect);
    		horizLayout.addComponent(drugSelect);
    		horizLayout.addComponent(getFormButton);
    		panelContainer.setContent(horizLayout);
    		addComponent(panelContainer);
    	}

		@Override
		public void enter(ViewChangeEvent event) {
			// TODO Auto-generated method stub
			
		}
    	
    }
    
    public class NoacMessageView extends HorizontalLayout implements View{

		@Override
		public void enter(ViewChangeEvent event) {
			Label aLabel = new Label("msg is " + event.getParameters());
			addComponent(aLabel);
			
		} 
    	
    } 
    
    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = MyVaadinUI.class, widgetset = "com.dal.noac.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
    	navigator = new Navigator(this, this);
    	navigator.addView("", new NoacFormView());
        navigator.addView(MSGVIEW, new NoacMessageView());
		
    }

}
