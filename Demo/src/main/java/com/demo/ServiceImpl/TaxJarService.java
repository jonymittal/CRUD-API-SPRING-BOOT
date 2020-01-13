package com.demo.ServiceImpl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.taxjar.Taxjar;
import com.taxjar.model.rates.RateResponse;

@Service
public class TaxJarService {

	String liveToken = "b129952219a0e077169d3d791edb2f9e";
	
	public RateResponse saleTaxForLocation(String country, String city, String street, String zipCode) {
		Taxjar client = new Taxjar(liveToken);
		try {
			Map<String, String> params = new HashMap<>();
            params.put("country", country);
            params.put("city", city);
            params.put("street", street);
            RateResponse res = client.ratesForLocation(zipCode, params);
            return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
