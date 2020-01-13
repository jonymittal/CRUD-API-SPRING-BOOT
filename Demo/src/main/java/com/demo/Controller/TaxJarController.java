package com.demo.Controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.demo.ServiceImpl.TaxJarService;

import com.taxjar.model.rates.RateResponse;

@Controller
@RequestMapping("/v1/api/")
public class TaxJarController {

	@Autowired
	TaxJarService taxJarService;
	
	@RequestMapping(value = "calculateSaleTaxForLocation", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> taxRateForLocation(@RequestBody String jsonStr) {
		Map<String, Object> response = new HashMap<String, Object>();
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			RateResponse res = taxJarService.saleTaxForLocation(jsonObject.getString("country"), jsonObject.getString("city"), jsonObject.getString("street"), jsonObject.getString("zipCode"));	
			Float stateRate = res.rate.getStateRate()* 100;
			Float countryRate = res.rate.getCountyRate()* 100;
			Float cityRate = res.rate.getCityRate()* 100;
			Float districRate = res.rate.getCombinedDistrictRate()* 100;
			Float totalSaleTax = stateRate+countryRate+cityRate+districRate;
			response.put("data", res);
			response.put("totalSaleTax", totalSaleTax+"%");
			response.put("message", "Success");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			response.put("message", "Something went wrong");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
