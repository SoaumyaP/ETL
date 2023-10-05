package com.csfe.edison.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.csfe.common.CSFEException;
import com.csfe.edison.business.entity.EdisonLog;

public interface EdisonLogDAO {
	//Query
    public abstract EdisonLog findById(int id);
    public abstract List<EdisonLog> findEdisonLog(@Param("db")String db, @Param("sysname")String sysname, @Param("companyCode")List companyCode, @Param("pocompanyCode")List pocompanyCode, @Param("list")List list) throws CSFEException;
    public abstract List<EdisonLog> findPendingEdisonLog(@Param("db")String db, @Param("sysname")String sysname) throws CSFEException;
    public abstract List<EdisonLog> findDeleteEdisonLog (@Param("db")String db, @Param("sysname")String sysname) throws CSFEException;
    public abstract EdisonLog findEdisonIDBySONO (@Param("db")String db, @Param("sysname")String sysname, @Param("sono")String sono, @Param("soseq")String soseq, @Param("type")String type) throws CSFEException;
    
    //Insert / Update
    public abstract void insertEdisonLog(Object log) throws CSFEException;
    public abstract void updateEdisonLog(Object log) throws CSFEException;
}
