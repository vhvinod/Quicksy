package com.appthon.rest;

import java.io.IOException;

import javax.annotation.Resource;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.appthon.common.UtilityClass;
import com.google.gson.Gson;

@Scope("prototype") // Spring prototype.
@Path("appthon") // Unique path to identify this class.
@Component
public class AppthonCommonRestService {
	@Resource(name = "commonUtils")
	private UtilityClass commonUtils;
	
	@Path("/balanceenquiry")
	@POST
	public String balanceEnquiry() throws JSONException, IOException {
		return new JSONObject()
				.put("balanceSummary",
					new Gson().toJson(commonUtils
						.connectToURL(
							"http://retailbanking.mybluemix.net/banking/icicibank/balanceenquiry?client_id=vh.vinod@gmail.com"
							+ "&token=b4b8f7e8eaa4&accountno=9999888877770001"))
						).toString();
	}
	
	@Path("/branchLocator")
	@POST
	public String branchLocator() throws JSONException, IOException {
		return new JSONObject()
				.put("branchLocator",
					new Gson().toJson(commonUtils
						.connectToURL(
							"http://retailbanking.mybluemix.net/banking/icicibank/BranchAtmLocator?client_id=vh.vinod@gmail.com"
							+"&token=b4b8f7e8eaa4&locate=ATM&lat=72.9376984&long=19.1445007"))
						).toString();
	}
	
	@Path("/transferFunds")
	@POST
	public String transferFunds(@FormParam("destAccNo") final String destAccNo, 
			@FormParam("amount") final String amount,
			 @FormParam("payeeDesc") final String payeeDesc) throws JSONException, IOException {
		return new JSONObject()
				.put("branchLocator",
					new Gson().toJson(commonUtils
						.connectToURL(
							"http://retailbanking.mybluemix.net/banking/icicibank/fundTransfer?client_id=vh.vinod@gmail.com&token=b4b8f7e8eaa4&srcAccount=9999888877770001"
							+"&destAccount="+destAccNo+"&amt="+amount+"&payeeDesc="+payeeDesc+"&payeeId=1&type_of_transaction=DTH"))
						).toString();
	}
	//"http://retailbanking.mybluemix.net/banking/icicibank/fundTransfer?client_id=vh.vinod@gmail.com&token=b4b8f7e8eaa4&srcAccount=9999888877770001&destAccount=9999888877770001&amt=1000&payeeDesc=payeedesc&payeeId=1&type_of_transaction=DTH"
}