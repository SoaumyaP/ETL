package com.csfe.etl.business.entity.master;

public class Organizations {
	private long Id;
	private String RowVersion;
	private String CreatedBy;
	private String CreatedDate;
	private String UpdatedBy;
	private String UpdatedDate;
	private String Code;
	private String Name;
	private String ContactEmail;
	private String ContactName;
	private String ContactNumber;
	private String Address;
	private String EdisonInstanceId;
	private String EdisonCompanyCodeId;
	private String LocationId;
	private String OrganizationType;
	private String ParentId;
	private String Status;

	public Organizations() {
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

	public String getCode() {
		return Code;
	}

	public void setCode(String code) {
		Code = code;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getContactEmail() {
		return ContactEmail;
	}

	public void setContactEmail(String contactEmail) {
		ContactEmail = contactEmail;
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

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getEdisonInstanceId() {
		return EdisonInstanceId;
	}

	public void setEdisonInstanceId(String edisonInstanceId) {
		EdisonInstanceId = edisonInstanceId;
	}

	public String getEdisonCompanyCodeId() {
		return EdisonCompanyCodeId;
	}

	public void setEdisonCompanyCodeId(String edisonCompanyCodeId) {
		EdisonCompanyCodeId = edisonCompanyCodeId;
	}

	public String getLocationId() {
		return LocationId;
	}

	public void setLocationId(String locationId) {
		LocationId = locationId;
	}

	public String getOrganizationType() {
		return OrganizationType;
	}

	public void setOrganizationType(String organizationType) {
		OrganizationType = organizationType;
	}

	public String getParentId() {
		return ParentId;
	}

	public void setParentId(String parentId) {
		ParentId = parentId;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public String toString() {
		return "Organizations [Id=" + Id + ", RowVersion=" + RowVersion + ", CreatedBy=" + CreatedBy + ", CreatedDate="
				+ CreatedDate + ", UpdatedBy=" + UpdatedBy + ", UpdatedDate=" + UpdatedDate + ", Code=" + Code
				+ ", Name=" + Name + ", ContactEmail=" + ContactEmail + ", ContactName=" + ContactName
				+ ", ContactNumber=" + ContactNumber + ", Address=" + Address + ", EdisonInstanceId=" + EdisonInstanceId
				+ ", EdisonCompanyCodeId=" + EdisonCompanyCodeId + ", LocationId=" + LocationId + ", OrganizationType="
				+ OrganizationType + ", ParentId=" + ParentId + ", Status=" + Status + "]";
	}
}
