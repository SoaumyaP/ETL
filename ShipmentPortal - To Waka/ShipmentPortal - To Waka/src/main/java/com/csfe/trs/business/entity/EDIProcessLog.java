package com.csfe.trs.business.entity;

import java.util.Date;

public class EDIProcessLog {
	
	private long id;
	private String channel;
	private String projectID;
	private String ptype;
	private String paction;
	private String refClass;
	private String refID;
	private String sysID;
	private String path;
	private String filename;
	private String displayFilename;
	private String ext;
	private String errCode;
	private String remarks;
	private String status;
    private String createdBy;	
    private String updatedBy; 
    private Date createdOn;
	private Date updatedOn;
	
	public EDIProcessLog() {
		super();
		init();
	}
	
	public EDIProcessLog(POFulfillment input, String projectID, String pType, String refClass, String sysid, String creator) {
		super();
		init();
		this.setCreatedBy(creator);
		this.setUpdatedBy(creator);
		this.setRefID(input.getBookingReferenceNo());
		
		this.setProjectID(projectID);
		this.setPtype(pType);
		this.setRefClass(refClass);
		this.setSysID(sysid);
	}

	public void init() {
		this.setChannel("OUT");
		this.setPaction("ADD");
		this.setStatus("DONE");
		this.setErrCode("");
		this.setRemarks(null);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getPtype() {
		return ptype;
	}

	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	public String getPaction() {
		return paction;
	}

	public void setPaction(String paction) {
		this.paction = paction;
	}

	public String getRefClass() {
		return refClass;
	}

	public void setRefClass(String refClass) {
		this.refClass = refClass;
	}

	public String getRefID() {
		return refID;
	}

	public void setRefID(String refID) {
		this.refID = refID;
	}

	public String getSysID() {
		return sysID;
	}

	public void setSysID(String sysID) {
		this.sysID = sysID;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDisplayFilename() {
		return displayFilename;
	}

	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return "EDIProcessLog [channel=" + channel + ", projectID=" + projectID + ", ptype=" + ptype + ", paction="
				+ paction + ", refClass=" + refClass + ", refID=" + refID + ", sysID=" + sysID + ", path=" + path
				+ ", filename=" + filename + ", displayFilename=" + displayFilename + ", ext=" + ext + ", errCode="
				+ errCode + ", remarks=" + remarks + ", status=" + status + ", createdBy=" + createdBy + ", updatedBy="
				+ updatedBy + ", updatedOn=" + updatedOn + "]";
	}

	
}
