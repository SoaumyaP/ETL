package com.csfe.etl.business.entity;

import java.util.Date;

public class BillOfLadingContacts {
	private long Id;
	private String RowVersion;
	private String CreatedBy;
	private String CreatedDate;
	private String UpdatedBy;
	private String UpdatedDate;
	private String BillOfLadingId;
	private String OrganizationId;
	private String OrganizationRole;
	private String CompanyName;
	private String Address;
	private String ContactName;
	private String ContactNumber;
	private String ContactEmail;
	
	public BillOfLadingContacts() {
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

	public String getBillOfLadingId() {
		return BillOfLadingId;
	}

	public void setBillOfLadingId(String billOfLadingId) {
		BillOfLadingId = billOfLadingId;
	}

	public String getOrganizationId() {
		return OrganizationId;
	}

	public void setOrganizationId(String organizationId) {
		OrganizationId = organizationId;
	}

	public String getOrganizationRole() {
		return OrganizationRole;
	}

	public void setOrganizationRole(String organizationRole) {
		OrganizationRole = organizationRole;
	}

	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getContactName() {
		return ContactName;
	}

	public void setContactName(String contactName) {
		ContactName = contactName;
	}

	public String getContactNumber() {
		return ContactNumber;
	}

	public void setContactNumber(String contactNumber) {
		ContactNumber = contactNumber;
	}

	public String getContactEmail() {
		return ContactEmail;
	}

	public void setContactEmail(String contactEmail) {
		ContactEmail = contactEmail;
	}

	@Override
	public String toString() {
		return "BillOfLadingContacts [Id=" + Id + ", RowVersion=" + RowVersion + ", CreatedBy=" + CreatedBy
				+ ", CreatedDate=" + CreatedDate + ", UpdatedBy=" + UpdatedBy + ", UpdatedDate=" + UpdatedDate
				+ ", BillOfLadingId=" + BillOfLadingId + ", OrganizationId=" + OrganizationId + ", OrganizationRole="
				+ OrganizationRole + ", CompanyName=" + CompanyName + ", Address=" + Address + ", ContactName="
				+ ContactName + ", ContactNumber=" + ContactNumber + ", ContactEmail=" + ContactEmail + "]";
	}
}
