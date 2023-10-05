package com.csfe.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.csfe.common.business.entity.DeliveryRequest;

@Component("WarningNotification")
public class WarningNotification extends EmailNotification {
	
	private CSFEException exception;
	
	public WarningNotification() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Value("${mailServer.EmailBody}")
	public void setAutoReplyContent(String autoReplyContent) {
		super.setAutoReplyContent(autoReplyContent);
	}

	@Autowired
    @Qualifier("WarningDeliveryReq")
	public void setDeliveryReq(DeliveryRequest deliveryReq) {
		super.setDeliveryReq(deliveryReq);
	}
	
	public CSFEException getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.cloneDelivery();
		this.exception = new CSFEException(exception);
	}

	@Override
	public void saveDeliveryReq() {
		// TODO Auto-generated method stub
		String content = this.getAutoReplyContent();
		content += this.getException() + "\n\n";
		content += this.getAutoReplyMsg();
		
		this.getDeliveryReq().setEmailContent(content);
		super.saveDeliveryReq();
	}
}
