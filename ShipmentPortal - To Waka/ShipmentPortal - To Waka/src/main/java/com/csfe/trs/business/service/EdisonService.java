package com.csfe.trs.business.service;

import com.csfe.common.CSFEException;
import com.csfe.trs.business.entity.Vesvoyforwarder;

public interface EdisonService {
	public abstract Vesvoyforwarder findVesvoyforwarderBySONO(String sysid, String sono) throws CSFEException;
}
