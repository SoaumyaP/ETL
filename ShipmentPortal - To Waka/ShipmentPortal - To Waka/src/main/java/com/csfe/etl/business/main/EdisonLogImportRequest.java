package com.csfe.etl.business.main;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.csfe.common.CSFEException;
import com.csfe.common.EmailNotification;
import com.csfe.common.WarningNotification;
import com.csfe.etl.business.service.AzureService;

@Component
public class EdisonLogImportRequest {
	private static Logger logger = Logger.getLogger(EdisonLogImportRequest.class);
	
	@Value("${System.UserID:}")
	private String userName = "";
	
	@Value("${System.ServiceHandler:}")
	private String serviceName = "";
	
	@Autowired
	@Qualifier("WarningNotification")
	private EmailNotification warningNotification;
	
	private AzureService service;
		
	public EmailNotification getWarningNotification() {
		return warningNotification;
	}
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public AzureService getService() {
		return service;
	}

	public void setService(AzureService service) {
		this.service = service;
	}

	public String getUserName() {
		return userName;
	}

	public static void main(String args[]) {
		logger.info("Azure_ETL - Start Process\n");
		ApplicationContext context = new ClassPathXmlApplicationContext(args[0]);
		EdisonLogImportRequest main = context.getBean(EdisonLogImportRequest.class);
		
		main.registerService(context, main.getServiceName());
		main.processReq();
		
		logger.info("Azure_ETL - End Process.");
	}
	
	private void registerService(ApplicationContext context, String name) {
		this.setService(context.getBean(name, AzureService.class));
	}
	
	public void processReq() {	
		logger.info("Start program - Import ETL Log.");
		try {
			this.getService().processEdisonLog(this.getUserName());
		} catch (CSFEException e) {
			logger.error(e);
			this.handleSendErrorNotification(e);
		}
	}
	
	protected void handleSendErrorNotification(Exception e) {
		((WarningNotification)this.getWarningNotification()).setException(e);
		this.getWarningNotification().saveDeliveryReq();
	}
}
