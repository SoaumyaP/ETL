package com.csfe.common.base;

import java.io.Serializable;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public abstract class BaseDAO implements Serializable  {
	
	public static final Logger logger = Logger.getLogger(BaseDAO.class);

	private SqlSessionFactory sessionFactory;
    
    public abstract Class getModelClass();
	
	@Value("${System.SystemID}")
	private String SYSTEM_ID;
	
	public BaseDAO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SqlSessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SqlSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public SqlSession getSession() {
		SqlSession session = this.sessionFactory.openSession();
		return session;
	}
}
