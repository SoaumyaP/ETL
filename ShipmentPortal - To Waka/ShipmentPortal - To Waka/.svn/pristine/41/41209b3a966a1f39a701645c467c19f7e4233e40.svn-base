package com.csfe.edison.business.service;

import java.util.List;

import com.csfe.common.CSFEException;
import com.csfe.edison.business.entity.EdisonLog;
import com.csfe.etl.business.entity.Activities;
import com.csfe.etl.business.entity.BillOfLadingConsignments;
import com.csfe.etl.business.entity.BillOfLadingContacts;
import com.csfe.etl.business.entity.BillOfLadingItineraries;
import com.csfe.etl.business.entity.BillOfLadingShipmentLoads;
import com.csfe.etl.business.entity.BillOfLadings;
import com.csfe.etl.business.entity.CargoDetails;
import com.csfe.etl.business.entity.ConsignmentItineraries;
import com.csfe.etl.business.entity.Consignments;
import com.csfe.etl.business.entity.Consolidations;
import com.csfe.etl.business.entity.ContainerItineraries;
import com.csfe.etl.business.entity.Containers;
import com.csfe.etl.business.entity.Itineraries;
import com.csfe.etl.business.entity.MasterBillContacts;
import com.csfe.etl.business.entity.MasterBillItineraries;
import com.csfe.etl.business.entity.MasterBills;
import com.csfe.etl.business.entity.ShipmentBillOfLadings;
import com.csfe.etl.business.entity.ShipmentContacts;
import com.csfe.etl.business.entity.ShipmentLoadDetails;
import com.csfe.etl.business.entity.ShipmentLoads;
import com.csfe.etl.business.entity.Shipments;
import com.csfe.etl.business.entity.Cruise.CruiseOrderContacts;
import com.csfe.etl.business.entity.Cruise.CruiseOrderWarehouse;
import com.csfe.etl.business.entity.Cruise.CruiseOrders;
import com.csfe.etl.business.entity.POFulfillment.POFulfillment;
import com.csfe.etl.business.entity.master.Organizations;
import com.csfe.etl.business.entity.po.PurchaseOrders;
import com.csfe.etl.business.entity.po.Contacts;

public interface EdisonService {
	public abstract String getDbName();
	public abstract List<EdisonLog> findPendEdisonID(String sysname) throws CSFEException;
	public abstract List<EdisonLog> findDeleteEdisonID(String sysname) throws CSFEException;
	public abstract List<EdisonLog> processEdisonLog(List sqllist, String sysname, String creator, String companyCode, String poCompanyCode) throws CSFEException;
	public abstract EdisonLog findEdisonIDBySONO(String db, String sysname, String sono, String soseq, String type) throws CSFEException;
	
	public abstract void saveEdisonLog(EdisonLog list, boolean isUpdate) throws CSFEException;	
	public abstract void saveEdisonLogList(List<EdisonLog> list) throws CSFEException;	
	
	public abstract List<Organizations> findOrganizations(String db, String sysname, List<String> list);
	public abstract List<Shipments> findShipments(String db, String sysname, List<String> list, String poCompanyCode);
	public abstract List<BillOfLadings> findBillOfLadings(String db, String sysname, String companyCode, List<String> list);
	public abstract List<MasterBills> findMasterBills(String db, String sysname, String companyCode, List<String> list);
	public abstract List<Containers> findContainers(String db, String sysname, List<String> list);
	public abstract List<Consolidations> findConsolidations(String db, String sysname, List<String> list);
	public abstract List<Consignments> findConsignments(String db, String sysname, String companyCode, List<String> list);
	public abstract List<CargoDetails> findCargoDetails(String db, String sysname, List<String> list);
	public abstract List<ShipmentLoads> findShipmentLoads(String db, String sysname, List<String> list);
	public abstract List<ShipmentLoadDetails> findShipmentLoadDetails(String db, String sysname, List<String> list);
	public abstract List<BillOfLadingContacts> findBillOfLadingContacts(String db, String sysname, String companyCode, List<String> list);
	public abstract List<MasterBillContacts> findMasterBillContacts(String db, String sysname, List<String> list);
	public abstract List<ShipmentContacts> findShipmentContacts(String db, String sysname, String companyCode, List<String> list);
	public abstract List<Itineraries> findItineraries(String db, String sysname, List<String> list);
	
	public abstract List<BillOfLadingConsignments> findBillOfLadingConsignments(String db, String sysname, String companyCode, String sysdate);
	public abstract List<BillOfLadingItineraries> findBillOfLadingItineraries(String db, String sysname, String sysdate);
	public abstract List<ShipmentBillOfLadings> findShipmentBillOfLadings(String db, String sysname, String sysdate);
	public abstract List<BillOfLadingShipmentLoads> findBillOfLadingShipmentLoads(String db, String sysname, String sysdate);
	public abstract List<ConsignmentItineraries> findConsignmentItineraries(String db, String sysname, String companyCode, String sysdate);
	public abstract List<ContainerItineraries> findContainerItineraries(String db, String sysname, String sysdate);
	public abstract List<MasterBillItineraries> findMasterBillItineraries(String db, String sysname, String sysdate);
	public abstract List<Activities> findActivities(String db, String sysname, List<String> list);
	public abstract List<PurchaseOrders> findPurchaseOrders(String db, String sysname, List<String> list);
	public abstract List<Contacts> findContacts(String db, String sysname, List<String> list);
	public abstract List<POFulfillment> findPOFulfillment(String db, String sysname, String sono, String seq);
	
	public abstract List<CruiseOrders> findCruiseOrders(String db, String sysname, List<String> list);
	public abstract List<CruiseOrderContacts> findCruiseOrderContacts(String db, String sysname, List<String> list);
	public abstract List<CruiseOrderWarehouse> findCruiseOrderWarehouse(String db, String sysname, List<String> list);
	
	public abstract List<BillOfLadingConsignments> findDeleteBillOfLadingConsignments(String db, String sysname, String companyCode, String sysdate);
	public abstract List<BillOfLadingItineraries> findDeleteBillOfLadingItineraries(String db, String sysname, String sysdate);
	public abstract List<ShipmentBillOfLadings> findDeleteShipmentBillOfLadings(String db, String sysname, String sysdate);
	public abstract List<BillOfLadingShipmentLoads> findDeleteBillOfLadingShipmentLoads(String db, String sysname, String sysdate);
	public abstract List<ConsignmentItineraries> findDeleteConsignmentItineraries(String db, String sysname, String companyCode, String sysdate);
	public abstract List<ContainerItineraries> findDeleteContainerItineraries(String db, String sysname, String sysdate);
	public abstract List<MasterBillItineraries> findDeleteMasterBillItineraries(String db, String sysname, String sysdate);
}
