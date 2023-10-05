package com.csfe.edison.business.entity;

import java.util.Date;

import com.csfe.common.ConstantsUtil;

public class EdisonLog {
	
	private long id;
	private String action;
	private String dbname;
	private String code;
	private String code2;
	private int seq;
	private String codetype;
	private String rowVersion;
	private String code3;
	private Date createdate;
	private String EdiData;
	private String IsETLtoAzure;
	private String status;
	private Date lastupdate;
	private long AzureId;
	private String AzureMsg;
	private Date AzureLastupdate;
	private Date edisonlastupdate;
	private String sysname;
	
	public EdisonLog() {
		super();
		this.setId(0);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode2() {
		return code2;
	}

	public void setCode2(String code2) {
		this.code2 = code2;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getCodetype() {
		return codetype;
	}

	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}

	public String getRowVersion() {
		return rowVersion;
	}

	public void setRowVersion(String rowVersion) {
		this.rowVersion = rowVersion;
	}

	public String getCode3() {
		return code3;
	}

	public void setCode3(String code3) {
		this.code3 = code3;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public String getEdiData() {
		return EdiData;
	}

	public void setEdiData(String ediData) {
		EdiData = ediData;
	}

	public String getIsETLtoAzure() {
		return IsETLtoAzure;
	}

	public void setIsETLtoAzure(String isETLtoAzure) {
		IsETLtoAzure = isETLtoAzure;
	}
	
	public void setIsETLtoAzure(boolean isETLtoAzure) {
		IsETLtoAzure = isETLtoAzure?ConstantsUtil.DEFAULT_YES:ConstantsUtil.DEFAULT_NO;
	}
	
	public boolean IsETLtoAzure() {
		return ConstantsUtil.DEFAULT_YES.equals(IsETLtoAzure);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	public long getAzureId() {
		return AzureId;
	}

	public void setAzureId(long azureId) {
		AzureId = azureId;
	}

	public String getAzureMsg() {
		return AzureMsg;
	}

	public void setAzureMsg(String azureMsg) {
		AzureMsg = azureMsg;
	}

	public Date getAzureLastupdate() {
		return AzureLastupdate;
	}

	public void setAzureLastupdate(Date azureLastupdate) {
		AzureLastupdate = azureLastupdate;
	}

	public Date getEdisonlastupdate() {
		return edisonlastupdate;
	}

	public void setEdisonlastupdate(Date edisonlastupdate) {
		this.edisonlastupdate = edisonlastupdate;
	}

	public String getSysname() {
		return sysname;
	}

	public void setSysname(String sysname) {
		this.sysname = sysname;
	}

	@Override
	public String toString() {
		return "EdisonLog [id=" + id + ", action=" + action + ", dbname=" + dbname + ", code=" + code + ", code2="
				+ code2 + ", seq=" + seq + ", codetype=" + codetype + ", rowVersion=" + rowVersion + ", code3=" + code3
				+ ", createdate=" + createdate + ", EdiData=" + EdiData + ", IsETLtoAzure=" + IsETLtoAzure + ", status="
				+ status + ", lastupdate=" + lastupdate + ", AzureId=" + AzureId + ", AzureMsg=" + AzureMsg
				+ ", AzureLastupdate=" + AzureLastupdate + ", edisonlastupdate=" + edisonlastupdate + "]";
	}
}
