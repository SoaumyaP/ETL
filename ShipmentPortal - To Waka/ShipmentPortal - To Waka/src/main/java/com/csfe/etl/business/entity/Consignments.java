package com.csfe.etl.business.entity;

import java.util.Date;

public class Consignments {
	private long Id;
	private String RowVersion;
	private String CreatedBy;
	private String CreatedDate;
	private String UpdatedBy;
	private String UpdatedDate;
	private String ShipmentId;
	private String ConsignmentType;
	private String ConsignmentDate;
	private String ExecutionAgentId;
	private String ShipFrom;
	private String ShipFromETDDate;
	private String ShipTo;
	private String ShipToETADate;
	private String Status;
	private String ModeOfTransport;
	private String Movement;
	private String Unit;
	private String UnitUOM;
	private String Package;
	private String PackageUOM;
	private String Volume;
	private String VolumeUOM;
	private String GrossWeight;
	private String GrossWeightUOM;
	private String NetWeight;
	private String NetWeightUOM;
	private String HouseBillId;
	private String MasterBillId;
	private String TriangleTradeFlag;
	private String MemoBOLFlag;
	private String Sequence;
	
	public Consignments() {
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

	public String getShipmentId() {
		return ShipmentId;
	}

	public void setShipmentId(String shipmentId) {
		ShipmentId = shipmentId;
	}

	public String getConsignmentType() {
		return ConsignmentType;
	}

	public void setConsignmentType(String consignmentType) {
		ConsignmentType = consignmentType;
	}

	public String getConsignmentDate() {
		return ConsignmentDate;
	}

	public void setConsignmentDate(String consignmentDate) {
		ConsignmentDate = consignmentDate;
	}

	public String getExecutionAgentId() {
		return ExecutionAgentId;
	}

	public void setExecutionAgentId(String executionAgentId) {
		ExecutionAgentId = executionAgentId;
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

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getModeOfTransport() {
		return ModeOfTransport;
	}

	public void setModeOfTransport(String modeOfTransport) {
		ModeOfTransport = modeOfTransport;
	}

	public String getMovement() {
		return Movement;
	}

	public void setMovement(String movement) {
		Movement = movement;
	}

	public String getUnit() {
		return Unit;
	}

	public void setUnit(String unit) {
		Unit = unit;
	}

	public String getUnitUOM() {
		return UnitUOM;
	}

	public void setUnitUOM(String unitUOM) {
		UnitUOM = unitUOM;
	}

	public String getPackage() {
		return Package;
	}

	public void setPackage(String package1) {
		Package = package1;
	}

	public String getPackageUOM() {
		return PackageUOM;
	}

	public void setPackageUOM(String packageUOM) {
		PackageUOM = packageUOM;
	}

	public String getVolume() {
		return Volume;
	}

	public void setVolume(String volume) {
		Volume = volume;
	}

	public String getVolumeUOM() {
		return VolumeUOM;
	}

	public void setVolumeUOM(String volumeUOM) {
		VolumeUOM = volumeUOM;
	}

	public String getGrossWeight() {
		return GrossWeight;
	}

	public void setGrossWeight(String grossWeight) {
		GrossWeight = grossWeight;
	}

	public String getGrossWeightUOM() {
		return GrossWeightUOM;
	}

	public void setGrossWeightUOM(String grossWeightUOM) {
		GrossWeightUOM = grossWeightUOM;
	}

	public String getNetWeight() {
		return NetWeight;
	}

	public void setNetWeight(String netWeight) {
		NetWeight = netWeight;
	}

	public String getNetWeightUOM() {
		return NetWeightUOM;
	}

	public void setNetWeightUOM(String netWeightUOM) {
		NetWeightUOM = netWeightUOM;
	}

	public String getHouseBillId() {
		return HouseBillId;
	}

	public void setHouseBillId(String houseBillId) {
		HouseBillId = houseBillId;
	}

	public String getMasterBillId() {
		return MasterBillId;
	}

	public void setMasterBillId(String masterBillId) {
		MasterBillId = masterBillId;
	}

	public String getTriangleTradeFlag() {
		return TriangleTradeFlag;
	}

	public void setTriangleTradeFlag(String triangleTradeFlag) {
		TriangleTradeFlag = triangleTradeFlag;
	}

	public String getMemoBOLFlag() {
		return MemoBOLFlag;
	}

	public void setMemoBOLFlag(String memoBOLFlag) {
		MemoBOLFlag = memoBOLFlag;
	}

	public String getSequence() {
		return Sequence;
	}

	public void setSequence(String sequence) {
		Sequence = sequence;
	}

	@Override
	public String toString() {
		return "Consignments [Id=" + Id + ", RowVersion=" + RowVersion + ", CreatedBy=" + CreatedBy + ", CreatedDate="
				+ CreatedDate + ", UpdatedBy=" + UpdatedBy + ", UpdatedDate=" + UpdatedDate + ", ShipmentId="
				+ ShipmentId + ", ConsignmentType=" + ConsignmentType + ", ConsignmentDate=" + ConsignmentDate
				+ ", ExecutionAgentId=" + ExecutionAgentId + ", ShipFrom=" + ShipFrom + ", ShipFromETDDate="
				+ ShipFromETDDate + ", ShipTo=" + ShipTo + ", ShipToETADate=" + ShipToETADate + ", Status=" + Status
				+ ", ModeOfTransport=" + ModeOfTransport + ", Movement=" + Movement + ", Unit=" + Unit + ", UnitUOM="
				+ UnitUOM + ", Package=" + Package + ", PackageUOM=" + PackageUOM + ", Volume=" + Volume
				+ ", VolumeUOM=" + VolumeUOM + ", GrossWeight=" + GrossWeight + ", GrossWeightUOM=" + GrossWeightUOM
				+ ", NetWeight=" + NetWeight + ", NetWeightUOM=" + NetWeightUOM + ", HouseBillId=" + HouseBillId
				+ ", MasterBillId=" + MasterBillId + ", TriangleTradeFlag=" + TriangleTradeFlag + ", MemoBOLFlag="
				+ MemoBOLFlag + ", Sequence=" + Sequence + "]";
	}

}
