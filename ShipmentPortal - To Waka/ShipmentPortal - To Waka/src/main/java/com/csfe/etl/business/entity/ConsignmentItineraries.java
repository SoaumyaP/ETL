package com.csfe.etl.business.entity;

import java.util.Date;

public class ConsignmentItineraries {
	
	private String ConsignmentId;
	private String ItineraryId;
	private String RowVersion;
	private String ShipmentId;
	private String MasterBillId;
	private String Sequence;
	private String CreatedBy;
	private String CreatedDate;
	private String UpdatedBy;
	private String UpdatedDate;
	
	public ConsignmentItineraries() {
		super();
	}

	public String getConsignmentId() {
		return ConsignmentId;
	}

	public void setConsignmentId(String consignmentId) {
		ConsignmentId = consignmentId;
	}

	public String getItineraryId() {
		return ItineraryId;
	}

	public void setItineraryId(String itineraryId) {
		ItineraryId = itineraryId;
	}

	public String getRowVersion() {
		return RowVersion;
	}

	public void setRowVersion(String rowVersion) {
		RowVersion = rowVersion;
	}

	public String getShipmentId() {
		return ShipmentId;
	}

	public void setShipmentId(String shipmentId) {
		ShipmentId = shipmentId;
	}

	public String getMasterBillId() {
		return MasterBillId;
	}

	public void setMasterBillId(String masterBillId) {
		MasterBillId = masterBillId;
	}

	public String getSequence() {
		return Sequence;
	}

	public void setSequence(String sequence) {
		Sequence = sequence;
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
		return "ConsignmentItineraries [ConsignmentId=" + ConsignmentId + ", ItineraryId=" + ItineraryId
				+ ", RowVersion=" + RowVersion + ", ShipmentId=" + ShipmentId + ", MasterBillId=" + MasterBillId
				+ ", Sequence=" + Sequence + ", CreatedBy=" + CreatedBy + ", CreatedDate=" + CreatedDate
				+ ", UpdatedBy=" + UpdatedBy + ", UpdatedDate=" + UpdatedDate + "]";
	}
}
