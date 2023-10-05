package com.csfe.trs.business.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.csfe.common.CSFEException;
import com.csfe.trs.business.dao.edison.EdisonDAO;
import com.csfe.trs.business.entity.Vesvoyforwarder;

public class EdisonServiceImpl implements EdisonService{
	
	@Autowired(required = false)
	private EdisonDAO edisonDAO;
	
	public EdisonDAO getEdisonDAO() {
		return edisonDAO;
	}

	public void setEdisonDAO(EdisonDAO edisonDAO) {
		this.edisonDAO = edisonDAO;
	}
	
	@Override
	public Vesvoyforwarder findVesvoyforwarderBySONO(String sysid, String sono) throws CSFEException{
		return this.getEdisonDAO().findVesvoyforwarderBySONO(sysid, sono);
	}
}
