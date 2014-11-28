package com.dal.noac.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dal.noac.model.Noacfield;
import com.dal.noac.model.Noacform;
import com.dal.noac.util.DatabaseUtil;

public class NOACFieldDAO {
	Session currentSession;
	
	public NOACFieldDAO(){
		currentSession = DatabaseUtil.getSessionFactory().getCurrentSession();
	}
	
	public Noacfield getField(int fieldId) {
		return (Noacfield) currentSession.get(Noacfield.class, fieldId);
	}

	public List<Noacfield> getNoacfieldList(int formId){
		String hql = "FROM Noacfield as nf where exists ( from Section as s where nf.section = s and exists ( from Noacform as f where s.noacform = f and f.id = " + formId + "))";
		Query listQuery = currentSession.createQuery(hql);
		return (List<Noacfield>)listQuery.list();
	}
}
