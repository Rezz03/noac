package com.dal.noac.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.dal.noac.util.DatabaseUtil;

public class ProvinceDAO {
	
	Session currentSession;

	public ProvinceDAO() {
		currentSession = DatabaseUtil.getSessionFactory().getCurrentSession();
	}
	
	public List getList(){
		String hql = "FROM Province";
		Query listQuery = currentSession.createQuery(hql);
		return listQuery.list();
	}

}
