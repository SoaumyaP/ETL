package com.csfe.etl.business.entity.POFulfillment;

public class Leg {
	private String ModeOfTransport;
	private String CarrierCode;
	private String VesselFlight;
	private String LoadingPortCode;
	private String ETD;
	private String DischargePortCode;
	private String ETA;
	 
	public Leg() {
		super();
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

	@Override
	public String toString() {
		return "Leg [ModeOfTransport=" + ModeOfTransport + ", CarrierCode=" + CarrierCode + ", VesselFlight="
				+ VesselFlight + ", LoadingPortCode=" + LoadingPortCode + ", ETD=" + ETD + ", DischargePortCode="
				+ DischargePortCode + ", ETA=" + ETA + "]";
	}
}
