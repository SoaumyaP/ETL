package com.csfe.etl.business.entity.po;

import com.csfe.common.util.StringUtil;
import com.csfe.etl.business.entity.po.PurchaseOrders;

public class LineItems {
	private String POLineKey;
	private String ProductCode;
	private String ProductName;
	private String OrderedUnitQty;
	private String UnitUOM;
	private String UnitPrice;
	private String LineOrder;
	private String CurrencyCode;
	private String ProductFamily;
	private String HSCode;
	private String SupplierProductCode;
	private String MinPackageQty;
	private String MinOrderQty;
	private String Commodity;
	private String ReferenceNumber1;
	private String ReferenceNumber2;
	private String ShippingMarks;
	private String DescriptionOfGoods;
	private String PackagingInstruction;
	private String CountryCodeOfOrigin;
	private String ProductRemark;
	private String PackageUOM;
	private String ColourCode;
	
	private String SeasonCode;
	private String StyleNo;
	private String Size;
	private String GrossWeight;
	private String Volume;
	private String ScheduleLineNo;
	private String InboundDelivery;
	private String POItemReference;
	private String ShipmentNo;
	private String Plant;
	private String StorageLocation;
	private String MatGrpDe;
	private String MaterialType;
	private String SKU;
	private String GridValue;
	private String StockCategory;
	private String HeaderText;
	private String Length;
	private String Width;
	private String Height;
	private String NetWeight;
	private String FactoryName;
	
	public LineItems(PurchaseOrders po) {
		this.setPOLineKey(po.getPOLineKey());
		this.setProductCode(po.getProductCode());
		this.setProductName(po.getProductName());
		this.setProductFamily(po.getProductFamily());
		this.setProductRemark(this.getPromoCode(po.getProductRemark()));
		this.setOrderedUnitQty(po.getOrderedUnitQty());
		this.setUnitUOM(po.getUnitUOM());
		this.setUnitPrice(po.getUnitPrice());
		this.setLineOrder(po.getLineOrder());
		this.setCurrencyCode(po.getCurrencyCode());
		this.setHSCode(po.getHSCode());
		this.setSupplierProductCode(po.getSupplierProductCode());
		this.setMinOrderQty(po.getMinOrderQty());
		this.setMinPackageQty(po.getMinPackageQty());
		this.setCommodity(po.getCommodity());
		this.setReferenceNumber1(po.getReferenceNumber1());
		this.setReferenceNumber2(po.getReferenceNumber2());
		this.setShippingMarks(po.getShippingMarks());
		this.setDescriptionOfGoods(po.getDescriptionOfGoods());
		this.setPackagingInstruction(po.getPackagingInstruction());
		this.setCountryCodeOfOrigin(po.getCountryCodeOfOrigin());
		this.setPackageUOM(po.getPackageUOM());
		this.setColourCode(po.getColourCode());
		this.setSeasonCode(po.getSeasonCode());
		this.setStyleNo(po.getStyleNo());
		this.setSize(po.getSize());
		this.setGrossWeight(po.getItemGrossWeight());
		this.setVolume(po.getItemVolume());
		this.setScheduleLineNo(po.getScheduleLineNo());
		this.setInboundDelivery(po.getInboundDelivery());
		this.setPOItemReference(po.getPOItemReference());
		this.setShipmentNo(po.getShipmentNo());
		this.setPlant(po.getPlant());
		this.setStorageLocation(po.getStorageLocation());
		this.setMatGrpDe(po.getMatGrpDe());
		this.setMaterialType(po.getMaterialType());
		this.setSKU(po.getSKU());
		this.setGridValue(po.getGridValue());
		this.setStockCategory(po.getStockCategory());
		this.setHeaderText(po.getHeaderText());
		this.setLength(po.getLength());
		this.setWidth(po.getWidth());
		this.setHeight(po.getHeight());
		this.setNetWeight(po.getNetWeight());
		this.setFactoryName(po.getFactoryName());
	}
	
	public String getPromoCode(String value) {
		if(!StringUtil.isNullOrBlank(value)) {
			String[]temp = value.split(",");
			if(temp.length > 0) {
				for(int i = 0; temp.length > i ; i++) 
					if(temp[i].contains("PromoCode:"))	return temp[i];
			}
		}
		return null;
	}
	
	public String getPOLineKey() {
		return POLineKey;
	}

	public void setPOLineKey(String pOLineKey) {
		POLineKey = pOLineKey;
	}

	public String getProductCode() {
		return ProductCode;
	}
	public void setProductCode(String productCode) {
		ProductCode = productCode;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getOrderedUnitQty() {
		return OrderedUnitQty;
	}
	public void setOrderedUnitQty(String orderedUnitQty) {
		OrderedUnitQty = orderedUnitQty;
	}
	public String getUnitUOM() {
		return UnitUOM;
	}
	public void setUnitUOM(String unitUOM) {
		UnitUOM = unitUOM;
	}
	public String getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		UnitPrice = unitPrice;
	}
	public String getLineOrder() {
		return LineOrder;
	}
	public void setLineOrder(String lineOrder) {
		LineOrder = lineOrder;
	}
	public String getCurrencyCode() {
		return CurrencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		CurrencyCode = currencyCode;
	}
	public String getProductFamily() {
		return ProductFamily;
	}
	public void setProductFamily(String productFamily) {
		ProductFamily = productFamily;
	}
	public String getHSCode() {
		return HSCode;
	}
	public void setHSCode(String hSCode) {
		HSCode = hSCode;
	}
	public String getSupplierProductCode() {
		return SupplierProductCode;
	}
	public void setSupplierProductCode(String supplierProductCode) {
		SupplierProductCode = supplierProductCode;
	}
	public String getMinPackageQty() {
		return MinPackageQty;
	}
	public void setMinPackageQty(String minPackageQty) {
		MinPackageQty = minPackageQty;
	}
	public String getMinOrderQty() {
		return MinOrderQty;
	}
	public void setMinOrderQty(String minOrderQty) {
		MinOrderQty = minOrderQty;
	}
	public String getCommodity() {
		return Commodity;
	}
	public void setCommodity(String commodity) {
		Commodity = commodity;
	}
	public String getReferenceNumber1() {
		return ReferenceNumber1;
	}
	public void setReferenceNumber1(String referenceNumber1) {
		ReferenceNumber1 = referenceNumber1;
	}
	public String getReferenceNumber2() {
		return ReferenceNumber2;
	}
	public void setReferenceNumber2(String referenceNumber2) {
		ReferenceNumber2 = referenceNumber2;
	}
	public String getShippingMarks() {
		return ShippingMarks;
	}
	public void setShippingMarks(String shippingMarks) {
		ShippingMarks = shippingMarks;
	}
	public String getDescriptionOfGoods() {
		return DescriptionOfGoods;
	}
	public void setDescriptionOfGoods(String descriptionOfGoods) {
		DescriptionOfGoods = descriptionOfGoods;
	}
	public String getPackagingInstruction() {
		return PackagingInstruction;
	}
	public void setPackagingInstruction(String packagingInstruction) {
		PackagingInstruction = packagingInstruction;
	}
	
	public String getCountryCodeOfOrigin() {
		return CountryCodeOfOrigin;
	}
	public void setCountryCodeOfOrigin(String countryCodeOfOrigin) {
		CountryCodeOfOrigin = countryCodeOfOrigin;
	}

	public String getProductRemark() {
		return ProductRemark;
	}

	public void setProductRemark(String productRemark) {
		ProductRemark = productRemark;
	}

	public String getPackageUOM() {
		return PackageUOM;
	}

	public void setPackageUOM(String packageUOM) {
		PackageUOM = packageUOM;
	}

	public String getColourCode() {
		return ColourCode;
	}

	public void setColourCode(String colourCode) {
		ColourCode = colourCode;
	}

	public String getSeasonCode() {
		return SeasonCode;
	}

	public void setSeasonCode(String seasonCode) {
		SeasonCode = seasonCode;
	}

	public String getStyleNo() {
		return StyleNo;
	}

	public void setStyleNo(String styleNo) {
		StyleNo = styleNo;
	}

	public String getSize() {
		return Size;
	}

	public void setSize(String size) {
		Size = size;
	}

	public String getGrossWeight() {
		return GrossWeight;
	}

	public void setGrossWeight(String grossWeight) {
		GrossWeight = grossWeight;
	}

	public String getVolume() {
		return Volume;
	}

	public void setVolume(String volume) {
		Volume = volume;
	}

	public String getScheduleLineNo() {
		return ScheduleLineNo;
	}

	public void setScheduleLineNo(String scheduleLineNo) {
		ScheduleLineNo = scheduleLineNo;
	}

	public String getInboundDelivery() {
		return InboundDelivery;
	}

	public void setInboundDelivery(String inboundDelivery) {
		InboundDelivery = inboundDelivery;
	}

	public String getPOItemReference() {
		return POItemReference;
	}

	public void setPOItemReference(String pOItemReference) {
		POItemReference = pOItemReference;
	}

	public String getShipmentNo() {
		return ShipmentNo;
	}

	public void setShipmentNo(String shipmentNo) {
		ShipmentNo = shipmentNo;
	}

	public String getPlant() {
		return Plant;
	}

	public void setPlant(String plant) {
		Plant = plant;
	}

	public String getStorageLocation() {
		return StorageLocation;
	}

	public void setStorageLocation(String storageLocation) {
		StorageLocation = storageLocation;
	}

	public String getMatGrpDe() {
		return MatGrpDe;
	}

	public void setMatGrpDe(String matGrpDe) {
		MatGrpDe = matGrpDe;
	}

	public String getMaterialType() {
		return MaterialType;
	}

	public void setMaterialType(String materialType) {
		MaterialType = materialType;
	}

	public String getSKU() {
		return SKU;
	}

	public void setSKU(String sKU) {
		SKU = sKU;
	}

	public String getGridValue() {
		return GridValue;
	}

	public void setGridValue(String gridValue) {
		GridValue = gridValue;
	}

	public String getStockCategory() {
		return StockCategory;
	}

	public void setStockCategory(String stockCategory) {
		StockCategory = stockCategory;
	}

	public String getHeaderText() {
		return HeaderText;
	}

	public void setHeaderText(String headerText) {
		HeaderText = headerText;
	}

	public String getLength() {
		return Length;
	}

	public void setLength(String length) {
		Length = length;
	}

	public String getWidth() {
		return Width;
	}

	public void setWidth(String width) {
		Width = width;
	}

	public String getHeight() {
		return Height;
	}

	public void setHeight(String height) {
		Height = height;
	}

	public String getNetWeight() {
		return NetWeight;
	}

	public void setNetWeight(String netWeight) {
		NetWeight = netWeight;
	}

	public String getFactoryName() {
		return FactoryName;
	}

	public void setFactoryName(String factoryName) {
		FactoryName = factoryName;
	}
	
}
