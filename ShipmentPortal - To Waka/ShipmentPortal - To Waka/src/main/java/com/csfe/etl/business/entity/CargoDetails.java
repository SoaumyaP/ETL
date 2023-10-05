package com.csfe.etl.business.entity;

import java.util.Date;

public class CargoDetails {
	private long Id;
	private String RowVersion;
	private String CreatedBy;
	private String CreatedDate;
	private String UpdatedBy;
	private String UpdatedDate;
	private String ShipmentId;
	private String OrderType;
	private String Sequence;
	private String ShippingMarks;
	private String Description;
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
	private String Commodity;
	private String HSCode;
	private String ProductNumber;
	private String CountryOfOrigin;

	public CargoDetails() {
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

	public String getOrderType() {
		return OrderType;
	}

	public void setOrderType(String orderType) {
		OrderType = orderType;
	}

	public String getSequence() {
		return Sequence;
	}

	public void setSequence(String sequence) {
		Sequence = sequence;
	}

	public String getShippingMarks() {
		return ShippingMarks;
	}

	public void setShippingMarks(String shippingMarks) {
		ShippingMarks = shippingMarks;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
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

	public String getCommodity() {
		return Commodity;
	}

	public void setCommodity(String commodity) {
		Commodity = commodity;
	}

	public String getHSCode() {
		return HSCode;
	}

	public void setHSCode(String hSCode) {
		HSCode = hSCode;
	}

	public String getProductNumber() {
		return ProductNumber;
	}

	public void setProductNumber(String productNumber) {
		ProductNumber = productNumber;
	}

	public String getCountryOfOrigin() {
		return CountryOfOrigin;
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		CountryOfOrigin = countryOfOrigin;
	}

	@Override
	public String toString() {
		return "CargoDetails [Id=" + Id + ", RowVersion=" + RowVersion + ", CreatedBy=" + CreatedBy + ", CreatedDate="
				+ CreatedDate + ", UpdatedBy=" + UpdatedBy + ", UpdatedDate=" + UpdatedDate + ", ShipmentId="
				+ ShipmentId + ", OrderType=" + OrderType + ", Sequence=" + Sequence + ", ShippingMarks="
				+ ShippingMarks + ", Description=" + Description + ", Unit=" + Unit + ", UnitUOM=" + UnitUOM
				+ ", Package=" + Package + ", PackageUOM=" + PackageUOM + ", Volume=" + Volume + ", VolumeUOM="
				+ VolumeUOM + ", GrossWeight=" + GrossWeight + ", GrossWeightUOM=" + GrossWeightUOM + ", NetWeight="
				+ NetWeight + ", NetWeightUOM=" + NetWeightUOM + ", Commodity=" + Commodity + ", HSCode=" + HSCode
				+ ", ProductNumber=" + ProductNumber + ", CountryOfOrigin=" + CountryOfOrigin + "]";
	}
}
