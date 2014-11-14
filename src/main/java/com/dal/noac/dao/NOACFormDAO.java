package com.dal.noac.dao;



import org.hibernate.Session;

import com.dal.noac.model.Noacfield;
import com.dal.noac.model.Noacform;
import com.dal.noac.util.DatabaseUtil;

public class NOACFormDAO {

	Session currentSession;
	
	public NOACFormDAO() {
		currentSession = DatabaseUtil.getSessionFactory().getCurrentSession();
	}
	
	public Noacform getForm(int formId) {
		return (Noacform) currentSession.get(Noacform.class, formId);
	}
	
	public Noacfield getFieldReference(int fieldId){
		return (Noacfield) currentSession.get(Noacfield.class, fieldId);
	}

}
