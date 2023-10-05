package com.csfe.trs.business.main;

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
import com.csfe.trs.business.service.TRSService;


@Component
public class TRSConfirmRequest {
	private static Logger logger = Logger.getLogger(TRSConfirmRequest.class);
	
	@Value("${System.UserID:}")
	private String userName = "";
	
	@Value("${System.ServiceHandler:}")
	private String serviceName = "";
	
	@Autowired
	@Qualifier("WarningNotification")
	private EmailNotification warningNotification;
	
	private TRSService service;
	
	public EmailNotification getWarningNotification() {
		return warningNotification;
	}
	
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public TRSService getService() {
		return service;
	}

	public void setService(TRSService service) {
		this.service = service;
	}
	
	public String getUserName() {
		return userName;
	}

	public static void main(String args[]) {
		logger.info("TRS Booking confirmation - Start Process\n");
		ApplicationContext context = new ClassPathXmlApplicationContext(args[0]);
		TRSConfirmRequest main = context.getBean(TRSConfirmRequest.class);
		
		main.registerService(context, main.getServiceName());
		main.processReq();
		
		logger.info("TRS Booking confirmation - End Process.");
	}
	
	private void registerService(ApplicationContext context, String name) {
		this.setService(context.getBean(name, TRSService.class));
	}
	
	public void processReq() {	
		logger.info("Start program - Import TRS confirm data.");
		try {
			this.getService().processTRSConfirmation(this.getUserName());
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
