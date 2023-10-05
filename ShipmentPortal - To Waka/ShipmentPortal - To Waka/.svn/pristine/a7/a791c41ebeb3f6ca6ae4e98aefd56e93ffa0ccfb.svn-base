package com.csfe.common;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import com.csfe.common.business.entity.DeliveryRequest;
import com.csfe.common.business.service.EmailNotificationService;

public abstract class EmailNotification {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	private DeliveryRequest deliveryReqClone;
	private DeliveryRequest deliveryReq;

	private String autoReplyContent;

	@Value("${mailServer.AutoReplyMsg}")
	private String autoReplyMsg;
	
    @Autowired
    @Qualifier("EmailNotificationService")
    private EmailNotificationService emailService;
	
	public EmailNotification() {
		super();
	}

	public String getAutoReplyMsg() {
		return autoReplyMsg;
	}

	public String getAutoReplyContent() {
		return autoReplyContent;
	}

	public void setAutoReplyContent(String autoReplyContent) {
		this.autoReplyContent = autoReplyContent;
	}

	public DeliveryRequest getDeliveryReq() {
		return deliveryReq;
	}

	public void setDeliveryReq(DeliveryRequest deliveryReq) {
		this.deliveryReq = deliveryReq;
		this.setDeliveryReqClone(deliveryReq.clone());
	}

	public DeliveryRequest getDeliveryReqClone() {
		return deliveryReqClone;
	}

	public void setDeliveryReqClone(DeliveryRequest deliveryReqClone) {
		this.deliveryReqClone = deliveryReqClone;
	}

	public void setAutoReplyMsg(String autoReplyMsg) {
		this.autoReplyMsg = autoReplyMsg;
	}

	public EmailNotificationService getEmailService() {
		return emailService;
	}

	public void setEmailService(EmailNotificationService emailService) {
		this.emailService = emailService;
	}
	
	public void saveDeliveryReq() {
		try {
			this.getEmailService().saveDelivery(this.getDeliveryReq());
		} catch (CSFEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Delivery notification problem.", e);
		}
	}
	
	public void cloneDelivery() {
		this.setDeliveryReq(this.getDeliveryReqClone().clone());
	}

}
