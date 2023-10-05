package com.csfe.etl.business.entity.Cruise;

import java.util.LinkedHashSet;
import java.util.Set;

import com.csfe.etl.business.entity.Cruise.Contacts;
import com.csfe.etl.business.entity.Cruise.Items;

public class CruiseOrders {
	
	private long id;
	private String POId;
	private String PONumber;
	private String POStatus;
	private String POType;
	private String PODate;
	private String POSubject;
	private String POCause;
	private String POPriority;
	private String Ship;
	private String Requestor;
	private String RequestDate;
	private String RequestApprovedDate;
	private String RequestPriority;
	private String RequestType;
	private String RequestType2;
	private String RequestType3;
	private String WithWO;
	private String EstimatedDeliveryDate;
	private String Maker;
	private String MaintenanceObject;
	private String Invoiced;
	private String FirstReceivingPoint;
	private String Department;
	private String DeliveryMeans;
	private String Delivered;
	private String CreationUser;
	private String CertificateNumber;
	private String CertificateId;
	private String BudgetYear;
	private String BudgetPeriod;
	private String BudgetId;
	private String BudgetAccount;
	private String Approver;
	private String ApprovedDate;
	private String ApprovalStatus;
	private String ActualShipDate;
	private String ActualDeliveryDate;
	private String CreatedDate;
	private String CreatedBy;
	private String UpdatedDate;
	private String UpdatedBy;
	
	private String POLine;
	private String ItemId;
	private String ItemName;
	private String NetUSUnitPrice;
	private String NetUnitPrice;
	private String TotalOrderPrice;
	private String OrderQuantity;
	private String Currency;
	private String UOM;
	private String RequestQuantity;
	private String RequestNumber;
	private String RequestLine;
	private String QuantityDelivered;
	private String ShipRequestLineNotes;
	private String RequestLineShoreNotes;
	private String MakerReferenceOfItemName2;
	private String FirstReceivedDate;
	private String LineEstimatedDeliveryDate;
	
	private Set<Items> Items;
	private Set<Contacts> Contacts;
	
	public CruiseOrders() {
		super();
		this.resetItems();
		this.resetContacts();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPOId() {
		return POId;
	}

	public void setPOId(String pOId) {
		POId = pOId;
	}

	public String getPONumber() {
		return PONumber;
	}

	public void setPONumber(String pONumber) {
		PONumber = pONumber;
	}

	public String getPOStatus() {
		return POStatus;
	}

	public void setPOStatus(String pOStatus) {
		POStatus = pOStatus;
	}

	public String getPOType() {
		return POType;
	}

	public void setPOType(String pOType) {
		POType = pOType;
	}

	public String getPODate() {
		return PODate;
	}

	public void setPODate(String pODate) {
		PODate = pODate;
	}

	public String getPOSubject() {
		return POSubject;
	}

	public void setPOSubject(String pOSubject) {
		POSubject = pOSubject;
	}

	public String getPOCause() {
		return POCause;
	}

	public void setPOCause(String pOCause) {
		POCause = pOCause;
	}

	public String getPOPriority() {
		return POPriority;
	}

	public void setPOPriority(String pOPriority) {
		POPriority = pOPriority;
	}

	public String getShip() {
		return Ship;
	}

	public void setShip(String ship) {
		Ship = ship;
	}

	public String getRequestor() {
		return Requestor;
	}

	public void setRequestor(String requestor) {
		Requestor = requestor;
	}

	public String getRequestDate() {
		return RequestDate;
	}

	public void setRequestDate(String requestDate) {
		RequestDate = requestDate;
	}

	public String getRequestApprovedDate() {
		return RequestApprovedDate;
	}

	public void setRequestApprovedDate(String requestApprovedDate) {
		RequestApprovedDate = requestApprovedDate;
	}

	public String getRequestPriority() {
		return RequestPriority;
	}

	public void setRequestPriority(String requestPriority) {
		RequestPriority = requestPriority;
	}

	public String getRequestType() {
		return RequestType;
	}

	public void setRequestType(String requestType) {
		RequestType = requestType;
	}

	public String getRequestType2() {
		return RequestType2;
	}

	public void setRequestType2(String requestType2) {
		RequestType2 = requestType2;
	}

	public String getRequestType3() {
		return RequestType3;
	}

	public void setRequestType3(String requestType3) {
		RequestType3 = requestType3;
	}

	public String getWithWO() {
		return WithWO;
	}

	public void setWithWO(String withWO) {
		WithWO = withWO;
	}

	public String getEstimatedDeliveryDate() {
		return EstimatedDeliveryDate;
	}

	public void setEstimatedDeliveryDate(String estimatedDeliveryDate) {
		EstimatedDeliveryDate = estimatedDeliveryDate;
	}

	public String getMaker() {
		return Maker;
	}

	public void setMaker(String maker) {
		Maker = maker;
	}

	public String getMaintenanceObject() {
		return MaintenanceObject;
	}

	public void setMaintenanceObject(String maintenanceObject) {
		MaintenanceObject = maintenanceObject;
	}

	public String getInvoiced() {
		return Invoiced;
	}

	public void setInvoiced(String invoiced) {
		Invoiced = invoiced;
	}

	public String getFirstReceivingPoint() {
		return FirstReceivingPoint;
	}

	public void setFirstReceivingPoint(String firstReceivingPoint) {
		FirstReceivingPoint = firstReceivingPoint;
	}

	public String getDepartment() {
		return Department;
	}

	public void setDepartment(String department) {
		Department = department;
	}

	public String getDeliveryMeans() {
		return DeliveryMeans;
	}

	public void setDeliveryMeans(String deliveryMeans) {
		DeliveryMeans = deliveryMeans;
	}

	public String getDelivered() {
		return Delivered;
	}

	public void setDelivered(String delivered) {
		Delivered = delivered;
	}

	public String getCreationUser() {
		return CreationUser;
	}

	public void setCreationUser(String creationUser) {
		CreationUser = creationUser;
	}

	public String getCertificateNumber() {
		return CertificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		CertificateNumber = certificateNumber;
	}

	public String getCertificateId() {
		return CertificateId;
	}

	public void setCertificateId(String certificateId) {
		CertificateId = certificateId;
	}

	public String getBudgetYear() {
		return BudgetYear;
	}

	public void setBudgetYear(String budgetYear) {
		BudgetYear = budgetYear;
	}

	public String getBudgetPeriod() {
		return BudgetPeriod;
	}

	public void setBudgetPeriod(String budgetPeriod) {
		BudgetPeriod = budgetPeriod;
	}

	public String getBudgetId() {
		return BudgetId;
	}

	public void setBudgetId(String budgetId) {
		BudgetId = budgetId;
	}

	public String getBudgetAccount() {
		return BudgetAccount;
	}

	public void setBudgetAccount(String budgetAccount) {
		BudgetAccount = budgetAccount;
	}

	public String getApprover() {
		return Approver;
	}

	public void setApprover(String approver) {
		Approver = approver;
	}

	public String getApprovedDate() {
		return ApprovedDate;
	}

	public void setApprovedDate(String approvedDate) {
		ApprovedDate = approvedDate;
	}

	public String getApprovalStatus() {
		return ApprovalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		ApprovalStatus = approvalStatus;
	}

	public String getActualShipDate() {
		return ActualShipDate;
	}

	public void setActualShipDate(String actualShipDate) {
		ActualShipDate = actualShipDate;
	}

	public String getActualDeliveryDate() {
		return ActualDeliveryDate;
	}

	public void setActualDeliveryDate(String actualDeliveryDate) {
		ActualDeliveryDate = actualDeliveryDate;
	}

	public String getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(String createdDate) {
		CreatedDate = createdDate;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public String getUpdatedDate() {
		return UpdatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		UpdatedDate = updatedDate;
	}

	public String getUpdatedBy() {
		return UpdatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		UpdatedBy = updatedBy;
	}

	public Set<Items> getItems() {
		return Items;
	}

	public void setItems(Set<Items> items) {
		Items = items;
	}

	public Set<Contacts> getContacts() {
		return Contacts;
	}

	public void setContacts(Set<Contacts> contacts) {
		Contacts = contacts;
	}
	
	public void resetContacts() {
		this.setContacts(new LinkedHashSet<Contacts>());
	}
	
	public void addCruiseOrderContacts(CruiseOrderContacts contacts) {
		if(this.getContacts() == null)	this.resetContacts();
		this.getContacts().add(new Contacts(contacts));
	}
	
	public void resetItems() {
		this.setItems(new LinkedHashSet<Items>());
	}
	
	public void addItems(Items item) {
		if(this.getItems() == null)	this.resetItems();
		this.getItems().add(item);
	}

	public String getPOLine() {
		return POLine;
	}

	public void setPOLine(String pOLine) {
		POLine = pOLine;
	}

	public String getItemId() {
		return ItemId;
	}

	public void setItemId(String itemId) {
		ItemId = itemId;
	}

	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
	}

	public String getNetUSUnitPrice() {
		return NetUSUnitPrice;
	}

	public void setNetUSUnitPrice(String netUSUnitPrice) {
		NetUSUnitPrice = netUSUnitPrice;
	}

	public String getNetUnitPrice() {
		return NetUnitPrice;
	}

	public void setNetUnitPrice(String netUnitPrice) {
		NetUnitPrice = netUnitPrice;
	}

	public String getTotalOrderPrice() {
		return TotalOrderPrice;
	}

	public void setTotalOrderPrice(String totalOrderPrice) {
		TotalOrderPrice = totalOrderPrice;
	}

	public String getOrderQuantity() {
		return OrderQuantity;
	}

	public void setOrderQuantity(String orderQuantity) {
		OrderQuantity = orderQuantity;
	}

	public String getCurrency() {
		return Currency;
	}

	public void setCurrency(String currency) {
		Currency = currency;
	}

	public String getUOM() {
		return UOM;
	}

	public void setUOM(String uOM) {
		UOM = uOM;
	}

	public String getRequestQuantity() {
		return RequestQuantity;
	}

	public void setRequestQuantity(String requestQuantity) {
		RequestQuantity = requestQuantity;
	}

	public String getRequestNumber() {
		return RequestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		RequestNumber = requestNumber;
	}

	public String getRequestLine() {
		return RequestLine;
	}

	public void setRequestLine(String requestLine) {
		RequestLine = requestLine;
	}

	public String getQuantityDelivered() {
		return QuantityDelivered;
	}

	public void setQuantityDelivered(String quantityDelivered) {
		QuantityDelivered = quantityDelivered;
	}

	public String getShipRequestLineNotes() {
		return ShipRequestLineNotes;
	}

	public void setShipRequestLineNotes(String shipRequestLineNotes) {
		ShipRequestLineNotes = shipRequestLineNotes;
	}

	public String getRequestLineShoreNotes() {
		return RequestLineShoreNotes;
	}

	public void setRequestLineShoreNotes(String requestLineShoreNotes) {
		RequestLineShoreNotes = requestLineShoreNotes;
	}

	public String getMakerReferenceOfItemName2() {
		return MakerReferenceOfItemName2;
	}

	public void setMakerReferenceOfItemName2(String makerReferenceOfItemName2) {
		MakerReferenceOfItemName2 = makerReferenceOfItemName2;
	}

	public String getFirstReceivedDate() {
		return FirstReceivedDate;
	}

	public void setFirstReceivedDate(String firstReceivedDate) {
		FirstReceivedDate = firstReceivedDate;
	}

	public String getLineEstimatedDeliveryDate() {
		return LineEstimatedDeliveryDate;
	}

	public void setLineEstimatedDeliveryDate(String lineEstimatedDeliveryDate) {
		LineEstimatedDeliveryDate = lineEstimatedDeliveryDate;
	}

	public void packupItems() {
		this.addItems(new Items(this));
		
		this.setCurrency(null);
		this.setFirstReceivedDate(null);
		this.setItemId(null);
		this.setItemName(null);
		this.setLineEstimatedDeliveryDate(null);
		this.setMakerReferenceOfItemName2(null);
		this.setNetUnitPrice(null);
		this.setNetUSUnitPrice(null);
		this.setOrderQuantity(null);
		this.setPOLine(null);
		this.setQuantityDelivered(null);
		this.setRequestLine(null);
		this.setRequestLineShoreNotes(null);
		this.setRequestNumber(null);
		this.setRequestQuantity(null);
		this.setShipRequestLineNotes(null);
		this.setTotalOrderPrice(null);
		this.setUOM(null);
	}
	@Override
	public String toString() {
		return "CruiseOrders [POId=" + POId + ", PONumber=" + PONumber + ", POStatus=" + POStatus + ", POType=" + POType
				+ ", PODate=" + PODate + ", POSubject=" + POSubject + ", POCause=" + POCause + ", POPriority=" + POPriority
				+ ", Ship=" + Ship + ", Requestor=" + Requestor + ", RequestDate=" + RequestDate
				+ ", RequestApprovedDate=" + RequestApprovedDate + ", RequestPriority=" + RequestPriority
				+ ", RequestType=" + RequestType + ", RequestType2=" + RequestType2 + ", RequestType3=" + RequestType3
				+ ", WithWO=" + WithWO + ", EstimatedDeliveryDate=" + EstimatedDeliveryDate + ", Maker=" + Maker
				+ ", MaintenanceObject=" + MaintenanceObject + ", Invoiced=" + Invoiced + ", FirstReceivingPoint="
				+ FirstReceivingPoint + ", Department=" + Department + ", DeliveryMeans=" + DeliveryMeans
				+ ", Delivered=" + Delivered + ", CreationUser=" + CreationUser + ", CertificateNumber="
				+ CertificateNumber + ", CertificateId=" + CertificateId + ", BudgetYear=" + BudgetYear
				+ ", BudgetPeriod=" + BudgetPeriod + ", BudgetId=" + BudgetId + ", BudgetAccount=" + BudgetAccount
				+ ", Approver=" + Approver + ", ApprovedDate=" + ApprovedDate + ", ApprovalStatus=" + ApprovalStatus
				+ ", ActualShipDate=" + ActualShipDate + ", ActualDeliveryDate=" + ActualDeliveryDate + ", CreatedDate="
				+ CreatedDate + ", CreatedBy=" + CreatedBy + ", UpdatedDate=" + UpdatedDate + ", UpdatedBy=" + UpdatedBy
				+ ", Items=" + Items + ", Contacts=" + Contacts + "]";
	}
}
