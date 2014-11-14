package com.dal.noac.dao;

import java.util.List;






import org.hibernate.Query;
import org.hibernate.Session;

import com.dal.noac.model.Drug;
import com.dal.noac.util.DatabaseUtil;

public class DrugDAO {
	
	Session currentSession;

	public DrugDAO() {
		currentSession = DatabaseUtil.getSessionFactory().getCurrentSession();
	}
	
	public List getDrugList(){
		String hql = "FROM Drug";
		Query listQuery = currentSession.createQuery(hql);
		return listQuery.list();
	}
	
	public Drug getDrugByName(String name){
		return (Drug) currentSession.createQuery(
			    "from Drug where name = '" + name + "'").uniqueResult();
	}
	
	public List getProvinceList(){
		String hql = "FROM Province";
		Query listQuery = currentSession.createQuery(hql);
		return listQuery.list();
	}

}
