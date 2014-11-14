package com.dal.noac.util;

import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.dal.noac.model.Outcome;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;

public class NOACValidateButtonListner extends CustomComponent implements Button.ClickListener {
	private Set<Outcome> outcomes;
	private NOACFieldGroup currentFieldGroup;
	final Layout layout = new HorizontalLayout();
	
	public NOACValidateButtonListner(NOACFieldGroup fieldgroup, Set<Outcome> outcomeSet){
		
		outcomes = outcomeSet;
		outcomes.size();
		currentFieldGroup = fieldgroup;
		Button button = new Button("Validate");
        button.addClickListener(this);
		layout.addComponent(button);
		setCompositionRoot(layout);
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		Iterator<Outcome> outcomeIterator = outcomes.iterator();
		Pattern referencePattern = Pattern.compile("NF\\d");
		while(outcomeIterator.hasNext()){
			Outcome outcome = outcomeIterator.next();
			String criteria = outcome.getCriteria();
			Matcher referenceFinder= referencePattern.matcher(criteria);
			while(referenceFinder.find()){
				String fieldReference = referenceFinder.group();
				TextField currentField = (TextField)currentFieldGroup.getField(fieldReference);
				criteria = criteria.replaceAll(fieldReference, currentField.getValue());
				referenceFinder= referencePattern.matcher(criteria);
			}
			ScriptEngineManager mgr = new ScriptEngineManager();
		    ScriptEngine engine = mgr.getEngineByName("JavaScript");
			try {
				if((Boolean)engine.eval(criteria)){
					Label messageLabel = new Label(outcome.getMessage());
					layout.addComponent(messageLabel);
				    break;
				}
			} catch (ScriptException e) {
				String trace = ExceptionUtils.getStackTrace(e);
				Label messageLabel = new Label(trace);
				layout.addComponent(messageLabel);
				break;
			}
		}
		
	}

}
