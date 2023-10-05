package com.csfe.etl.business.entity.Cruise;

public class CruiseOrderContacts {
	private long Id;
	private long OrganizationId;
	private String OrganizationRole;
	private String CompanyName;
	private String Address;
	private String ContactName;
	private String ContactNumber;
	private String ContactEmail;
	private String CreatedDate;
	private String CreatedBy;
	private String UpdatedDate;
	private String UpdatedBy;
	
	public CruiseOrderContacts() {
		super();
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public long getOrganizationId() {
		return OrganizationId;
	}

	public void setOrganizationId(long organizationId) {
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

	@Override
	public String toString() {
		return "CruiseOrderContacts [Id=" + Id + ", OrganizationId=" + OrganizationId + ", OrganizationRole="
				+ OrganizationRole + ", CompanyName=" + CompanyName + ", Address=" + Address + ", ContactName="
				+ ContactName + ", ContactNumber=" + ContactNumber + ", ContactEmail=" + ContactEmail + ", CreatedDate="
				+ CreatedDate + ", CreatedBy=" + CreatedBy + ", UpdatedDate=" + UpdatedDate + ", UpdatedBy=" + UpdatedBy
				+ "]";
	}
}
