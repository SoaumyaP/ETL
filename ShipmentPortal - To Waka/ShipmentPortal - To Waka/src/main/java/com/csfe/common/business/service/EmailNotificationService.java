package com.csfe.common.business.service;

import java.util.List;

import com.csfe.common.CSFEException;
import com.csfe.common.business.entity.DeliveryRequest;

public interface EmailNotificationService {
	
	//Query
    public abstract List<DeliveryRequest> getPendingDelivery() throws CSFEException;
	
	//Insert / Update
	public abstract void saveDelivery(DeliveryRequest req) throws CSFEException;	
	
}
