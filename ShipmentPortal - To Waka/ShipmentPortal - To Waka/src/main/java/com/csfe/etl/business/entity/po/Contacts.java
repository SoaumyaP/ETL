package com.csfe.etl.business.entity.po;

public class Contacts {
	private long id;
	private String OrganizationRole;
	private String OrganizationCode;
	private String CompanyName;
	private String AddressLine1;
	private String AddressLine2;
	private String AddressLine3;
	private String AddressLine4;
	private String ContactNumber;
	private String ContactName;
	private String ContactEmail;
	
	public Contacts() {
		super();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOrganizationRole() {
		return OrganizationRole;
	}
	public void setOrganizationRole(String organizationRole) {
		OrganizationRole = organizationRole;
	}
	public String getOrganizationCode() {
		return OrganizationCode;
	}
	public void setOrganizationCode(String organizationCode) {
		OrganizationCode = organizationCode;
	}
	
	public String getCompanyName() {
		return CompanyName;
	}

	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}

	public String getAddressLine1() {
		return AddressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		AddressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return AddressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		AddressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return AddressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		AddressLine3 = addressLine3;
	}

	public String getAddressLine4() {
		return AddressLine4;
	}

	public void setAddressLine4(String addressLine4) {
		AddressLine4 = addressLine4;
	}

	public String getContactNumber() {
		return ContactNumber;
	}

	public void setContactNumber(String contactNumber) {
		ContactNumber = contactNumber;
	}

	public String getContactName() {
		return ContactName;
	}

	public void setContactName(String contactName) {
		ContactName = contactName;
	}

	public String getContactEmail() {
		return ContactEmail;
	}

	public void setContactEmail(String contactEmail) {
		ContactEmail = contactEmail;
	}

	@Override
	public String toString() {
		return "Contacts [OrganizationRole=" + OrganizationRole + ", OrganizationCode="
				+ OrganizationCode + ", CompanyName=" + CompanyName + ", AddressLine1=" + AddressLine1
				+ ", AddressLine2=" + AddressLine2 + ", AddressLine3=" + AddressLine3 + ", AddressLine4=" + AddressLine4
				+ ", ContactNumber=" + ContactNumber + ", ContactName=" + ContactName + ", ContactEmail=" + ContactEmail
				+ "]";
	}

}
