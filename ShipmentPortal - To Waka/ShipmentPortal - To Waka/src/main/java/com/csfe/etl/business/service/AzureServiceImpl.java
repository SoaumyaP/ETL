package com.csfe.etl.business.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.csfe.common.CSFEException;
import com.csfe.common.ConstantsUtil;
import com.csfe.common.EmailNotification;
import com.csfe.common.WarningNotification;
import com.csfe.common.util.StringUtil;
import com.csfe.edison.business.entity.EdisonLog;
import com.csfe.edison.business.service.EdisonService;
import com.csfe.etl.business.dao.AzureDAO;
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
import com.csfe.etl.business.entity.Cruise.Items;
import com.csfe.etl.business.entity.POFulfillment.POFulfillment;
import com.csfe.etl.business.entity.master.Organizations;
import com.csfe.etl.business.entity.po.PurchaseOrders;
import com.csfe.etl.business.entity.po.Contacts;
import com.csfe.etl.business.entity.po.LineItems;
import com.google.gson.Gson;


import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;


import javax.net.ssl.HostnameVerifier;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.ssl.SSLContextBuilder;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = "transactionManager")
public class AzureServiceImpl implements AzureService{
	
	private Logger logger = Logger.getLogger(this.getClass());

	@Autowired(required = false)
	private ApplicationContext appContext;
	
	@Autowired
	@Resource(name = "DirectDBList")
	private ArrayList<String> directList;
	
	@Autowired
	@Qualifier("APIMap")
	private LinkedHashMap<String, String> apiList;
	
	@Autowired
	@Resource(name = "EdisonLogSQLList")
	private ArrayList<ArrayList<String>> sqlList;
	
	@Autowired
	@Qualifier("WarningNotification")
	private EmailNotification warningNotification;
	
	@Autowired(required = false)
	private AzureDAO azureDAO;

	private EdisonService edisonService;
	
	private String db;
	
	private String sysname;
	
	private String companyCode;
	
	private String poCompanyCode;
	
	@Value("${Grant.Type:}")
	private String grantType;
	
	@Value("${Client.Id:}")
	private String clientid;
	
	@Value("${Client.Secret:}")
	private String clientSecret;
	
	@Value("${API.Endpoints.Url:}")
	private String apiEdUrl;
	
	@Value("${API.Identity.Url:}")
	private String apiUrl;
	
	@Value("${API.JSON.data.log:}")
	private String apiJsonDataLog;
	
	@Value("${Test.id:}")
	private String testID;
	
	@Value("${Error.msg.exceptions:}")
	private String exMsg;
	
	@Value("${System.process.count:0}")
	private int processCount;
	
	@Value("${System.process.times:0}")
	private int processTimes;

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public ApplicationContext getAppContext() {
		return appContext;
	}

	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}

	public EdisonService getEdisonService() {
		return edisonService;
	}

	public void setEdisonService(EdisonService edisonService) {
		this.edisonService = edisonService;
	}

	public EmailNotification getWarningNotification() {
		return warningNotification;
	}
	
	public AzureDAO getAzureDAO() {
		return azureDAO;
	}

	public void setAzureDAO(AzureDAO azureDAO) {
		this.azureDAO = azureDAO;
	}

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}
	
	public String getSysname() {
		return sysname;
	}

	public void setSysname(String sysname) {
		this.sysname = sysname;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getPoCompanyCode() {
		return poCompanyCode;
	}

	public void setPoCompanyCode(String poCompanyCode) {
		this.poCompanyCode = poCompanyCode;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getClientid() {
		return clientid;
	}

	public void setClientid(String clientid) {
		this.clientid = clientid;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getApiEdUrl() {
		return apiEdUrl;
	}

	public void setApiEdUrl(String apiEdUrl) {
		this.apiEdUrl = apiEdUrl;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public ArrayList<ArrayList<String>> getSqlList() {
		return sqlList;
	}

	public void setSqlList(ArrayList<ArrayList<String>> sqlList) {
		this.sqlList = sqlList;
	}
	
	public String getApiJsonDataLog() {
		return apiJsonDataLog;
	}

	public void setApiJsonDataLog(String apiJsonDataLog) {
		this.apiJsonDataLog = apiJsonDataLog;
	}
	
	public String getTestID() {
		return testID;
	}

	public void setTestID(String testID) {
		this.testID = testID;
	}

	public int getProcessCount() {
		return processCount;
	}

	public void setProcessCount(int processCount) {
		this.processCount = processCount;
	}

	public int getProcessTimes() {
		return processTimes;
	}

	public void setProcessTimes(int processTimes) {
		this.processTimes = processTimes;
	}

	public String getExMsg() {
		return exMsg;
	}

	public String getDbName() {
		return this.getEdisonService().getDbName();
	}

	public ArrayList<String> getDirectList() {
		return directList;
	}

	public void setDirectList(ArrayList<String> directList) {
		this.directList = directList;
	}

	public HashMap<String, String> getApiList() {
		return apiList;
	}

	public void setApiList(LinkedHashMap<String, String> apiList) {
		this.apiList = apiList;
	}
	
	public void setDefaultEdisonService() throws CSFEException{
		this.setEdisonService(this.getAppContext().getBean(this.getDb() + ConstantsUtil.DEFAULT_SERVICE, EdisonService.class));
	}

	@Override
	public void processEdisonLog(String creator) throws CSFEException{
		this.setDefaultEdisonService();
		int count = 0;
		for(ArrayList<String> sqllist : this.getSqlList()) {
			logger.info("Start process "+ (++count) + " of "+this.getSqlList().size()+". System ID ["+ this.getDbName()+"], SystemName["+this.getSysname()+"], CompanyCode["+companyCode+"], poCompanyCode["+poCompanyCode+"]");
			List<EdisonLog> list = this.getEdisonService().processEdisonLog(sqllist, this.getSysname(), creator, this.getCompanyCode(), this.getPoCompanyCode());
			if(list != null && !list.isEmpty()) {
				this.getEdisonService().saveEdisonLogList(this.processShipmentEdisonLog(list, creator));
			} else {
				logger.info("Cannot find any insert or update edisonID records.");
			}
		}
	}
	
	public List<EdisonLog> processShipmentEdisonLog(List<EdisonLog> list, String creator) {
		List<EdisonLog> processList = new ArrayList<EdisonLog>();
		String msg = "";
		for(EdisonLog log : list) {
			if(ConstantsUtil.SHIPMENTS_TYPE.equals(log.getCodetype()) && !log.IsETLtoAzure()) {
				try {
					if(!StringUtil.isNullOrBlank(log.getCode2())) {
						String [] sono = log.getCode2().split("-");
						if(sono.length > 1) {
							EdisonLog bulkPO = this.getEdisonService().findEdisonIDBySONO(this.getDbName(), this.getSysname(), sono[0], sono[1], ConstantsUtil.POFULFILLMENT);
							if(bulkPO != null) processList.add(log);
						}
					}else {
						if(this.savePOFulfillment(this.getApiList().get(ConstantsUtil.POFULFILLMENT), log, creator)) processList.add(log);
					}
				} catch (Exception e) {
					msg = "System Name["+this.getSysname()+"], Office["+this.getDb()+"]," + "Cannot save or update POFulfillment record.\n"+e;
				}
			}else {
				processList.add(log);
			}
		}
		if(!StringUtil.isNullOrBlank(msg))	this.handleSendErrorNotification(new Exception(msg));
		logger.info("Find "+ list.size()+" edison ID records, and process "+processList.size()+" edison ID records");
		return processList;
	}
	
	public HashMap<String,List<EdisonLog>> findLogMapList(List<EdisonLog> list) throws CSFEException {
		HashMap<String,List<EdisonLog>> logMapList = new LinkedHashMap<String, List<EdisonLog>>();
		if(list != null && !list.isEmpty()) {
			logger.info("Find ["+ list.size()+"] PEND edison ID record(s)");
			for(EdisonLog log : list) {
				if(this.hasTestID(String.valueOf(log.getId())))
					if(logMapList.containsKey(log.getCodetype())) {
						logMapList.get(log.getCodetype()).add(log);
					}else {
						List<EdisonLog> arrayList = new ArrayList<EdisonLog>();
						arrayList.add(log);
						logMapList.put(log.getCodetype(), arrayList);
					}
			}
			if(!StringUtil.isNullOrBlank(this.getTestID())) {
				for(Entry<String, List<EdisonLog>> entry : logMapList.entrySet()) {
					logger.info("TEST "+entry.getKey()+" size["+entry.getValue().size()+"]");
					for(EdisonLog log : entry.getValue()) {
						logger.info(log.toString());
					}
				}
			}
		}
		return logMapList;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void processAzure(String creator) throws CSFEException{
		try {
			this.setDefaultEdisonService();
			HashMap<String,List<EdisonLog>> logMapList = this.findLogMapList(this.getEdisonService().findPendEdisonID(this.getSysname()));
			HashMap<String,List<EdisonLog>> deletelogMapList = this.findLogMapList(this.getEdisonService().findDeleteEdisonID(this.getSysname()));
			// Direct DB Insert or Update
			/*if(this.getDirectList() != null && !this.getDirectList().isEmpty()) {
				for(String table : this.getDirectList()) {
					logger.info("--- Start process "+table+" Direct DB Insert or Update. ---");
					this.switchTable(table, null, logMapList, false, creator);
					logger.info("--- End process "+table+" Direct DB Insert or Update. ---");
				}
			}*/
			if(this.getApiList() != null && !this.getApiList().isEmpty()) {
				this.setDefaultEdisonService();
				for(Entry<String, String> entry : this.getApiList().entrySet()) {
					if(!ConstantsUtil.POFULFILLMENT.equals(entry.getKey())) {
						logger.info("--- Start process "+entry.getKey()+" API Insert, Update or Delete. ---");
						this.switchTable(entry.getKey(), entry.getValue(), logMapList, deletelogMapList, true, creator);
						logger.info("--- End process "+entry.getKey()+" API Insert, Update or Delete. ---");
					}
				}
			}
		}catch (CSFEException e) {
			throw new CSFEException(e);
		}catch (Exception e) {
			throw new CSFEException(e);
		}
	}	

	public void switchTable(String key, String value, HashMap<String,List<EdisonLog>> logMapList, HashMap<String,List<EdisonLog>> deleteLogMapList, boolean isAPI, String creator) {
		try {
			int totalTimes = 1;
			switch (key) {
				case ConstantsUtil.ORGANIZATIONS:
					List<EdisonLog> clist = new ArrayList<EdisonLog>();
					if(logMapList.containsKey(ConstantsUtil.ORGANIZATIONS_TYPE))	clist.addAll(logMapList.get(ConstantsUtil.ORGANIZATIONS_TYPE));
					if(logMapList.containsKey(ConstantsUtil.PRINCIPALS_TYPE))	clist.addAll(logMapList.get(ConstantsUtil.PRINCIPALS_TYPE));
					totalTimes = this.getProcessTimes(clist);
					for(int times = 0; times < totalTimes ; times++) {
						logger.info("Process "+(times+1)+" of "+ totalTimes +" times to save Azure "+ConstantsUtil.ORGANIZATIONS+".");
						this.saveAzureOrganizations(value, this.getEdisonIDByProcessTimes(times, clist), creator);
					}
					break;
				case ConstantsUtil.SHIPMENTS:
					this.deleteAPI(ConstantsUtil.SHIPMENTS_TYPE, value, deleteLogMapList.get(ConstantsUtil.SHIPMENTS_TYPE), creator);
					totalTimes = this.getProcessTimes(logMapList.get(ConstantsUtil.SHIPMENTS_TYPE));
					for(int times = 0; times < totalTimes ; times++) {
						logger.info("Process "+(times+1)+" of "+ totalTimes +" times to save Azure "+ConstantsUtil.SHIPMENTS+".");
						this.saveAzureShipment(value, this.getEdisonIDByProcessTimes(times, logMapList.get(ConstantsUtil.SHIPMENTS_TYPE)), creator);
					}
					break;
				case ConstantsUtil.BILLOFLADINGS:
					this.deleteAPI(ConstantsUtil.BILLOFLADINGS_TYPE, value, deleteLogMapList.get(ConstantsUtil.BILLOFLADINGS_TYPE), creator);
					totalTimes = this.getProcessTimes(logMapList.get(ConstantsUtil.BILLOFLADINGS_TYPE));
					for(int times = 0; times < totalTimes ; times++) {
						logger.info("Process "+(times+1)+" of "+ totalTimes +" times to save Azure "+ConstantsUtil.BILLOFLADINGS+".");
						this.saveAzureBillOfLadings(value, this.getEdisonIDByProcessTimes(times, logMapList.get(ConstantsUtil.BILLOFLADINGS_TYPE)), creator);
					}
					break;
				case ConstantsUtil.MASTERBILLS:
					this.deleteAPI(ConstantsUtil.MASTERBILLS_TYPE, value, deleteLogMapList.get(ConstantsUtil.MASTERBILLS_TYPE), creator);
					totalTimes = this.getProcessTimes(logMapList.get(ConstantsUtil.MASTERBILLS_TYPE));
					for(int times = 0; times < totalTimes ; times++) {
						logger.info("Process "+(times+1)+" of "+ totalTimes +" times to save Azure "+ConstantsUtil.MASTERBILLS+".");
						this.saveAzureMasterBills(value, this.getEdisonIDByProcessTimes(times, logMapList.get(ConstantsUtil.MASTERBILLS_TYPE)), creator);
					}
					break;
				case ConstantsUtil.CONTAINERS:
					this.deleteAPI(ConstantsUtil.CONTAINERS_TYPE, value, deleteLogMapList.get(ConstantsUtil.CONTAINERS_TYPE), creator);
					totalTimes = this.getProcessTimes(logMapList.get(ConstantsUtil.CONTAINERS_TYPE));
					for(int times = 0; times < totalTimes ; times++) {
						logger.info("Process "+(times+1)+" of "+ totalTimes +" times to save Azure "+ConstantsUtil.CONTAINERS+".");
						this.saveAzureContainers(value, this.getEdisonIDByProcessTimes(times, logMapList.get(ConstantsUtil.CONTAINERS_TYPE)), creator);
					}
					break;
				case ConstantsUtil.CONSOLIDATIONS:
					this.deleteAPI(ConstantsUtil.CONSOLIDATIONS_TYPE, value, deleteLogMapList.get(ConstantsUtil.CONSOLIDATIONS_TYPE), creator);
					totalTimes = this.getProcessTimes(logMapList.get(ConstantsUtil.CONSOLIDATIONS_TYPE));
					for(int times = 0; times < totalTimes ; times++) {
						logger.info("Process "+(times+1)+" of "+ totalTimes +" times to save Azure "+ConstantsUtil.CONSOLIDATIONS+".");
						this.saveAzureConsolidations(value, this.getEdisonIDByProcessTimes(times, logMapList.get(ConstantsUtil.CONSOLIDATIONS_TYPE)), creator);
					}
					break;
				case ConstantsUtil.CONSIGNMENTS:
					this.deleteAPI(ConstantsUtil.CONSIGNMENTS_TYPE, value, deleteLogMapList.get(ConstantsUtil.CONSIGNMENTS_TYPE), creator);
					totalTimes = this.getProcessTimes(logMapList.get(ConstantsUtil.CONSIGNMENTS_TYPE));
					for(int times = 0; times < totalTimes ; times++) {
						logger.info("Process "+(times+1)+" of "+ totalTimes +" times to save Azure "+ConstantsUtil.CONSIGNMENTS+".");
						this.saveAzureConsignments(value, this.getEdisonIDByProcessTimes(times, logMapList.get(ConstantsUtil.CONSIGNMENTS_TYPE)), creator);
					}
					break;
				case ConstantsUtil.CARGODETAILS:
					this.deleteAPI(ConstantsUtil.CARGODETAILS_TYPE, value, deleteLogMapList.get(ConstantsUtil.CARGODETAILS_TYPE), creator);
					totalTimes = this.getProcessTimes(logMapList.get(ConstantsUtil.CARGODETAILS_TYPE));
					for(int times = 0; times < totalTimes ; times++) {
						logger.info("Process "+(times+1)+" of "+ totalTimes +" times to save Azure "+ConstantsUtil.CARGODETAILS+".");
					this.saveAzureCargoDetails(value, this.getEdisonIDByProcessTimes(times, logMapList.get(ConstantsUtil.CARGODETAILS_TYPE)), creator);
					}
					break;
				case ConstantsUtil.SHIPMENTLOADS:
					this.deleteAPI(ConstantsUtil.SHIPMENTLOADS_TYPE, value, deleteLogMapList.get(ConstantsUtil.SHIPMENTLOADS_TYPE), creator);
					totalTimes = this.getProcessTimes(logMapList.get(ConstantsUtil.SHIPMENTLOADS_TYPE));
					for(int times = 0; times < totalTimes ; times++) {
						logger.info("Process "+(times+1)+" of "+ totalTimes +" times to save Azure "+ConstantsUtil.SHIPMENTLOADS+".");
						this.saveAzureShipmentLoads(value, this.getEdisonIDByProcessTimes(times, logMapList.get(ConstantsUtil.SHIPMENTLOADS_TYPE)), creator);
					}
					break;
				case ConstantsUtil.SHIPMENTLOADDETAILS:
					this.deleteAPI(ConstantsUtil.SHIPMENTLOADDETAILS_TYPE, value, deleteLogMapList.get(ConstantsUtil.SHIPMENTLOADDETAILS_TYPE), creator);
					totalTimes = this.getProcessTimes(logMapList.get(ConstantsUtil.SHIPMENTLOADDETAILS_TYPE));
					for(int times = 0; times < totalTimes ; times++) {
						logger.info("Process "+(times+1)+" of "+ totalTimes +" times to save Azure "+ConstantsUtil.SHIPMENTLOADDETAILS+".");
						//this.getEdisonIDByProcessTimes(times, logMapList.get(ConstantsUtil.SHIPMENTLOADDETAILS_TYPE));
						this.saveAzureShipmentLoadDetails(value, this.getEdisonIDByProcessTimes(times, logMapList.get(ConstantsUtil.SHIPMENTLOADDETAILS_TYPE)), creator);
					}
					break;
				case ConstantsUtil.BILLOFLADINGCONTACTS:
					this.deleteAPI(ConstantsUtil.BILLOFLADINGCONTACTS_TYPE, value, deleteLogMapList.get(ConstantsUtil.BILLOFLADINGCONTACTS_TYPE), creator);
					totalTimes = this.getProcessTimes(logMapList.get(ConstantsUtil.BILLOFLADINGCONTACTS_TYPE));
					for(int times = 0; times < totalTimes ; times++) {
						logger.info("Process "+(times+1)+" of "+ totalTimes +" times to save Azure "+ConstantsUtil.BILLOFLADINGCONTACTS+".");
						this.saveAzureBillOfLadingContacts(value, this.getEdisonIDByProcessTimes(times, logMapList.get(ConstantsUtil.BILLOFLADINGCONTACTS_TYPE)), creator);
					}
					break;
				case ConstantsUtil.MASTERBILLCONTACTS:
					this.deleteAPI(ConstantsUtil.MASTERBILLCONTACTS_TYPE, value, deleteLogMapList.get(ConstantsUtil.MASTERBILLCONTACTS_TYPE), creator);
					totalTimes = this.getProcessTimes(logMapList.get(ConstantsUtil.MASTERBILLCONTACTS_TYPE));
					for(int times = 0; times < totalTimes ; times++) {
						logger.info("Process "+(times+1)+" of "+ totalTimes +" times to save Azure "+ConstantsUtil.MASTERBILLCONTACTS+".");
						this.saveAzureMasterBillContacts(value, this.getEdisonIDByProcessTimes(times, logMapList.get(ConstantsUtil.MASTERBILLCONTACTS_TYPE)), creator);
					}
					break;
				case ConstantsUtil.SHIPMENTCONTACTS:
					this.deleteAPI(ConstantsUtil.SHIPMENTCONTACTS_TYPE, value, deleteLogMapList.get(ConstantsUtil.SHIPMENTCONTACTS_TYPE), creator);
					totalTimes = this.getProcessTimes(logMapList.get(ConstantsUtil.SHIPMENTCONTACTS_TYPE));
					for(int times = 0; times < totalTimes ; times++) {
						logger.info("Process "+(times+1)+" of "+ totalTimes +" times to save Azure "+ConstantsUtil.SHIPMENTCONTACTS+".");
						this.saveAzureShipmentContacts(value, this.getEdisonIDByProcessTimes(times, logMapList.get(ConstantsUtil.SHIPMENTCONTACTS_TYPE)), creator);
					}
					break;
				case ConstantsUtil.ITINERARIES:
					this.deleteAPI(ConstantsUtil.ITINERARIES_TYPE, value, deleteLogMapList.get(ConstantsUtil.ITINERARIES_TYPE), creator);
					totalTimes = this.getProcessTimes(logMapList.get(ConstantsUtil.ITINERARIES_TYPE));
					for(int times = 0; times < totalTimes ; times++) {
						logger.info("Process "+(times+1)+" of "+ totalTimes +" times to save Azure "+ConstantsUtil.ITINERARIES+".");
						this.saveAzureItineraries(value, this.getEdisonIDByProcessTimes(times, logMapList.get(ConstantsUtil.ITINERARIES_TYPE)), creator);
					}
					break;
				case ConstantsUtil.BILLOFLADINGCONSIGNMENTS:
					this.saveAzureBillOfLadingConsignments(value, creator);
					break;
				case ConstantsUtil.BILLOFLADINGITINERARIES:
					this.saveAzureBillOfLadingItineraries(value, creator);
					break;
				case ConstantsUtil.SHIPMENTBILLOFLADINGS:
					this.saveAzureShipmentBillOfLadings(value, creator);
					break;
				case ConstantsUtil.BILLOFLADINGSHIPMENTLOADS:
					this.saveAzureBillOfLadingShipmentLoads(value, creator);
					break;
				case ConstantsUtil.CONSIGNMENTITINERARIES:
					this.saveAzureConsignmentItineraries(value, creator);
					break;
				case ConstantsUtil.CONTAINERITINERARIES:
					this.saveAzureContainerItineraries(value, creator);
					break;
				case ConstantsUtil.MASTERBILLITINERARIES:
					this.saveAzureMasterBillItineraries(value, creator);
					break;
				case ConstantsUtil.ACTIVITIES:
					this.saveAzureActivities(value, logMapList.get(ConstantsUtil.ACTIVITIES_TYPE), creator);
					break;
				case ConstantsUtil.PURCHASEORDERS:
					this.saveAzurePurchaseOrders(value, logMapList.get(ConstantsUtil.PURCHASEORDERS_TYPE), creator);
					break;
				case ConstantsUtil.CRUISEORDERS:
					this.saveAzureCruiseOrders(value, logMapList.get(ConstantsUtil.CRUISEORDERS_TYPE), creator);
					break;
				case ConstantsUtil.CRUISEORDERWAREHOUSE:
					this.saveAzureCruiseOrderWarehouse(value, logMapList.get(ConstantsUtil.CRUISEORDERWAREHOUSE_TYPE), creator);
					break;
					
			}
		}catch (CSFEException e) {
			logger.error(e);
			this.handleSendErrorNotification(e);
		}catch (ParseException e) {
			logger.error(e);
			this.handleSendErrorNotification(e);
		}catch (Exception e) {
			logger.error(e);
			this.handleSendErrorNotification(e);
		}

	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureOrganizations(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<Organizations> resultList = this.getEdisonService().findOrganizations(this.getDbName(), this.getSysname(), this.getEdisonID(list));
		
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" Organizations record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]" );
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				for(Organizations result : resultList) {
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						if(log != null) {
							if(!StringUtil.isNullOrBlank(apiUrl)) {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.doPutJson(apiUrl, String.valueOf(result.getId()), token, gson.toJson(result));
									updateCount++;
								}else {
									log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
									insertCount++;
								}
							} else {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.getAzureDAO().updateOrganizations(result);
									updateCount++;
								}else {
									this.getAzureDAO().saveOrganizations(result);
									insertCount++;
								}
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "System Name["+this.getSysname()+"], Office["+this.getDb()+"]," + "Cannot save or update Organizations record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "System Name["+this.getSysname()+"], Office["+this.getDb()+"]," + "Cannot save or update Organizations record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" Organizations record(s) have been inserted.");
				logger.info("There are "+updateCount+" Organizations record(s) have been updated.");
			}else {
				logger.info("Cannot find any Organizations record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg))this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for Organizations");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureShipment(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<Shipments> resultList = this.getEdisonService().findShipments(this.getDbName(), this.getSysname(), this.getEdisonID(list), this.getPoCompanyCode());
		
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" Shipments record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				for(Shipments result : resultList) {
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						if(log != null) {
							if(!StringUtil.isNullOrBlank(apiUrl)) {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.doPutJson(apiUrl, String.valueOf(result.getId()), token, gson.toJson(result));
									updateCount++;
								}else {
									log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
									insertCount++;
								}
							} else {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.getAzureDAO().updateShipment(result);
									updateCount++;
								}else {
									this.getAzureDAO().saveShipment(result);
									insertCount++;
								}
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update Shipment record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update Shipment record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" Shipment record(s) have been inserted.");
				logger.info("There are "+updateCount+" Shipment record(s) have been updated.");
			}else {
				logger.info("Cannot find any Shipments record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg))this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for Shipment");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureBillOfLadings(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<BillOfLadings> resultList = this.getEdisonService().findBillOfLadings(this.getDbName(), this.getSysname(), this.getCompanyCode(), this.getEdisonID(list));
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" BillOfLadings record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				for(BillOfLadings result : resultList) {
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						if(log != null) {
							if(!StringUtil.isNullOrBlank(apiUrl)) {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.doPutJson(apiUrl, String.valueOf(result.getId()), token, gson.toJson(result));
									updateCount++;
								}else {
									log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
									insertCount++;
								}
							} else {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.getAzureDAO().updateBillOfLadings(result);
									updateCount++;
								}else {
									this.getAzureDAO().saveBillOfLadings(result);
									insertCount++;
								}
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update BillOfLadings record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update BillOfLadings record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" BillOfLadings record(s) have been inserted.");
				logger.info("There are "+updateCount+" BillOfLadings record(s) have been updated.");
				
			}else {
				logger.info("Cannot find any BillOfLadings record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for BillOfLadings");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureMasterBills(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<MasterBills> resultList = this.getEdisonService().findMasterBills(this.getDbName(), this.getSysname(), this.getCompanyCode(), this.getEdisonID(list));
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" MasterBills record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				for(MasterBills result : resultList) {
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						if(log != null) {
							if(!StringUtil.isNullOrBlank(apiUrl)) {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.doPutJson(apiUrl, String.valueOf(result.getId()), token, gson.toJson(result));
									updateCount++;
								}else {
									log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
									insertCount++;
								}
							} else {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.getAzureDAO().updateMasterBills(result);
									updateCount++;
								}else {
									this.getAzureDAO().saveMasterBills(result);
									insertCount++;
								}
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update  MasterBills record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update MasterBills record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" MasterBills record(s) have been inserted.");
				logger.info("There are "+updateCount+" MasterBills record(s) have been updated.");
				
			}else {
				logger.info("Cannot find any MasterBills record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for MasterBills");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureContainers(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<Containers> resultList = this.getEdisonService().findContainers(this.getDbName(), this.getSysname(), this.getEdisonID(list));
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" Containers record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				for(Containers result : resultList) {
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						if(log != null) {
							if(!StringUtil.isNullOrBlank(apiUrl)) {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.doPutJson(apiUrl, String.valueOf(result.getId()), token, gson.toJson(result));
									updateCount++;
								}else {
									log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
									insertCount++;
								}
							} else {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.getAzureDAO().updateContainers(result);
									updateCount++;
								}else {
									this.getAzureDAO().saveContainers(result);
									insertCount++;
								}
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update  Containers record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update Containers record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" Containers record(s) have been inserted.");
				logger.info("There are "+updateCount+" Containers record(s) have been updated.");
				
			}else {
				logger.info("Cannot find any Containers record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for Containers");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureConsolidations(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<Consolidations> resultList = this.getEdisonService().findConsolidations(this.getDbName(), this.getSysname(), this.getEdisonID(list));
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" Consolidations record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				for(Consolidations result : resultList) {
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						if(log != null) {
							if(!StringUtil.isNullOrBlank(apiUrl)) {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.doPutJson(apiUrl, String.valueOf(result.getId()), token, gson.toJson(result));
									updateCount++;
								}else {
									log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
									insertCount++;
								}
							} else {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.getAzureDAO().updateConsolidations(result);
									updateCount++;
								}else {
									this.getAzureDAO().saveConsolidations(result);
									insertCount++;
								}
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update  Consolidations record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update Consolidations record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" Consolidations record(s) have been inserted.");
				logger.info("There are "+updateCount+" Consolidations record(s) have been updated.");
				
			}else {
				logger.info("Cannot find any Consolidations record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for Consolidations");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureConsignments(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<Consignments> resultList = this.getEdisonService().findConsignments(this.getDbName(), this.getSysname(), this.getCompanyCode(), this.getEdisonID(list));
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" Consignments record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				for(Consignments result : resultList) {
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						if(log != null) {
							if(!StringUtil.isNullOrBlank(apiUrl)) {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.doPutJson(apiUrl, String.valueOf(result.getId()), token, gson.toJson(result));
									updateCount++;
								}else {
									log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
									insertCount++;
								}
							} else {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.getAzureDAO().updateConsignments(result);
									updateCount++;
								}else {
									this.getAzureDAO().saveConsignments(result);
									insertCount++;
								}
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update  Consignments record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update Consignments record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" Consignments record(s) have been inserted.");
				logger.info("There are "+updateCount+" Consignments record(s) have been updated.");
				
			}else {
				logger.info("Cannot find any Consignments record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for Consignments");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureCargoDetails(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<CargoDetails> resultList = this.getEdisonService().findCargoDetails(this.getDbName(), this.getSysname(), this.getEdisonID(list));
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" CargoDetails record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				for(CargoDetails result : resultList) {
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						if(log != null) {
							if(!StringUtil.isNullOrBlank(apiUrl)) {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.doPutJson(apiUrl, String.valueOf(result.getId()), token, gson.toJson(result));
									updateCount++;
								}else {
									log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
									insertCount++;
								}
							} else {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.getAzureDAO().updateCargoDetails(result);
									updateCount++;
								}else {
									this.getAzureDAO().saveCargoDetails(result);
									insertCount++;
								}
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find any EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update  CargoDetails record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update CargoDetails record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" CargoDetails record(s) have been inserted.");
				logger.info("There are "+updateCount+" CargoDetails record(s) have been updated.");
				
			} else {
				logger.info("Cannot find any CargoDetails record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for CargoDetails");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureShipmentLoads(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<ShipmentLoads> resultList = this.getEdisonService().findShipmentLoads(this.getDbName(), this.getSysname(), this.getEdisonID(list));
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" ShipmentLoads record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				for(ShipmentLoads result : resultList) {
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						if(log != null) {
							if(!StringUtil.isNullOrBlank(apiUrl)) {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.doPutJson(apiUrl, String.valueOf(result.getId()), token, gson.toJson(result));
									updateCount++;
								}else {
									log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
									insertCount++;
								}
							} else {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.getAzureDAO().updateShipmentLoads(result);
									updateCount++;
								}else {
									this.getAzureDAO().saveShipmentLoads(result);
									insertCount++;
								}
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update  ShipmentLoads record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update ShipmentLoads record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" ShipmentLoads record(s) have been inserted.");
				logger.info("There are "+updateCount+" ShipmentLoads record(s) have been updated.");
				
			}else {
				logger.info("Cannot find any ShipmentLoads record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for ShipmentLoads");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureShipmentLoadDetails(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<ShipmentLoadDetails> resultList = this.getEdisonService().findShipmentLoadDetails(this.getDbName(), this.getSysname(), this.getEdisonID(list));
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" ShipmentLoadDetails record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				for(ShipmentLoadDetails result : resultList) {
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						if(log != null) {
							if(!StringUtil.isNullOrBlank(apiUrl)) {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.doPutJson(apiUrl, String.valueOf(result.getId()), token, gson.toJson(result));
									updateCount++;
								}else {
									log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
									insertCount++;
								}
							} else {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.getAzureDAO().updateShipmentLoadDetails(result);
									updateCount++;
								}else {
									this.getAzureDAO().saveShipmentLoadDetails(result);
									insertCount++;
								}
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update  ShipmentLoadDetails record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update ShipmentLoadDetails record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" ShipmentLoadDetails record(s) have been inserted.");
				logger.info("There are "+updateCount+" ShipmentLoadDetails record(s) have been updated.");
				
			}else {
				logger.info("Cannot find any ShipmentLoadDetails record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for ShipmentLoadDetails");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureBillOfLadingContacts(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<BillOfLadingContacts> resultList = this.getEdisonService().findBillOfLadingContacts(this.getDbName(), this.getSysname(), this.getCompanyCode(), this.getEdisonID(list));
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" BillOfLadingContacts record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				for(BillOfLadingContacts result : resultList) {
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						if(log != null) {
							if(!StringUtil.isNullOrBlank(apiUrl)) {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.doPutJson(apiUrl, String.valueOf(result.getId()), token, gson.toJson(result));
									updateCount++;
								}else {
									log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
									insertCount++;
								}
							} else {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.getAzureDAO().updateBillOfLadingContacts(result);
									updateCount++;
								}else {
									this.getAzureDAO().saveBillOfLadingContacts(result);
									insertCount++;
								}
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update  BillOfLadingContacts record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update BillOfLadingContacts record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" BillOfLadingContacts record(s) have been inserted.");
				logger.info("There are "+updateCount+" BillOfLadingContacts record(s) have been updated.");
				
			}else {
				logger.info("Cannot find any BillOfLadingContacts record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for BillOfLadingContacts");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureMasterBillContacts(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<MasterBillContacts> resultList = this.getEdisonService().findMasterBillContacts(this.getDbName(), this.getSysname(), this.getEdisonID(list));
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" MasterBillContacts record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				for(MasterBillContacts result : resultList) {
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						if(log != null) {
							if(!StringUtil.isNullOrBlank(apiUrl)) {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.doPutJson(apiUrl, String.valueOf(result.getId()), token, gson.toJson(result));
									updateCount++;
								}else {
									log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
									insertCount++;
								}
							} else {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.getAzureDAO().updateMasterBillContacts(result);
									updateCount++;
								}else {
									this.getAzureDAO().saveMasterBillContacts(result);
									insertCount++;
								}
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update  MasterBillContacts record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update MasterBillContacts record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" MasterBillContacts record(s) have been inserted.");
				logger.info("There are "+updateCount+" MasterBillContacts record(s) have been updated.");
				
			}else {
				logger.info("Cannot find any MasterBillContacts record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for MasterBillContacts");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureShipmentContacts(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<ShipmentContacts> resultList = this.getEdisonService().findShipmentContacts(this.getDbName(), this.getSysname(), this.getCompanyCode(), this.getEdisonID(list));
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" ShipmentContacts record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				for(ShipmentContacts result : resultList) {
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						if(log != null) {
							if(!StringUtil.isNullOrBlank(apiUrl)) {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.doPutJson(apiUrl, String.valueOf(result.getId()), token, gson.toJson(result));
									updateCount++;
								}else {
									log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
									insertCount++;
								}
							} else {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.getAzureDAO().updateShipmentContacts(result);
									updateCount++;
								}else {
									this.getAzureDAO().saveShipmentContacts(result);
									insertCount++;
								}
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update  ShipmentContacts record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update ShipmentContacts record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" ShipmentContacts record(s) have been inserted.");
				logger.info("There are "+updateCount+" ShipmentContacts record(s) have been updated.");
				
			}else {
				logger.info("Cannot find any ShipmentContacts record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for ShipmentContacts");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureItineraries(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<Itineraries> resultList = this.getEdisonService().findItineraries(this.getDbName(), this.getSysname(), this.getEdisonID(list));
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" Itineraries record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				for(Itineraries result : resultList) {
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						if(log != null) {
							if(!StringUtil.isNullOrBlank(apiUrl)) {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.doPutJson(apiUrl, String.valueOf(result.getId()), token, gson.toJson(result));
									updateCount++;
								}else {
									log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
									insertCount++;
								}
							} else {
								if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
									result.setId(log.getAzureId());
									this.getAzureDAO().updateItineraries(result);
									updateCount++;
								}else {
									this.getAzureDAO().saveItineraries(result);
									insertCount++;
								}
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update Itineraries record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update Itineraries record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" Itineraries record(s) have been inserted.");
				logger.info("There are "+updateCount+" Itineraries record(s) have been updated.");
				
			}else {
				logger.info("Cannot find any Itineraries record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for Itineraries");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureBillOfLadingConsignments(String apiUrl, String creator) throws CSFEException, IOException{
		String errMsg = "";
		String sysdate = new SimpleDateFormat(ConstantsUtil.SIMPLE_DATE).format(new Date());
		logger.debug("Current system date (sysdate): " + sysdate);
		List<BillOfLadingConsignments> resultDeleteList = this.getEdisonService().findDeleteBillOfLadingConsignments(this.getDbName(), this.getSysname(), this.getCompanyCode(), sysdate);
		if(resultDeleteList != null && !resultDeleteList.isEmpty()) {
			logger.info("Find "+resultDeleteList.size()+" BillOfLadingConsignments delete record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			int deleteCount = 0;
			JSONObject token= null;
			if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
			for(BillOfLadingConsignments result : resultDeleteList) {
				try {
					if(!StringUtil.isNullOrBlank(apiUrl))
						this.doDeleteJson(apiUrl, token, result, 0);
					else
						this.getAzureDAO().deleteBillOfLadingConsignments(result);
					deleteCount++;
				} catch (CSFEException e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot delete BillOfLadingConsignments record. "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				} catch (Exception e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot delete BillOfLadingConsignments record. "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				}
			}
			logger.info("There are "+deleteCount+" BillOfLadingConsignments record(s) have been deleted.");
		}else {
			logger.info("Cannot find any BillOfLadingConsignments record for DELETE, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
		}
		List<BillOfLadingConsignments> resultList = this.getEdisonService().findBillOfLadingConsignments(this.getDbName(), this.getSysname(), this.getCompanyCode(), sysdate);
		if(resultList != null && !resultList.isEmpty()) {
			logger.info("Find "+resultList.size()+" BillOfLadingConsignments record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			int insertCount = 0;
			JSONObject token= null;
			Gson gson = new Gson();
			if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
			for(BillOfLadingConsignments result : resultList) {
				try {
					if(!StringUtil.isNullOrBlank(apiUrl))
						this.doPostJson(apiUrl, token, gson.toJson(result));
					else
						this.getAzureDAO().saveBillOfLadingConsignments(result);
					insertCount++;
				} catch (CSFEException e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save BillOfLadingConsignments record. "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				} catch (Exception e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save BillOfLadingConsignments record. "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				}
			}
			logger.info("There are "+insertCount+" BillOfLadingConsignments record(s) have been inserted.");
		}else {
			logger.info("Cannot find any BillOfLadingConsignments record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
		}
		if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureBillOfLadingItineraries(String apiUrl, String creator) throws CSFEException, IOException{
		String errMsg = "";
		String sysdate = new SimpleDateFormat(ConstantsUtil.SIMPLE_DATE).format(new Date());
		logger.debug("Current system date (sysdate): " + sysdate);
		List<BillOfLadingItineraries> resultDeleteList = this.getEdisonService().findDeleteBillOfLadingItineraries(this.getDbName(), this.getSysname(), sysdate);
		if(resultDeleteList != null && !resultDeleteList.isEmpty()) {
			logger.info("Find "+resultDeleteList.size()+" BillOfLadingItineraries delete record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			int deleteCount = 0;
			JSONObject token= null;
			if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
			for(BillOfLadingItineraries result : resultDeleteList) {
				try {
					if(!StringUtil.isNullOrBlank(apiUrl))
						this.doDeleteJson(apiUrl, token, result, 0);
					else
						this.getAzureDAO().deleteBillOfLadingItineraries(result);
					deleteCount++;
				} catch (CSFEException e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot delete BillOfLadingItineraries record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				} catch (Exception e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot delete BillOfLadingItineraries record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				}
			}
			logger.info("There are "+deleteCount+" BillOfLadingItineraries record(s) have been deleted.");
		}else {
			logger.info("Cannot find any BillOfLadingItineraries record for DELETE, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
		}
		List<BillOfLadingItineraries> resultList = this.getEdisonService().findBillOfLadingItineraries(this.getDbName(), this.getSysname(), sysdate);
		if(resultList != null && !resultList.isEmpty()) {
			logger.info("Find "+resultList.size()+" BillOfLadingItineraries record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			int insertCount = 0;
			JSONObject token= null;
			Gson gson = new Gson();
			if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
			for(BillOfLadingItineraries result : resultList) {
				try {
					if(!StringUtil.isNullOrBlank(apiUrl))
						this.doPostJson(apiUrl, token, gson.toJson(result));
					else
						this.getAzureDAO().saveBillOfLadingItineraries(result);
					insertCount++;
				} catch (CSFEException e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save BillOfLadingItineraries record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				} catch (Exception e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save BillOfLadingItineraries record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				}
			}
			logger.info("There are "+insertCount+" BillOfLadingItineraries record(s) have been inserted.");
		}else {
			logger.info("Cannot find any BillOfLadingItineraries record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
		}
		if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureShipmentBillOfLadings(String apiUrl, String creator) throws CSFEException, IOException{
		String errMsg = "";
		String sysdate = new SimpleDateFormat(ConstantsUtil.SIMPLE_DATE).format(new Date());
		logger.debug("Current system date (sysdate): " + sysdate);
		List<ShipmentBillOfLadings> resultDeleteList = this.getEdisonService().findDeleteShipmentBillOfLadings(this.getDbName(), this.getSysname(), sysdate);
		if(resultDeleteList != null && !resultDeleteList.isEmpty()) {
			logger.info("Find "+resultDeleteList.size()+" ShipmentBillOfLadings delete record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			int deleteCount = 0;
			JSONObject token= null;
			if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
			for(ShipmentBillOfLadings result : resultDeleteList) {
				try {
					if(!StringUtil.isNullOrBlank(apiUrl))
						this.doDeleteJson(apiUrl, token, result, 0);
					else
						this.getAzureDAO().deleteShipmentBillOfLadings(result);
					deleteCount++;
				} catch (CSFEException e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot delete ShipmentBillOfLadings record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				} catch (Exception e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot delete ShipmentBillOfLadings record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				}
			}
			logger.info("There are "+deleteCount+" ShipmentBillOfLadings record(s) have been deleted.");
		}else {
			logger.info("Cannot find any ShipmentBillOfLadings record for DELETE, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
		}
		List<ShipmentBillOfLadings> resultList = this.getEdisonService().findShipmentBillOfLadings(this.getDbName(), this.getSysname(), sysdate);
		if(resultList != null && !resultList.isEmpty()) {
			logger.info("Find "+resultList.size()+" ShipmentBillOfLadings record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			int insertCount = 0;
			JSONObject token= null;
			Gson gson = new Gson();
			if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
			for(ShipmentBillOfLadings result : resultList) {
				try {
					if(!StringUtil.isNullOrBlank(apiUrl))
						this.doPostJson(apiUrl, token, gson.toJson(result));
					else
						this.getAzureDAO().saveShipmentBillOfLadings(result);
					insertCount++;
				} catch (CSFEException e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save ShipmentBillOfLadings record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				} catch (Exception e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save ShipmentBillOfLadings record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				}
			}
			logger.info("There are "+insertCount+" ShipmentBillOfLadings record(s) have been inserted.");
		}else {
			logger.info("Cannot find any ShipmentBillOfLadings record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
		}
		if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureBillOfLadingShipmentLoads(String apiUrl, String creator) throws CSFEException, IOException{
		String errMsg = "";
		String sysdate = new SimpleDateFormat(ConstantsUtil.SIMPLE_DATE).format(new Date());
		logger.debug("Current system date (sysdate): " + sysdate);
		List<BillOfLadingShipmentLoads> resultDeleteList = this.getEdisonService().findDeleteBillOfLadingShipmentLoads(this.getDbName(), this.getSysname(), sysdate);
		if(resultDeleteList != null && !resultDeleteList.isEmpty()) {
			logger.info("Find "+resultDeleteList.size()+" BillOfLadingShipmentLoads delete record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			int deleteCount = 0;
			JSONObject token= null;
			if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
			for(BillOfLadingShipmentLoads result : resultDeleteList) {
				try {
					if(!StringUtil.isNullOrBlank(apiUrl))
						this.doDeleteJson(apiUrl, token, result, 0);
					else
						this.getAzureDAO().deleteBillOfLadingShipmentLoads(result);
					deleteCount++;
				} catch (CSFEException e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot delete BillOfLadingShipmentLoads record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				} catch (Exception e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot delete BillOfLadingShipmentLoads record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				}
			}
			logger.info("There are "+deleteCount+" BillOfLadingShipmentLoads record(s) have been deleted.");
		}else {
			logger.info("Cannot find any BillOfLadingShipmentLoads record for DELETE, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
		}
		List<BillOfLadingShipmentLoads> resultList = this.getEdisonService().findBillOfLadingShipmentLoads(this.getDbName(), this.getSysname(), sysdate);
		if(resultList != null && !resultList.isEmpty()) {
			logger.info("Find "+resultList.size()+" BillOfLadingShipmentLoads record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			int insertCount = 0;
			JSONObject token= null;
			Gson gson = new Gson();
			if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
			for(BillOfLadingShipmentLoads result : resultList) {
				try {
					if(!StringUtil.isNullOrBlank(apiUrl))
						this.doPostJson(apiUrl, token, gson.toJson(result));
					else
						this.getAzureDAO().saveBillOfLadingShipmentLoads(result);
					insertCount++;
				} catch (CSFEException e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save BillOfLadingShipmentLoads record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				} catch (Exception e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save BillOfLadingShipmentLoads record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				}
			}
			logger.info("There are "+insertCount+" BillOfLadingShipmentLoads record(s) have been inserted.");
		}else {
			logger.info("Cannot find any BillOfLadingShipmentLoads record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
		}
		if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureConsignmentItineraries(String apiUrl, String creator) throws CSFEException, IOException{
		String errMsg = "";
		String sysdate = new SimpleDateFormat(ConstantsUtil.SIMPLE_DATE).format(new Date());
		logger.debug("Current system date (sysdate): " + sysdate);
		List<ConsignmentItineraries> resultDeleteList = this.getEdisonService().findDeleteConsignmentItineraries(this.getDbName(), this.getSysname(), this.getCompanyCode(), sysdate);
		if(resultDeleteList != null && !resultDeleteList.isEmpty()) {
			logger.info("Find "+resultDeleteList.size()+" ConsignmentItineraries delete record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			int deleteCount = 0;
			JSONObject token= null;
			if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
			for(ConsignmentItineraries result : resultDeleteList) {
				try {
					if(!StringUtil.isNullOrBlank(apiUrl))
						this.doDeleteJson(apiUrl, token, result, 0);
					else
						this.getAzureDAO().deleteConsignmentItineraries(result);
					deleteCount++;
				} catch (CSFEException e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot delete ConsignmentItineraries record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				} catch (Exception e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot delete ConsignmentItineraries record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				}
			}
			logger.info("There are "+deleteCount+" ConsignmentItineraries record(s) have been deleted.");
		}else {
			logger.info("Cannot find any ConsignmentItineraries record for DELETE, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
		}
		List<ConsignmentItineraries> resultList = this.getEdisonService().findConsignmentItineraries(this.getDbName(), this.getSysname(), this.getCompanyCode(), sysdate);
		if(resultList != null && !resultList.isEmpty()) {
			logger.info("Find "+resultList.size()+" ConsignmentItineraries record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			int insertCount = 0;
			JSONObject token= null;
			Gson gson = new Gson();
			if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
			for(ConsignmentItineraries result : resultList) {
				try {
					if(!StringUtil.isNullOrBlank(apiUrl))
						this.doPostJson(apiUrl, token, gson.toJson(result));
					else
						this.getAzureDAO().saveConsignmentItineraries(result);
					insertCount++;
				} catch (CSFEException e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save ConsignmentItineraries record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				} catch (Exception e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save ConsignmentItineraries record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				}
			}
			logger.info("There are "+insertCount+" ConsignmentItineraries record(s) have been inserted.");
		}else {
			logger.info("Cannot find any ConsignmentItineraries record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
		}
		if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureContainerItineraries(String apiUrl, String creator) throws CSFEException, IOException{
		String errMsg = "";
		String sysdate = new SimpleDateFormat(ConstantsUtil.SIMPLE_DATE).format(new Date());
		logger.debug("Current system date (sysdate): " + sysdate);
		List<ContainerItineraries> resultDeleteList = this.getEdisonService().findDeleteContainerItineraries(this.getDbName(), this.getSysname(), sysdate);
		if(resultDeleteList != null && !resultDeleteList.isEmpty()) {
			logger.info("Find "+resultDeleteList.size()+" ContainerItineraries delete record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			int deleteCount = 0;
			JSONObject token= null;
			if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
			for(ContainerItineraries result : resultDeleteList) {
				try {
					if(!StringUtil.isNullOrBlank(apiUrl))
						this.doDeleteJson(apiUrl, token, result, 0);
					else
						this.getAzureDAO().deleteContainerItineraries(result);
					deleteCount++;
				} catch (CSFEException e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot delete ContainerItineraries record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				} catch (Exception e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot delete ContainerItineraries record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				}
			}
			logger.info("There are "+deleteCount+" ContainerItineraries record(s) have been deleted.");
		}else {
			logger.info("Cannot find any ContainerItineraries record for DELETE, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
		}
		List<ContainerItineraries> resultList = this.getEdisonService().findContainerItineraries(this.getDbName(), this.getSysname(), sysdate);
		if(resultList != null && !resultList.isEmpty()) {
			logger.info("Find "+resultList.size()+" ContainerItineraries record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			int insertCount = 0;
			JSONObject token= null;
			Gson gson = new Gson();
			if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
			for(ContainerItineraries result : resultList) {
				try {
					if(!StringUtil.isNullOrBlank(apiUrl))
						this.doPostJson(apiUrl, token, gson.toJson(result));
					else
						this.getAzureDAO().saveContainerItineraries(result);
					insertCount++;
				} catch (CSFEException e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save ContainerItineraries record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				} catch (Exception e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save ContainerItineraries record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				}
			}
			logger.info("There are "+insertCount+" ContainerItineraries record(s) have been inserted.");
		}else {
			logger.info("Cannot find any ContainerItineraries record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
		}
		if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureMasterBillItineraries(String apiUrl, String creator) throws CSFEException, IOException{
		String errMsg = "";
		String sysdate = new SimpleDateFormat(ConstantsUtil.SIMPLE_DATE).format(new Date());
		logger.debug("Current system date (sysdate): " + sysdate);
		List<MasterBillItineraries> resultDeleteList = this.getEdisonService().findDeleteMasterBillItineraries(this.getDbName(), this.getSysname(), sysdate);
		if(resultDeleteList != null && !resultDeleteList.isEmpty()) {
			logger.info("Find "+resultDeleteList.size()+" MasterBillItineraries delete record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			int deleteCount = 0;
			JSONObject token= null;
			if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
			for(MasterBillItineraries result : resultDeleteList) {
				try {
					if(!StringUtil.isNullOrBlank(apiUrl))
						this.doDeleteJson(apiUrl, token, result, 0);
					else
						this.getAzureDAO().deleteMasterBillItineraries(result);
					deleteCount++;
				} catch (CSFEException e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot delete MasterBillItineraries record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				} catch (Exception e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot delete MasterBillItineraries record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				}
			}
			logger.info("There are "+deleteCount+" MasterBillItineraries record(s) have been deleted.");
		} else {
			logger.info("Cannot find any MasterBillItineraries record for DELETE, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
		}
		List<MasterBillItineraries> resultList = this.getEdisonService().findMasterBillItineraries(this.getDbName(), this.getSysname(), sysdate);
		if(resultList != null && !resultList.isEmpty()) {
			logger.info("Find "+resultList.size()+" MasterBillItineraries record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			int insertCount = 0;
			JSONObject token= null;
			Gson gson = new Gson();
			if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
			for(MasterBillItineraries result : resultList) {
				try {
					if(!StringUtil.isNullOrBlank(apiUrl))
						this.doPostJson(apiUrl, token, gson.toJson(result));
					else
						this.getAzureDAO().saveMasterBillItineraries(result);
					insertCount++;
				} catch (CSFEException e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save MasterBillItineraries record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				} catch (Exception e) {
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save MasterBillItineraries record(s). "+result.toString()+"\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				}
			}
			logger.info("There are "+insertCount+" MasterBillItineraries record(s) have been inserted.");
		} else {
			logger.info("Cannot find any MasterBillItineraries record(s), by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
		}
		if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureActivities(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<Activities> resultList = this.getEdisonService().findActivities(this.getDbName(), this.getSysname(), this.getEdisonID(list));

			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" Activities record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token = this.doPostToken(this.getApiUrl());
				Gson gson = new Gson();
				int insertCount = 0;
				int updateCount = 0;
				for(Activities result : resultList) {
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						if(log != null) {
							if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
								result.setId(log.getAzureId());
								this.doPutJson(apiUrl, String.valueOf(result.getId()), token, gson.toJson(result));
								updateCount++;
							}else {
								log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
								insertCount++;
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update Activities record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update Activities record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" Activities record(s) have been inserted.");
				logger.info("There are "+updateCount+" Activities record(s) have been updated.");
				
			}else {
				logger.info("Cannot find any Activities record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for Activities");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzurePurchaseOrders(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<PurchaseOrders> resultList = this.getEdisonService().findPurchaseOrders(this.getDbName(), this.getSysname(), this.getEdisonID(list));
			List<Contacts> resultContactsList = this.getEdisonService().findContacts(this.getDbName(), this.getSysname(), this.getEdisonID(list));
			
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" PurchaseOrders record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				
				HashMap<Integer, PurchaseOrders> purchaseOrdersList = new HashMap<Integer, PurchaseOrders>();
				for(PurchaseOrders result : resultList) {
					if(purchaseOrdersList.containsKey(Math.toIntExact(result.getId()))) {
						PurchaseOrders po = purchaseOrdersList.get(Math.toIntExact(result.getId()));
						if(!StringUtil.isNullOrBlank(result.getNumberOfLineItems()) && Integer.parseInt(result.getNumberOfLineItems()) > 0)
							po.addLinerItems(new LineItems(result)); 
					}else {
						result.packupLinerItems();
						if(!StringUtil.isNullOrBlank(result.getNumberOfLineItems()) && Integer.parseInt(result.getNumberOfLineItems()) == 0)
							result.resetLinerItems();
						purchaseOrdersList.put(Math.toIntExact(result.getId()), result);
					}
				}
				for(Entry<Integer, PurchaseOrders> entry : purchaseOrdersList.entrySet()) {
					PurchaseOrders result = entry.getValue();
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						this.packupPurchaseOrders(result, resultContactsList);
						if(log != null) {
							if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
								result.setId(log.getAzureId());
								this.doPutJson(apiUrl, result.getPOKey(), token, gson.toJson(result));
								updateCount++;
							}else {
								log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
								insertCount++;
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update PurchaseOrders record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update PurchaseOrders record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" PurchaseOrders record(s) have been inserted.");
				logger.info("There are "+updateCount+" PurchaseOrders record(s) have been updated.");
				
			}else {
				logger.info("Cannot find any PurchaseOrders record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for PurchaseOrders");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureCruiseOrders(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<CruiseOrders> resultList = this.getEdisonService().findCruiseOrders(this.getDbName(), this.getSysname(), this.getEdisonID(list));
			List<CruiseOrderContacts> resultContactsList = this.getEdisonService().findCruiseOrderContacts(this.getDbName(), this.getSysname(), this.getEdisonID(list));
			
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" CruiseOrders record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				
				HashMap<Integer, CruiseOrders> ordersList = new HashMap<Integer, CruiseOrders>();
				for(CruiseOrders result : resultList) {
					if(ordersList.containsKey(Math.toIntExact(result.getId()))) {
						CruiseOrders po = ordersList.get(Math.toIntExact(result.getId()));
						if(!StringUtil.isNullOrBlank(result.getPOStatus()) && Integer.parseInt(result.getPOStatus()) > 0)
							po.addItems(new Items(result)); 
					}else {
						result.packupItems();
						if(!StringUtil.isNullOrBlank(result.getPOStatus()) && Integer.parseInt(result.getPOStatus()) == 0)
							result.resetItems();
						ordersList.put(Math.toIntExact(result.getId()), result);
					}
				}
				for(Entry<Integer, CruiseOrders> entry : ordersList.entrySet()) {
					CruiseOrders result = entry.getValue();
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						this.packupCruiseOrders(result, resultContactsList);
						if(log != null) {
							if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
								this.doPutJson(apiUrl, String.valueOf(log.getAzureId()), token, gson.toJson(result));
								updateCount++;
							}else {
								log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
								insertCount++;
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update CruiseOrders record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update CruiseOrders record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" CruiseOrders record(s) have been inserted.");
				logger.info("There are "+updateCount+" CruiseOrders record(s) have been updated.");
				
			}else {
				logger.info("Cannot find any CruiseOrders record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for CruiseOrders");
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void saveAzureCruiseOrderWarehouse(String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			List<CruiseOrderWarehouse> resultList = this.getEdisonService().findCruiseOrderWarehouse(this.getDbName(), this.getSysname(), this.getEdisonID(list));
			
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" CruiseOrderWarehouse record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				int insertCount = 0;
				int updateCount = 0;
				
				for(CruiseOrderWarehouse result : resultList) {
					EdisonLog log = this.findEdisonLog(result.getId(), list);
					try {
						if(log != null) {
							if(log.IsETLtoAzure() && log.getAzureId() > 0 ) {
								result.setId(log.getAzureId());
								this.doPutJson(apiUrl, String.valueOf(result.getId()), token, gson.toJson(result));
								updateCount++;
							}else {
								log.setAzureId((long)this.doPostJson(apiUrl, token, gson.toJson(result)).get("id"));
								insertCount++;
							}
							this.saveEdisonLog(log, result.getUpdatedDate());
						}else {
							logger.info("Cannot find EdisonID record, ID["+result.getId()+"]");
						}
					} catch (CSFEException e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update CruiseOrderWarehouse record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveFailureEdisonLog(log,result.getUpdatedDate(),e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update CruiseOrderWarehouse record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				logger.info("There are "+insertCount+" CruiseOrderWarehouse record(s) have been inserted.");
				logger.info("There are "+updateCount+" CruiseOrderWarehouse record(s) have been updated.");
				
			}else {
				logger.info("Cannot find any CruiseOrderWarehouse record, by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
		}else {
			logger.info("Cannot find any PEND Edison record for CruiseOrderWarehouse");
		}
	}
	
	/*@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public boolean savePOFulfillment(String apiUrl, POFulfillment result, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(result != null) {
			JSONObject token= null;
			Gson gson = new Gson();
			if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
			List<EdisonLog> edisonLogList = new ArrayList<EdisonLog>();
			for(EdisonLog log : list) {
				EdisonLog newlog = new EdisonLog();
				newlog.setCode(log.getCode());
				newlog.setSeq(log.getSeq());
				newlog.setCode2(!StringUtil.isNullOrBlank(result.getBillOfLadingHeader())?result.getBillOfLadingHeader().trim():"");
				newlog.setCode3(!StringUtil.isNullOrBlank(result.getBookingReferenceNo())?result.getBookingReferenceNo().trim():"");
				newlog.setCodetype(ConstantsUtil.POFULFILLMENT_TYPE);
				newlog.setDbname(this.getDbName());
				edisonLogList.add(newlog);
			}
			try {
				this.doPostJson(apiUrl, token, gson.toJson(result));
				for(EdisonLog log : edisonLogList) this.saveNewEdisonLog(log, false, null);
			} catch (CSFEException e) {
				for(EdisonLog log : list) this.saveNewEdisonLog(log, true,  e);
				String msg = "Office["+this.getDb()+"]," + "Cannot save or update POFulfillment record. "+result.toString()+"\n"+e;
				logger.error(msg);
				errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
			} catch (Exception e) {
				for(EdisonLog log : list) this.saveNewEdisonLog(log, true, e);
				String msg = "Office["+this.getDb()+"]," + "Cannot save or update POFulfillment record. "+result.toString()+"\n"+e;
				logger.error(msg);
				errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
			}
			if(!StringUtil.isNullOrBlank(errMsg)) {
				this.handleSendErrorNotification(new CSFEException(errMsg));
				return false;
			}
			return true;
		}else {
			//logger.info("Cannot find any PEND Edison record for POFulfillment");
			return false;
		}
	}*/
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public boolean savePOFulfillment(String apiUrl, EdisonLog list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null) {
			List<POFulfillment> resultList = this.getEdisonService().findPOFulfillment(this.getDbName(), this.getSysname(), list.getCode(), String.valueOf(list.getSeq()));
			if(resultList != null && !resultList.isEmpty()) {
				logger.info("Find "+resultList.size()+" POFulfillment record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
				JSONObject token= null;
				Gson gson = new Gson();
				if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
				for(POFulfillment result : resultList) {
					result.packupLeg();
					EdisonLog log = new EdisonLog();
					log.setCode(list.getCode());
					log.setSeq(list.getSeq());
					log.setCode2(!StringUtil.isNullOrBlank(result.getBillOfLadingHeader())?result.getBillOfLadingHeader().trim():"");
					log.setCode3(!StringUtil.isNullOrBlank(result.getBookingReferenceNo())?result.getBookingReferenceNo().trim():"");
					log.setCodetype(ConstantsUtil.POFULFILLMENT_TYPE);
					log.setDbname(this.getDbName());
					log.setSysname(this.getSysname());
					try {
						this.doPostJson(apiUrl, token, gson.toJson(result));
						this.saveNewEdisonLog(log, false, null);
					} catch (CSFEException e) {
						this.saveNewEdisonLog(log, true,  e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update POFulfillment record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					} catch (Exception e) {
						this.saveNewEdisonLog(log, true, e);
						String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot save or update POFulfillment record. "+result.toString()+"\n"+e;
						logger.error(msg);
						errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
					}
				}
				//logger.info("There are "+insertCount+" POFulfillment record(s) have been inserted.");
			}else {
				logger.info("Cannot find any POFulfillment record, by DB["+this.getDbName()+"]");
			}
			if(!StringUtil.isNullOrBlank(errMsg)) {
				this.handleSendErrorNotification(new CSFEException(errMsg));
				return false;
			}
			return true;
		}else {
			//logger.info("Cannot find any PEND Edison record for POFulfillment");
			return false;
		}
	}

	public String convertJson(Object obj) {
		 Gson gson = new Gson();
		 return gson.toJson(obj);
	}
	
	public RequestConfig getRequestConfig(int MAX_TIMEOUT) {
	    RequestConfig.Builder configBuilder = RequestConfig.custom();
	    configBuilder.setConnectTimeout(MAX_TIMEOUT);
	    configBuilder.setSocketTimeout(MAX_TIMEOUT);
	    configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
	    configBuilder.setStaleConnectionCheckEnabled(true);
	    return configBuilder.build();
	    
	}
	
	public JSONObject doPostJson(String value, JSONObject token, String json) throws CSFEException, Exception {
		String packupJSON = this.packupJSON(json, false);
        StringEntity stringEntity = new StringEntity(packupJSON,"UTF-8");
        stringEntity.setContentEncoding("UTF-8");
        stringEntity.setContentType("application/json");
		//logger.info(token.get("token_type")+" "+token.get("access_token"));
        if(ConstantsUtil.DEFAULT_YES.equals(this.getApiJsonDataLog())) logger.info(packupJSON);
        
        HttpPost httpPost = new HttpPost(this.getAPIEndpointsUrl(value, "", false));
        long expires = (long)token.get("expires_in");
        httpPost.setConfig(this.getRequestConfig((int)expires));
        httpPost.setEntity(stringEntity);
        
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, token.get("token_type")+" "+token.get("access_token"));
        //httpPost.setHeader("Authorization", token.get("token_type")+" "+token.get("access_token"));
	    httpPost.setHeader("Content-Type", "application/json");
	    
	    String [] entity = this.doPost(httpPost);
	    //if(!entity[0].equals("200")) throw new CSFEException(entity[1]);
	    if(entity[0].equals("200")) {
			JSONParser parser = new JSONParser();
			if(StringUtil.isNullOrBlank(entity[1])) return null;
			return (JSONObject) parser.parse(entity[1]);
		}else {
			String error = "";
			if(!StringUtil.isNullOrBlank(entity[1])) error = entity[1];
			logger.info("API post json error: " + error);
			throw new CSFEException(this.packupErrorJSON(packupJSON, error));
		}
	}
	
	public JSONObject doPutJson(String value, String id, JSONObject token, String json) throws CSFEException, Exception {
		
		String packupJSON = this.packupJSON(json, true);
        StringEntity stringEntity = new StringEntity(packupJSON,"UTF-8");
        stringEntity.setContentEncoding("UTF-8");
        stringEntity.setContentType("application/json");
		//logger.info(token.get("token_type")+" "+token.get("access_token"));
        if(ConstantsUtil.DEFAULT_YES.equals(this.getApiJsonDataLog())) logger.info(packupJSON);
        
        try {
			HttpPut httpPut = new HttpPut(this.getAPIEndpointsUrl(value, URLEncoder.encode(id, "UTF-8"), true));
			long expires = (long)token.get("expires_in");
			httpPut.setConfig(this.getRequestConfig((int)expires));
			httpPut.setEntity(stringEntity);
			
			httpPut.setHeader(HttpHeaders.AUTHORIZATION, token.get("token_type")+" "+token.get("access_token"));
			httpPut.setHeader("Content-Type", "application/json");
			
			String [] entity =  this.doPut(httpPut);
			//if(!entity[0].equals("200")) throw new CSFEException(entity[1]);
			if(entity[0].equals("200")) {
				JSONParser parser = new JSONParser();
				if(StringUtil.isNullOrBlank(entity[1])) return null;
				return (JSONObject) parser.parse(entity[1]);
			}else {
				String error = "";
				if(!StringUtil.isNullOrBlank(entity[1])) error = entity[1];
				logger.info("API post json error: " + error);
				throw new CSFEException(this.packupErrorJSON(packupJSON, error));
			}
        } catch (UnsupportedEncodingException e) {
			throw new CSFEException(e);
		}
	}
	
	public void doDeleteJson(String value, JSONObject token, Object obj, long id) throws CSFEException, Exception {

        HttpDelete httpDelete = new HttpDelete(this.getAPIEndpointsDeleteUrl(value, obj, id));
        long expires = (long)token.get("expires_in");
        httpDelete.setConfig(this.getRequestConfig((int)expires));
        //httpDelete.setEntity(stringEntity);
        
        httpDelete.setHeader(HttpHeaders.AUTHORIZATION, token.get("token_type")+" "+token.get("access_token"));
        httpDelete.setHeader("Content-Type", "application/json");
	    
	    String [] entity = this.doDelete(httpDelete);
	    if(!entity[0].equals("200")) throw new CSFEException(entity[1]);
	}
		 
	public JSONObject doPostToken(String url) throws CSFEException, IOException {
		try {
			url+="/connect/token";
			List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("grant_type", this.getGrantType()));
	        params.add(new BasicNameValuePair("client_id", this.getClientid()));
	        params.add(new BasicNameValuePair("client_secret", this.getClientSecret()));
	        HttpPost httpPost = new HttpPost(url);
	        httpPost.setEntity(new UrlEncodedFormEntity(params));
			String [] entity = this.doPost(httpPost);
			if(entity[0].equals("200")) {
				JSONParser parser = new JSONParser();
				return (JSONObject) parser.parse(entity[1]);
			}else {
				throw new CSFEException(entity[1]);
			}
		} catch (org.json.simple.parser.ParseException e) {
			throw new CSFEException(e);
		}
	}
	
	public String[] doPost(HttpPost httpPost) throws CSFEException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String[] httpStr = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = this.createSSLClientDefault();
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = new String[] {String.valueOf(response.getStatusLine().getStatusCode()),EntityUtils.toString(entity, "UTF-8")};
        } catch (IOException e) {
            logger.error(e);
            throw new CSFEException(e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                	logger.error(e);
                	throw new CSFEException(e);
                }
            }
        }
        return httpStr;
    }
	
	public String[] doPut(HttpPut httpPut) throws CSFEException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String[] httpStr = null;
        CloseableHttpResponse response = null;
        try {
            //httpClient = this.createSSLClientDefault();
            response = httpClient.execute(httpPut);
            HttpEntity entity = response.getEntity();
            httpStr = new String[] {String.valueOf(response.getStatusLine().getStatusCode()),EntityUtils.toString(entity, "UTF-8")};
        } catch (IOException e) {
            logger.error(e);
            throw new CSFEException(e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                	logger.error(e);
                	throw new CSFEException(e);
                }
            }
        }
        return httpStr;
    }
	
	public String[] doDelete(HttpDelete httpDelete) throws CSFEException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String[] httpStr = null;
        CloseableHttpResponse response = null;
        try {
            //httpClient = this.createSSLClientDefault();
            response = httpClient.execute(httpDelete);
            HttpEntity entity = response.getEntity();
            httpStr = new String[] {String.valueOf(response.getStatusLine().getStatusCode()),EntityUtils.toString(entity, "UTF-8")};
        } catch (IOException e) {
            logger.error(e);
            throw new CSFEException(e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                	logger.error(e);
                	throw new CSFEException(e);
                }
            }
        }
        return httpStr;
    }
	
	public String getAPIEndpointsUrl(String value, String id, boolean isUpdate) {
		//Save Url "/api/activities?profile={profileName}";
		//Update Url = "/api/activities/{id}?profile={profileName}";
		return this.getApiEdUrl()+value+(isUpdate?("/"+id):"");
	}
	
	public String getAPIEndpointsDeleteUrl(String value, Object obj, long id) {
		//Save Url "/api/activities?profile={profileName}";
		//Update Url = "/api/activities/{id}?profile={profileName}";
		String input="";
		if(obj instanceof BillOfLadingConsignments)
			input = "?billOfLadingId="+((BillOfLadingConsignments)obj).getBillOfLadingId()+"&consignmentId="+((BillOfLadingConsignments)obj).getConsignmentId();
		else if(obj instanceof BillOfLadingShipmentLoads)
			input = "?billOfLadingId="+((BillOfLadingShipmentLoads)obj).getBillOfLadingId()+"&shipmentLoadId="+((BillOfLadingShipmentLoads)obj).getShipmentLoadId();
		else if(obj instanceof ShipmentBillOfLadings)
			input = "?billOfLadingId="+((ShipmentBillOfLadings)obj).getBillOfLadingId()+"&shipmentId="+((ShipmentBillOfLadings)obj).getShipmentId();
		else if(obj instanceof ConsignmentItineraries)
			input = "?itineraryId="+((ConsignmentItineraries)obj).getItineraryId()+"&consignmentId="+((ConsignmentItineraries)obj).getConsignmentId();
		else if(obj instanceof ContainerItineraries)
			input = "?itineraryId="+((ContainerItineraries)obj).getItineraryId()+"&containerId="+((ContainerItineraries)obj).getContainerId();
		else if(obj instanceof BillOfLadingItineraries)
			input = "?itineraryId="+((BillOfLadingItineraries)obj).getItineraryId()+"&billOfLadingId="+((BillOfLadingItineraries)obj).getBillOfLadingId();
		else if(obj instanceof MasterBillItineraries)
			input = "?itineraryId="+((MasterBillItineraries)obj).getItineraryId()+"&masterBillOfLadingId="+((MasterBillItineraries)obj).getMasterBillOfLadingId();
		else 
			input = "/"+id;
		//logger.info(this.getApiEdUrl()+"/api"+value+input);
		return this.getApiEdUrl()+value+input;
	}
	
	public List<String> getEdisonID(List<EdisonLog> list){
		List<String> idList = new ArrayList<String>();
		if(list != null && !list.isEmpty()) {
			for(EdisonLog log : list) {
				idList.add(String.valueOf(log.getId()));
			}
		}
		return idList;
	}
	
	public EdisonLog findEdisonLog(long id, List<EdisonLog> list) {
		if(list != null && !list.isEmpty()) {
			for(EdisonLog log : list) {
				if(String.valueOf(log.getId()).equals(String.valueOf(id))) 
					return log;
			}
		}
		return null;
	}
	
	public boolean hasTestID(String id) {
		if(!StringUtil.isNullOrBlank(this.getTestID())) {
			String [] list = this.getTestID().split(",");
			for(int i = 0;i<list.length;i++) {
				if(id.equals(list[i])) return true;
			}
			return false;
		}
		return true;
	}
	
	public List<EdisonLog> getEdisonIDByProcessTimes(int processTimes, List<EdisonLog> list){
		List<EdisonLog> resList = new ArrayList<EdisonLog>();
		if(list != null && !list.isEmpty()) {
			if(this.getProcessCount() > 0 && list.size() > this.getProcessCount()) {
				int startpoint = processTimes * this.getProcessCount();
				if(list.size() > startpoint) {
					int endpoint = ((processTimes+1) * this.getProcessCount());
					if(endpoint > list.size()) endpoint = list.size();
					logger.info("Find "+(startpoint+1)+" To "+endpoint+" EdisonLog records.");
					for(int point = startpoint; point < endpoint;point++) {
						resList.add(list.get(point));
					}
				}else {
					return null;
				}
			} else {
				logger.info("Find all "+list.size()+" EdisonLog records.");
				return list;
			}
		}
		return resList;
	}
	
	public String packupJSON(String json, boolean isUpdate) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(json);
		
		jsonObject.replace("IsFCL", ("1").equals(jsonObject.get("IsFCL")));
		jsonObject.replace("TriangleTradeFlag", ("1").equals(jsonObject.get("TriangleTradeFlag")));
		jsonObject.replace("MemoBOLFlag", ("1").equals(jsonObject.get("MemoBOLFlag")));
		jsonObject.replace("Resolved", ("1").equals(jsonObject.get("Resolved")));
		JSONArray list = (JSONArray)jsonObject.get("Contacts");
		if(list != null && !list.isEmpty()) {
			for(int i = 0 ; i < list.size() ; i++)	((JSONObject)list.get(i)).remove("id");
		}
		if(isUpdate) {
			jsonObject.remove("createdDate");
			jsonObject.remove("createdBy");
			jsonObject.remove("CreatedDate");
			jsonObject.remove("CreatedBy");
		}else {
			jsonObject.remove("Id");
			jsonObject.remove("id");
		}
		
		//logger.debug(jsonObject.toJSONString());
		return jsonObject.toJSONString();
	}
	
	protected void handleSendErrorNotification(Exception e) {
		((WarningNotification)this.getWarningNotification()).setException(e);
		this.getWarningNotification().saveDeliveryReq();
	}
 
	public CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			HostnameVerifier hostnameVerifier = NoopHostnameVerifier.INSTANCE;
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
 
	}

	public int getProcessTimes(List<EdisonLog> list) {
		int count = 1;
		if(this.getProcessCount() > 0 && list != null && !list.isEmpty()) count = new BigDecimal(Double.valueOf(list.size())/Double.valueOf(this.getProcessCount())).setScale(0, RoundingMode.UP).intValue();
		return (this.getProcessTimes() > 0 && count > this.getProcessTimes())?this.getProcessTimes():count;
	}
	
	public void packupPurchaseOrders(PurchaseOrders purchaseOrder, List<Contacts> contactsList) {
		long id = purchaseOrder.getId();
		for(Contacts contact: contactsList) {
			if(id == contact.getId())	purchaseOrder.addContacts(contact);
		}
	}
	
	public void packupCruiseOrders(CruiseOrders cruiseOrders, List<CruiseOrderContacts> contactsList) {
		long id = cruiseOrders.getId();
		for(CruiseOrderContacts contact: contactsList) {
			if(id == contact.getId()) {
				cruiseOrders.addCruiseOrderContacts(contact);
			}
		}
	}
	
	public String packupErrorJSON(String json, String error) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(json);
		JSONObject jsonErrorObject = (JSONObject) parser.parse(error);
		jsonObject.put("errors", jsonErrorObject.get("errors"));
		return jsonObject.toJSONString();
	}
	
	public void saveEdisonLog(EdisonLog log, String lastupdate) throws CSFEException{
		Date date;
		try {
			date = new SimpleDateFormat(ConstantsUtil.DATE_SHIPMENT_VENDOR_FORMAT).parse(lastupdate);
		} catch (ParseException e) {
			try {
				date = new SimpleDateFormat(ConstantsUtil.DATE_SHIPMENT_VENDOR_FORMAT_WITHOUT_MILLISEC).parse(lastupdate);
			} catch (ParseException ex) {
				throw new CSFEException(ex+" Date : "+lastupdate);
			}
		}
		log.setAzureMsg(null);
		log.setStatus(ConstantsUtil.DONE_KEY);
		log.setIsETLtoAzure(true);
		log.setAzureLastupdate(date);
		this.getEdisonService().saveEdisonLog(log, true);
	}
	
	public void saveNewEdisonLog(EdisonLog log, boolean failure, Exception ex) throws CSFEException{
		try {
			String azureMsg = "";
			if(failure) {
			if(ex!= null && ex.getCause() != null)	azureMsg = ex.getCause().getMessage();
				else azureMsg = ex.getMessage();
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(azureMsg);
			azureMsg = jsonObject.get("errors").toString();
			}
			if(!StringUtil.isNullOrBlank(azureMsg)) {
				logger.info("Failure azure Msg["+azureMsg+"]");
				log.setAzureMsg(azureMsg);
				log.setIsETLtoAzure(false);
			}else {
				log.setIsETLtoAzure(true);
			}
			log.setStatus(ConstantsUtil.DONE_KEY);
			log.setAzureLastupdate(new Date());
			this.getEdisonService().saveEdisonLog(log, false);
		} catch (Exception e) {
			throw new CSFEException(e);
		}
	}
	
	public void saveDeleteEdisonLog(EdisonLog log) throws CSFEException{
		log.setAzureMsg(null);
		log.setStatus(ConstantsUtil.DELETED_KEY);
		log.setIsETLtoAzure(false);
		log.setAzureLastupdate(new Date());
		this.getEdisonService().saveEdisonLog(log, true);
	}
	
	public void saveFailureEdisonLog(EdisonLog log, String lastupdate, Exception ex) throws CSFEException{
		Date date;
		String azureMsg = "";
		try {
			if(!StringUtil.isNullOrBlank(lastupdate)) 
				date = new SimpleDateFormat(ConstantsUtil.DATE_SHIPMENT_VENDOR_FORMAT).parse(lastupdate);
			else
				date = new Date();	
			if(ex.getCause() != null)	azureMsg = ex.getCause().getMessage();
			else azureMsg = ex.getMessage();
			
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(azureMsg);
			azureMsg = jsonObject.get("errors").toString();
			logger.info("Failure azure Msg["+azureMsg+"]");
		} catch (ParseException e) {
			try {
				date = new SimpleDateFormat(ConstantsUtil.DATE_SHIPMENT_VENDOR_FORMAT_WITHOUT_MILLISEC).parse(lastupdate);
			} catch (ParseException es) {
				throw new CSFEException(es+" Date : "+lastupdate);
			}
		} catch (Exception e) {
			throw new CSFEException(e);
		}
		log.setAzureMsg(azureMsg);
		if(!StringUtil.isNullOrBlank(exMsg) && !StringUtil.isNullOrBlank(azureMsg)) {
			for(String x : exMsg.split(";")) {
				if(azureMsg.contains(x) && log.IsETLtoAzure())	{
					log.setStatus(ConstantsUtil.DONE_KEY);
					log.setAzureLastupdate(date);
					break;
				}
			}
		}
		this.getEdisonService().saveEdisonLog(log, true);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
	public void deleteAPI (String objectName, String apiUrl, List<EdisonLog> list, String creator) throws CSFEException, ParseException, IOException{
		String errMsg = "";
		if(list != null && !list.isEmpty()) {
			logger.info("Find "+list.size()+" "+objectName+" delete record(s) by DB["+this.getDbName()+"], System Name["+this.getSysname()+"]");
			int deleteCount = 0;
			JSONObject token= null;
			if(!StringUtil.isNullOrBlank(apiUrl)) token = this.doPostToken(this.getApiUrl());
			for(EdisonLog log : list) {
				try {
					if(!StringUtil.isNullOrBlank(apiUrl))
						this.doDeleteJson(apiUrl, token, log, log.getAzureId());
					deleteCount++;
					this.saveDeleteEdisonLog(log);
				} catch (CSFEException e) {
					this.saveFailureEdisonLog(log, null, e);
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot delete "+objectName+" record(s). AzureID["+log.getAzureId()+"]\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				} catch (Exception e) {
					this.saveFailureEdisonLog(log, null, e);
					String msg = "Office["+this.getDb()+"], System Name["+this.getSysname()+"]" + "Cannot delete "+objectName+" record(s). AzureID["+log.getAzureId()+"]\n"+e;
					logger.error(msg);
					errMsg += msg+"\n-----------------------------------------------------------------------------------\n";
				}
			}
			logger.info("There are "+deleteCount+" "+objectName+" record(s) have been deleted.");
		} 
		if(!StringUtil.isNullOrBlank(errMsg)) this.handleSendErrorNotification(new CSFEException(errMsg));
	}
}
