package com.csfe.trs.business.dao.edison;

import org.apache.ibatis.annotations.Param;

import com.csfe.common.CSFEException;
import com.csfe.trs.business.entity.Vesvoyforwarder;

public interface EdisonDAO {
	public abstract Vesvoyforwarder findVesvoyforwarderBySONO(@Param("SYSID")String sysid, @Param("SONO")String sono) throws CSFEException;
}
