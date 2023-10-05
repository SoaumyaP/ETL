package com.csfe.etl.business.entity;

import java.util.Date;

public class ShipmentLoadDetails {
	private long Id;
	private String RowVersion;
	private String CreatedBy;
	private String CreatedDate;
	private String UpdatedBy;
	private String UpdatedDate;
	private String ShipmentId;
	private String ConsignmentId;
	private String CargoDetailId;
	private String ShipmentLoadId;
	private String ContainerId;
	private String ConsolidationId;
	private String Sequence;
	private String Package;
	private String PackageUOM;
	private String Unit;
	private String UnitUOM;
	private String Volume;
	private String VolumeUOM;
	private String GrossWeight;
	private String GrossWeightUOM;
	private String NetWeight;
	private String NetWeightUOM;
	
	public ShipmentLoadDetails() {
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

	public String getConsignmentId() {
		return ConsignmentId;
	}

	public void setConsignmentId(String consignmentId) {
		ConsignmentId = consignmentId;
	}

	public String getCargoDetailId() {
		return CargoDetailId;
	}

	public void setCargoDetailId(String cargoDetailId) {
		CargoDetailId = cargoDetailId;
	}

	public String getShipmentLoadId() {
		return ShipmentLoadId;
	}

	public void setShipmentLoadId(String shipmentLoadId) {
		ShipmentLoadId = shipmentLoadId;
	}

	public String getContainerId() {
		return ContainerId;
	}

	public void setContainerId(String containerId) {
		ContainerId = containerId;
	}

	public String getConsolidationId() {
		return ConsolidationId;
	}

	public void setConsolidationId(String consolidationId) {
		ConsolidationId = consolidationId;
	}

	public String getSequence() {
		return Sequence;
	}

	public void setSequence(String sequence) {
		Sequence = sequence;
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

	@Override
	public String toString() {
		return "ShipmentLoadDetails [Id=" + Id + ", RowVersion=" + RowVersion + ", CreatedBy=" + CreatedBy
				+ ", CreatedDate=" + CreatedDate + ", UpdatedBy=" + UpdatedBy + ", UpdatedDate=" + UpdatedDate
				+ ", ShipmentId=" + ShipmentId + ", ConsignmentId=" + ConsignmentId + ", CargoDetailId=" + CargoDetailId
				+ ", ShipmentLoadId=" + ShipmentLoadId + ", ContainerId=" + ContainerId + ", ConsolidationId="
				+ ConsolidationId + ", Sequence=" + Sequence + ", Package=" + Package + ", PackageUOM=" + PackageUOM
				+ ", Unit=" + Unit + ", UnitUOM=" + UnitUOM + ", Volume=" + Volume + ", VolumeUOM=" + VolumeUOM
				+ ", GrossWeight=" + GrossWeight + ", GrossWeightUOM=" + GrossWeightUOM + ", NetWeight=" + NetWeight
				+ ", NetWeightUOM=" + NetWeightUOM + "]";
	}
}
