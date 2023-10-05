package com.csfe.etl.business.entity;

import java.util.Date;

public class BillOfLadings {
	private long Id;
	private String RowVersion;
	private String CreatedBy;
	private String CreatedDate;
	private String UpdatedBy;
	private String UpdatedDate;
	private String BillOfLadingNo;
	private String ExecutionAgentId;
	private String BillOfLadingType;
	private String MainCarrier;
	private String MainVessel;
	private String TotalGrossWeight;
	private String TotalGrossWeightUOM;
	private String TotalNetWeight;
	private String TotalNetWeightUOM;
	private String TotalPackage;
	private String TotalPackageUOM;
	private String TotalVolume;
	private String TotalVolumeUOM;
	private String JobNumber;
	private String IssueDate;
	private String ModeOfTransport;
	private String ShipFrom;
	private String ShipFromETDDate;
	private String ShipTo;
	private String ShipToETADate;
	private String Movement;
	private String Incoterm;
	
	public BillOfLadings() {
		super();
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
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

	public String getBillOfLadingNo() {
		return BillOfLadingNo;
	}

	public void setBillOfLadingNo(String billOfLadingNo) {
		BillOfLadingNo = billOfLadingNo;
	}

	public String getExecutionAgentId() {
		return ExecutionAgentId;
	}

	public void setExecutionAgentId(String executionAgentId) {
		ExecutionAgentId = executionAgentId;
	}

	public String getBillOfLadingType() {
		return BillOfLadingType;
	}

	public void setBillOfLadingType(String billOfLadingType) {
		BillOfLadingType = billOfLadingType;
	}

	public String getMainCarrier() {
		return MainCarrier;
	}

	public void setMainCarrier(String mainCarrier) {
		MainCarrier = mainCarrier;
	}

	public String getMainVessel() {
		return MainVessel;
	}

	public void setMainVessel(String mainVessel) {
		MainVessel = mainVessel;
	}

	public String getTotalGrossWeight() {
		return TotalGrossWeight;
	}

	public void setTotalGrossWeight(String totalGrossWeight) {
		TotalGrossWeight = totalGrossWeight;
	}

	public String getTotalGrossWeightUOM() {
		return TotalGrossWeightUOM;
	}

	public void setTotalGrossWeightUOM(String totalGrossWeightUOM) {
		TotalGrossWeightUOM = totalGrossWeightUOM;
	}

	public String getTotalNetWeight() {
		return TotalNetWeight;
	}

	public void setTotalNetWeight(String totalNetWeight) {
		TotalNetWeight = totalNetWeight;
	}

	public String getTotalNetWeightUOM() {
		return TotalNetWeightUOM;
	}

	public void setTotalNetWeightUOM(String totalNetWeightUOM) {
		TotalNetWeightUOM = totalNetWeightUOM;
	}

	public String getTotalPackage() {
		return TotalPackage;
	}

	public void setTotalPackage(String totalPackage) {
		TotalPackage = totalPackage;
	}

	public String getTotalPackageUOM() {
		return TotalPackageUOM;
	}

	public void setTotalPackageUOM(String totalPackageUOM) {
		TotalPackageUOM = totalPackageUOM;
	}

	public String getTotalVolume() {
		return TotalVolume;
	}

	public void setTotalVolume(String totalVolume) {
		TotalVolume = totalVolume;
	}

	public String getTotalVolumeUOM() {
		return TotalVolumeUOM;
	}

	public void setTotalVolumeUOM(String totalVolumeUOM) {
		TotalVolumeUOM = totalVolumeUOM;
	}

	public String getJobNumber() {
		return JobNumber;
	}

	public void setJobNumber(String jobNumber) {
		JobNumber = jobNumber;
	}

	public String getIssueDate() {
		return IssueDate;
	}

	public void setIssueDate(String issueDate) {
		IssueDate = issueDate;
	}

	public String getModeOfTransport() {
		return ModeOfTransport;
	}

	public void setModeOfTransport(String modeOfTransport) {
		ModeOfTransport = modeOfTransport;
	}

	public String getShipFrom() {
		return ShipFrom;
	}

	public void setShipFrom(String shipFrom) {
		ShipFrom = shipFrom;
	}

	public String getShipFromETDDate() {
		return ShipFromETDDate;
	}

	public void setShipFromETDDate(String shipFromETDDate) {
		ShipFromETDDate = shipFromETDDate;
	}

	public String getShipTo() {
		return ShipTo;
	}

	public void setShipTo(String shipTo) {
		ShipTo = shipTo;
	}

	public String getShipToETADate() {
		return ShipToETADate;
	}

	public void setShipToETADate(String shipToETADate) {
		ShipToETADate = shipToETADate;
	}

	public String getMovement() {
		return Movement;
	}

	public void setMovement(String movement) {
		Movement = movement;
	}

	public String getIncoterm() {
		return Incoterm;
	}

	public void setIncoterm(String incoterm) {
		Incoterm = incoterm;
	}

	@Override
	public String toString() {
		return "BillOfLadings [Id=" + Id + ", RowVersion=" + RowVersion + ", CreatedBy=" + CreatedBy + ", CreatedDate="
				+ CreatedDate + ", UpdatedBy=" + UpdatedBy + ", UpdatedDate=" + UpdatedDate + ", BillOfLadingNo="
				+ BillOfLadingNo + ", ExecutionAgentId=" + ExecutionAgentId + ", BillOfLadingType=" + BillOfLadingType
				+ ", MainCarrier=" + MainCarrier + ", MainVessel=" + MainVessel + ", TotalGrossWeight="
				+ TotalGrossWeight + ", TotalGrossWeightUOM=" + TotalGrossWeightUOM + ", TotalNetWeight="
				+ TotalNetWeight + ", TotalNetWeightUOM=" + TotalNetWeightUOM + ", TotalPackage=" + TotalPackage
				+ ", TotalPackageUOM=" + TotalPackageUOM + ", TotalVolume=" + TotalVolume + ", TotalVolumeUOM="
				+ TotalVolumeUOM + ", JobNumber=" + JobNumber + ", IssueDate=" + IssueDate + ", ModeOfTransport="
				+ ModeOfTransport + ", ShipFrom=" + ShipFrom + ", ShipFromETDDate=" + ShipFromETDDate + ", ShipTo="
				+ ShipTo + ", ShipToETADate=" + ShipToETADate + ", Movement=" + Movement + ", Incoterm=" + Incoterm
				+ "]";
	}

}
