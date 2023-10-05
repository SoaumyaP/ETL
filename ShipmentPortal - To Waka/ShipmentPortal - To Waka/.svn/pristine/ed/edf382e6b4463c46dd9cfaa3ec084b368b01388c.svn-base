package com.csfe.trs.business.entity;

import java.util.ArrayList;
import java.util.List;

public class POFulfillment {
	
	private String BookingReferenceNo;
	private String sysid;
	private String SONumber;
	private String BillOfLadingHeader;
	private String Warehouse;
	private String CargoClosingDate;
	
	private List<Leg> Legs;

	private String ModeOfTransport;
	private String CarrierCode;
	private String VesselFlight;
	private String LoadingPortCode;
	private String ETD;
	private String DischargePortCode;
	private String ETA;
	
	public POFulfillment() {
		super();
	}
	
	public void packupLeg() {
		Leg legs = new Leg();
		legs.setCarrierCode(this.getCarrierCode());
		legs.setLoadingPortCode(this.UNCODE(this.getLoadingPortCode()));
		legs.setDischargePortCode(this.UNCODE(this.getDischargePortCode()));
		legs.setETA(this.getETA());
		legs.setETD(this.getETD());
		legs.setModeOfTransport(this.getModeOfTransport());
		legs.setVesselFlight(this.getVesselFlight());
		
		this.setLegs(new ArrayList<Leg>());
		this.getLegs().add(legs);
		
		this.setCarrierCode(null);
		this.setDischargePortCode(null);
		this.setETA(null);
		this.setETD(null);
		this.setLoadingPortCode(null);
		this.setModeOfTransport(null);
		this.setVesselFlight(null);
		
		this.setSysid(null);
	}

	public String getBookingReferenceNo() {
		return BookingReferenceNo;
	}

	public void setBookingReferenceNo(String bookingReferenceNo) {
		BookingReferenceNo = bookingReferenceNo;
	}

	public String getSysid() {
		return sysid;
	}

	public void setSysid(String sysid) {
		this.sysid = sysid;
	}

	public String getSONumber() {
		return SONumber;
	}

	public void setSONumber(String sONumber) {
		SONumber = sONumber;
	}

	public String getBillOfLadingHeader() {
		return BillOfLadingHeader;
	}

	public void setBillOfLadingHeader(String billOfLadingHeader) {
		BillOfLadingHeader = billOfLadingHeader;
	}

	public String getWarehouse() {
		return Warehouse;
	}

	public void setWarehouse(String warehouse) {
		Warehouse = warehouse;
	}

	public String getCargoClosingDate() {
		return CargoClosingDate;
	}

	public void setCargoClosingDate(String cargoClosingDate) {
		CargoClosingDate = cargoClosingDate;
	}

	public List<Leg> getLegs() {
		return Legs;
	}

	public void setLegs(List<Leg> legs) {
		Legs = legs;
	}

	public String getModeOfTransport() {
		return ModeOfTransport;
	}

	public void setModeOfTransport(String modeOfTransport) {
		ModeOfTransport = modeOfTransport;
	}

	public String getCarrierCode() {
		return CarrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		CarrierCode = carrierCode;
	}

	public String getVesselFlight() {
		return VesselFlight;
	}

	public void setVesselFlight(String vesselFlight) {
		VesselFlight = vesselFlight;
	}

	public String getLoadingPortCode() {
		return LoadingPortCode;
	}

	public void setLoadingPortCode(String loadingPortCode) {
		LoadingPortCode = loadingPortCode;
	}

	public String getETD() {
		return ETD;
	}

	public void setETD(String eTD) {
		ETD = eTD;
	}

	public String getDischargePortCode() {
		return DischargePortCode;
	}

	public void setDischargePortCode(String dischargePortCode) {
		DischargePortCode = dischargePortCode;
	}

	public String getETA() {
		return ETA;
	}

	public void setETA(String eTA) {
		ETA = eTA;
	}
	
	private String UNCODE(String value) {
		return value.substring(3,5)+value.substring(0,3);
	}

	@Override
	public String toString() {
		return "POFulfillment [BookingReferenceNo=" + BookingReferenceNo + ", SONumber=" + SONumber
				+ ", BillOfLadingHeader=" + BillOfLadingHeader + ", Warehouse=" + Warehouse + ", CargoClosingDate="
				+ CargoClosingDate + ", Legs=" + Legs + "]";
	}
}
