package com.csfe.common.business.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.csfe.common.CSFEException;
import com.csfe.common.business.dao.delivery.DeliveryRequestDAO;
import com.csfe.common.business.entity.DeliveryRequest;

@Service("EmailNotificationService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "emailTransactionManager")
public class EmailNotificationServiceImpl implements EmailNotificationService {

	private Logger logger = Logger.getLogger(this.getClass());
    
    private DeliveryRequestDAO deliveryRequestDAO;
    
    public Logger getLogger() {
		return logger;
	}

	@Autowired
    @Qualifier("DeliveryRequestDAO")
	public void setDeliveryRequestDAO(DeliveryRequestDAO deliveryRequestDAO) {
		this.deliveryRequestDAO = deliveryRequestDAO;
	}

	public DeliveryRequestDAO getDeliveryRequestDAO() {
		return deliveryRequestDAO;
	}
	
	//Public Methods
	@Override
	public List<DeliveryRequest> getPendingDelivery() throws CSFEException {
		// TODO Auto-generated method stub
		return this.getDeliveryRequestDAO().getPendingDelivery();
	}
	
	@Override
	public void saveDelivery(DeliveryRequest req) throws CSFEException {
		// TODO Auto-generated method stub
		try {
			if(req.getReqID() > 0) {
				this.getDeliveryRequestDAO().update(req);
			} else {
				this.getDeliveryRequestDAO().save(req);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CSFEException("Delivery request service problem.", e);
		}
	}
}
