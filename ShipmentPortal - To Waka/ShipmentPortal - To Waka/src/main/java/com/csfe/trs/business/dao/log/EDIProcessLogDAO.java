package com.csfe.trs.business.dao.log;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.csfe.common.CSFEException;
import com.csfe.trs.business.entity.EDIProcessLog;

public interface EDIProcessLogDAO {
	//Insert 
	public abstract void saveEDIProcessLog(Object log) throws CSFEException;
	public abstract List<EDIProcessLog> findEDIProcessLogByBKNO(@Param("list")Set list, @Param("ProjectID")String projectID, @Param("PType")String pType, @Param("RefClass")String refClass, @Param("SYSID")String sysid)throws CSFEException;
}