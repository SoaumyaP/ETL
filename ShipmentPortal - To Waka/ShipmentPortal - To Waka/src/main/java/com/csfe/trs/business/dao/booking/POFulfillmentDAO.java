package com.csfe.trs.business.dao.booking;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.csfe.common.CSFEException;
import com.csfe.trs.business.entity.POFulfillment;

public interface POFulfillmentDAO {
	public abstract List<POFulfillment> findCNFMBooking(@Param("DataSource")String dataSource, @Param("CreatedOn")String createdOn) throws CSFEException;
}
