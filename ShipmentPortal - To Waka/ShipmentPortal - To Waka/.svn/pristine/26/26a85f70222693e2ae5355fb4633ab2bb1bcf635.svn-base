package com.csfe.trs.business.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.csfe.common.CSFEException;
import com.csfe.common.EmailNotification;
import com.csfe.common.WarningNotification;
import com.csfe.trs.business.dao.log.EDIProcessLogDAO;
import com.csfe.trs.business.entity.EDIProcessLog;
import com.csfe.trs.business.entity.POFulfillment;

public class EDIServiceImpl implements EDIService{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired(required = false)
	private EDIProcessLogDAO EDIProcessLogDAO;

	@Value("${TRS.ProjectID}")
	private String projectID;
	@Value("${TRS.Ptype}")
	private String ptype;
	@Value("${TRS.RefClass}")
	private String refClass;
	@Value("${TRS.SysID}")
	private String sysID;
	
	@Autowired
	@Qualifier("WarningNotification")
	private EmailNotification warningNotification;
	
	public EmailNotification getWarningNotification() {
		return warningNotification;
	}
	
	public EDIProcessLogDAO getEDIProcessLogDAO() {
		return EDIProcessLogDAO;
	}

	public void setEDIProcessLogDAO(EDIProcessLogDAO eDIProcessLogDAO) {
		EDIProcessLogDAO = eDIProcessLogDAO;
	}
	
	public String getProjectID() {
		return projectID;
	}

	public String getPtype() {
		return ptype;
	}

	public String getRefClass() {
		return refClass;
	}

	public String getSysID() {
		return sysID;
	}

	@Override
	public List<EDIProcessLog> findEDIProcessLogByBKNO(Set<String> list) throws CSFEException{
		return this.getEDIProcessLogDAO().findEDIProcessLogByBKNO(list, this.getProjectID(), this.getPtype(), this.getRefClass(), this.getSysID());
	}
	
	@Override
	public void saveEDIProcessLog(POFulfillment value, String creator) {
		try {
			EDIProcessLog log = new EDIProcessLog(value, this.getProjectID(), this.getPtype(), this.getRefClass(), this.getSysID(), creator);
			Date now = new Date();
			log.setCreatedOn(now);
			log.setUpdatedOn(now);
			this.getEDIProcessLogDAO().saveEDIProcessLog(log);
		}catch (CSFEException e) {
			logger.error(e);
			this.handleSendErrorNotification(e);
		}catch (Exception e) {
			logger.error(e);
			this.handleSendErrorNotification(e);
		}
	}
	
	protected void handleSendErrorNotification(Exception e) {
		((WarningNotification)this.getWarningNotification()).setException(e);
		this.getWarningNotification().saveDeliveryReq();
	}
}
