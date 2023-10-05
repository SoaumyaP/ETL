package com.csfe.common.business.dao.delivery;

import java.util.List;

import com.csfe.common.CSFEException;
import com.csfe.common.business.entity.DeliveryRequest;

public interface DeliveryRequestDAO {
	//Query
    public abstract DeliveryRequest find(int id);
    public abstract List<DeliveryRequest> getPendingDelivery() throws CSFEException;

    //Insert / Update
    public abstract void save(Object deliveryReq) throws CSFEException;
    public abstract void update(Object deliveryReq) throws CSFEException;
}
