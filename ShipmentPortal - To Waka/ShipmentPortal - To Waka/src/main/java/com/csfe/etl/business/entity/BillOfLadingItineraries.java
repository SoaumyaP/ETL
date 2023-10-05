package com.csfe.etl.business.entity;

import java.util.Date;

public class BillOfLadingItineraries {
	private String ItineraryId;
	private String BillOfLadingId;
	private String MasterBillOfLadingId;
	private String RowVersion;
	private String CreatedBy;
	private String CreatedDate;
	private String UpdatedBy;
	private String UpdatedDate;
	
	public BillOfLadingItineraries() {
		super();
	}

	public String getItineraryId() {
		return ItineraryId;
	}

	public void setItineraryId(String itineraryId) {
		ItineraryId = itineraryId;
	}

	public String getBillOfLadingId() {
		return BillOfLadingId;
	}

	public void setBillOfLadingId(String billOfLadingId) {
		BillOfLadingId = billOfLadingId;
	}

	public String getMasterBillOfLadingId() {
		return MasterBillOfLadingId;
	}

	public void setMasterBillOfLadingId(String masterBillOfLadingId) {
		MasterBillOfLadingId = masterBillOfLadingId;
	}

	public String getRowVersion() {
		return RowVersion;
	}

	public void setRowVersion(String rowVersion) {
		RowVersion = rowVersion;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}

	public String getUpdatedBy() {
		return UpdatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		UpdatedBy = updatedBy;
	}

	public String getUpdatedDate() {
		return UpdatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		UpdatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "BillOfLadingItineraries [ItineraryId=" + ItineraryId + ", BillOfLadingId=" + BillOfLadingId
				+ ", MasterBillOfLadingId=" + MasterBillOfLadingId + ", RowVersion=" + RowVersion + ", CreatedBy="
				+ CreatedBy + ", CreatedDate=" + CreatedDate + ", UpdatedBy=" + UpdatedBy + ", UpdatedDate="
				+ UpdatedDate + "]";
	}
}
