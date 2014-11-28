package com.dal.noac.dao;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dal.noac.model.Noacfield;
import com.dal.noac.model.Noacform;
import com.dal.noac.model.Section;
import com.dal.noac.util.DatabaseUtil;

public class NOACFormDAO {

	Session currentSession;
	
	public NOACFormDAO() {
		currentSession = DatabaseUtil.getSessionFactory().getCurrentSession();
	}
	
	public Noacform getForm(int formId) {
		return (Noacform) currentSession.get(Noacform.class, formId);
	}
	
	public List<Noacform> getForms(){
		String hql = "FROM Noacform";
		Query listQuery = currentSession.createQuery(hql);
		return (List<Noacform>)listQuery.list();
	}
	
	public Noacfield getFieldReference(int fieldId){
		return (Noacfield) currentSession.get(Noacfield.class, fieldId);
	}
	
	public Section getSectionById(int sectionId){
		return (Section) currentSession.get(Section.class, sectionId);
	}

}
