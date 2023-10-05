package com.csfe.etl.business.service;

import com.csfe.common.CSFEException;

public interface AzureService {
	
	public abstract void processEdisonLog(String creator) throws CSFEException;
	public abstract void processAzure(String creator) throws CSFEException;
}
