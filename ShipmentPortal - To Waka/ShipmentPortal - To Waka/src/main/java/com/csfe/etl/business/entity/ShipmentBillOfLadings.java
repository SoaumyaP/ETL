package com.csfe.etl.business.entity;

import java.util.Date;

public class ShipmentBillOfLadings {
	private String ShipmentId;
	private String BillOfLadingId;
	private String RowVersion;
	private String CreatedBy;
	private String CreatedDate;
	private String UpdatedBy;
	private String UpdatedDate;
	
	public ShipmentBillOfLadings() {
		super();
	}

	public String getShipmentId() {
		return ShipmentId;
	}

	public void setShipmentId(String shipmentId) {
		ShipmentId = shipmentId;
	}

	public String getBillOfLadingId() {
		return BillOfLadingId;
	}

	public void setBillOfLadingId(String billOfLadingId) {
		BillOfLadingId = billOfLadingId;
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
		return "ShipmentBillOfLadings [ShipmentId=" + ShipmentId + ", BillOfLadingId=" + BillOfLadingId
				+ ", RowVersion=" + RowVersion + ", CreatedBy=" + CreatedBy + ", CreatedDate=" + CreatedDate
				+ ", UpdatedBy=" + UpdatedBy + ", UpdatedDate=" + UpdatedDate + "]";
	}
}
