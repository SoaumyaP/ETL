package com.csfe.trs.business.service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

import com.csfe.common.CSFEException;
import com.csfe.common.ConstantsUtil;
import com.csfe.common.EmailNotification;
import com.csfe.common.WarningNotification;
import com.csfe.common.util.StringUtil;
import com.csfe.trs.business.dao.booking.POFulfillmentDAO;
import com.csfe.trs.business.entity.EDIProcessLog;
import com.csfe.trs.business.entity.POFulfillment;
import com.csfe.trs.business.entity.Vesvoyforwarder;
import com.google.gson.Gson;

public class TRSServiceImpl implements TRSService{
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Value("${DAY.cutOff:0}")
	private int cutOffday;
	
	@Value("${API.url:}")
	private String url;
	
	@Value("${API.Endpoints.Url:}")
	private String apiEdUrl;
	
	@Value("${API.Identity.Url:}")
	private String apiUrl;
	
	@Value("${Grant.Type:}")
	private String grantType;
	
	@Value("${Client.Id:}")
	private String clientid;
	
	@Value("${Client.Secret:}")
	private String clientSecret;
	
	@Value("${TRS.ProjectID}")
	private String projectID;
	
	@Autowired(required = false)
	private ApplicationContext appContext;
	
	private EDIService ediService;
	
	private EdisonService edisonService;
	
	@Autowired(required = false)
	private POFulfillmentDAO poFulfillmentDAO;
	
	@Autowired
	@Qualifier("WarningNotification")
	private EmailNotification warningNotification;
	
	public ApplicationContext getAppContext() {
		return appContext;
	}

	public void setAppContext(ApplicationContext appContext) {
		this.appContext = appContext;
	}
	
	public int getCutOffday() {
		return cutOffday;
	}

	public String getUrl() {
		return url;
	}

	public String getApiEdUrl() {
		return apiEdUrl;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public String getGrantType() {
		return grantType;
	}

	public String getClientid() {
		return clientid;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public String getProjectID() {
		return projectID;
	}

	public POFulfillmentDAO getPoFulfillmentDAO() {
		return poFulfillmentDAO;
	}

	public void setPoFulfillmentDAO(POFulfillmentDAO poFulfillmentDAO) {
		this.poFulfillmentDAO = poFulfillmentDAO;
	}

	public EmailNotification getWarningNotification() {
		return warningNotification;
	}

	public EDIService getEdiService() {
		return ediService;
	}

	public void setEdiService(EDIService ediService) {
		this.ediService = ediService;
	}
	
	public EdisonService getEdisonService() {
		return edisonService;
	}

	public void setEdisonService(EdisonService edisonService) {
		this.edisonService = edisonService;
	}
	
	public void setDefaultEDIService() throws CSFEException{
		this.setEdiService(this.getAppContext().getBean("edi" + ConstantsUtil.DEFAULT_SERVICE, EDIService.class));
		this.setEdisonService(this.getAppContext().getBean("edison" + ConstantsUtil.DEFAULT_SERVICE, EdisonService.class));
	}

	public void processTRSConfirmation(String creator) throws CSFEException{
		try {
			this.setDefaultEDIService();
			SimpleDateFormat sdf = new SimpleDateFormat(ConstantsUtil.SIMPLE_DATE);
			Calendar date = Calendar.getInstance(TimeZone.getDefault());
			date.add(Calendar.DATE,-cutOffday);
			logger.info("THe BookingEDIRequest cut off day is ["+sdf.format(date.getTime())+"]");
			List<POFulfillment> list = this.getPoFulfillmentDAO().findCNFMBooking(this.getProjectID(), sdf.format(date.getTime()));
			if(list != null && !list.isEmpty()) {
				logger.info("Find "+list.size()+" confirm TRS Booking.");
				int count = 1;
				HashMap<String,POFulfillment> bknoList = new HashMap<String,POFulfillment>();
				for(POFulfillment value : list) bknoList.put(value.getBookingReferenceNo().trim(),value);
				List<EDIProcessLog> logList = this.getEdiService().findEDIProcessLogByBKNO(bknoList.keySet());
				if(logList != null && !logList.isEmpty()) 
					for(EDIProcessLog value : logList) 	bknoList.remove(value.getRefID().trim());
				
				for(POFulfillment value : list) {
					try {
						logger.info("Processing ["+count+"] of ["+list.size()+"] confirm Booking Reference No["+value.getBookingReferenceNo().trim()+"].");
						if(bknoList.containsKey(value.getBookingReferenceNo().trim())) {
							String sysid = value.getSysid();
							logger.info("SYSID ["+sysid+"], SONO ["+value.getSONumber()+"].");
							Vesvoyforwarder vf = this.getEdisonService().findVesvoyforwarderBySONO(sysid, value.getSONumber());
							if(vf != null && !StringUtil.isNullOrBlank(vf.getCarrierNo()))value.setCarrierCode(vf.getCarrierNo().trim());
							if(vf != null && !StringUtil.isNullOrBlank(vf.getVoyage()))value.setVesselFlight(vf.getVoyage().trim());
							value.packupLeg();
							logger.info(value.toString());
							String msg = this.processConfirmationAPI(this.getUrl(), value);
							if(StringUtil.isNullOrBlank(msg))	this.getEdiService().saveEDIProcessLog(value, creator);
							else	logger.error("Failure to process booking["+value.getBookingReferenceNo().trim()+"] Error msg:"+msg);
						}else {
							logger.info("Skip process. This booking has been processed before.");
						}
						count++;
					} catch (CSFEException e) {
						logger.error(e);
					} catch (Exception e) {
						logger.error(e);
					}
				}
			}
		}catch (CSFEException e) {
			logger.error(e);
			this.handleSendErrorNotification(e);
		}catch (Exception e) {
			logger.error(e);
			this.handleSendErrorNotification(e);
		}
	}
	
	public String processConfirmationAPI(String url, POFulfillment value)throws CSFEException, IOException {
		String errMsg = "";
		JSONObject token= null;
		Gson gson = new Gson();
		if(!StringUtil.isNullOrBlank(this.getApiUrl())) token = this.doPostToken(this.getApiUrl());
		try {
			this.doPostJson(this.getUrl(), token, gson.toJson(value));
		} catch (CSFEException e) {
			String msg = "Cannot process POFulfillment record(s). "+value.toString()+"\n"+e;
			errMsg += msg;
		} catch (Exception e) {
			String msg = "Cannot process POFulfillment record(s). "+value.toString()+"\n"+e;
			errMsg += msg;
		}
		return errMsg;
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
	
	public JSONObject doPostJson(String value, JSONObject token, String json) throws CSFEException, Exception {
		
		logger.info(json);
        StringEntity stringEntity = new StringEntity(json,"UTF-8");
        stringEntity.setContentEncoding("UTF-8");
        stringEntity.setContentType("application/json");
		logger.info(token.get("token_type")+" "+token.get("access_token"));
        logger.info(this.getAPIEndpointsUrl(value, 0, false));
        HttpPost httpPost = new HttpPost(this.getAPIEndpointsUrl(value, 0, false));
        long expires = (long)token.get("expires_in");
        httpPost.setConfig(this.getRequestConfig((int)expires));
        httpPost.setEntity(stringEntity);
        
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, token.get("token_type")+" "+token.get("access_token"));
        //httpPost.setHeader("Authorization", token.get("token_type")+" "+token.get("access_token"));
	    httpPost.setHeader("Content-Type", "application/json");
	    
	    String [] entity = this.doPost(httpPost);
	    logger.info(entity[0]+"["+(entity[0].equals("200"))+"]");
	    //if(!entity[0].equals("200")) throw new CSFEException(entity[1]);
	    if(entity[0].equals("200")) {
	    	if(!StringUtil.isNullOrBlank(entity[1])) {
				JSONParser parser = new JSONParser();
				return (JSONObject) parser.parse(entity[1]);
	    	}else{
	    		return null;
	    	}
		}else {
			throw new CSFEException(entity[1]);
		}
	}
	
	public String getAPIEndpointsUrl(String value, long id, boolean isUpdate) {
		//Save Url "/api/activities?profile={profileName}";
		//Update Url = "/api/activities/{id}?profile={profileName}";
		return this.getApiEdUrl()+value+(isUpdate?("/"+String.valueOf(id)):"");
	}
	
	public String packupJSON(String json, boolean isUpdate) throws Exception {
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = (JSONObject) parser.parse(json);

		jsonObject.replace("IsFCL", ("1").equals(jsonObject.get("IsFCL")));
		jsonObject.replace("TriangleTradeFlag", ("1").equals(jsonObject.get("TriangleTradeFlag")));
		jsonObject.replace("MemoBOLFlag", ("1").equals(jsonObject.get("MemoBOLFlag")));
		jsonObject.replace("Resolved", ("1").equals(jsonObject.get("Resolved")));
		((JSONObject)jsonObject.get("Contacts")).remove("id");
		if(isUpdate) {
			jsonObject.remove("createdDate");
			jsonObject.remove("createdBy");
			jsonObject.remove("CreatedDate");
			jsonObject.remove("CreatedBy");
		}else {
			jsonObject.remove("Id");
			jsonObject.remove("id");
		}
		
		//logger.info(jsonObject.toJSONString());
		return jsonObject.toJSONString();
	}
	
	public RequestConfig getRequestConfig(int MAX_TIMEOUT) {
	    RequestConfig.Builder configBuilder = RequestConfig.custom();
	    configBuilder.setConnectTimeout(MAX_TIMEOUT);
	    configBuilder.setSocketTimeout(MAX_TIMEOUT);
	    configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
	    configBuilder.setStaleConnectionCheckEnabled(true);
	    return configBuilder.build();
	    
	}
	
	protected void handleSendErrorNotification(Exception e) {
		((WarningNotification)this.getWarningNotification()).setException(e);
		this.getWarningNotification().saveDeliveryReq();
	}
}
