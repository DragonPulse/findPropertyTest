package org.test.takehome.findhome.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.test.takehome.findhome.model.HomeDetails;

@Service
public class HomeDetailsServiceImpl implements HomeDetailsService{

	@Autowired
	RestTemplate restTemplate;

	@Value("${URL.SPOOKLE.PROPERTY.DETAILS}")
	String spookleURL;

	@Value("${URL.HEROKUAPP.PROPERTY.DETAILS}")
	String herokuappURL;

	@Override
	public List<HomeDetails> getHighestValueHome(List<String> homeIds) {
		
		List<HomeDetails> homeDetails = new ArrayList<>();
		for (String homeId:homeIds) {
			HomeDetails homeDetail = callExternalService(homeId);
			if(homeDetail!=null) {
				homeDetails.add(homeDetail);
			}
		}
		Collections.sort(homeDetails, new Comparator<HomeDetails>() {
			@Override
			public int compare(HomeDetails o1, HomeDetails o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});

		return homeDetails;
	}

	private HomeDetails callExternalService(String homeId) {
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromUriString(spookleURL)
				.queryParam("property_id", homeId);
		try {
			HomeDetails homeDetailResponse = restTemplate.getForObject(builder.toUriString(), HomeDetails.class);
			System.out.println("HOme ID::" + homeDetailResponse.getHomeId() + "Home Value :: " + homeDetailResponse.getValue());
			return homeDetailResponse;
		}catch(Exception e){
			//can be handled better
			System.out.println("Error Occured for Home ID :: "+ homeId +"Error :: "+e.getLocalizedMessage());
			return null;
		}
	}

}
