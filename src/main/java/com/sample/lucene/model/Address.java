package com.sample.lucene.model;

import java.io.Serializable;

public class Address implements Serializable{
	
	private static final long serialVersionUID = -32226280946827701L;

	public Address(){}
	
	public Address(long addrId, String addrLineOne, String addrLineTwo,
			String city, String state, String country) {
		this.addrId = addrId;
		this.addrLineOne = addrLineOne;
		this.addrLineTwo = addrLineTwo;
		this.city = city;
		this.state = state;
		this.country = country;
	}

	private long addrId;
	
	private String addrLineOne;
	
	private String addrLineTwo;
	
	private String city;
	
	private String state;
	
	private String country;

	public long getAddrId() {
		return addrId;
	}

	public void setAddrId(long addrId) {
		this.addrId = addrId;
	}

	public String getAddrLineOne() {
		return addrLineOne;
	}

	public void setAddrLineOne(String addrLineOne) {
		this.addrLineOne = addrLineOne;
	}

	public String getAddrLineTwo() {
		return addrLineTwo;
	}

	public void setAddrLineTwo(String addrLineTwo) {
		this.addrLineTwo = addrLineTwo;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		Address person = (Address) obj;
		
		return addrId == person.addrId && 
			  (addrLineOne == person.addrLineOne || (addrLineOne != null && addrLineOne .equals(person.getAddrLineOne()))) && 
			  (addrLineTwo == person.addrLineTwo || (addrLineTwo != null && addrLineTwo.equals(person.getAddrLineTwo()))) &&
			  (city == person.city || (city != null && city.equals(person.getCity()))) &&
			  (state == person.state || (state != null && state.equals(person.getState()))) &&
			  (country == person.country || (country != null && country.equals(person.getCountry())));
	}
	
	@Override
	public int hashCode() {
		final int prime = 30;
		int result = 1;
		result = prime * result + Long.valueOf(addrId).hashCode();
		result = prime * result + ((addrLineOne == null) ? 0 : addrLineOne.hashCode());
		result = prime * result + ((addrLineTwo == null) ? 0 : addrLineTwo.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		return result;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(addrId).append(" ");
		builder.append(addrLineOne).append(" ");
		builder.append(addrLineTwo).append(" ");
		builder.append(city).append(" ");
		builder.append(state).append(" ");
		builder.append(country);
		return builder.toString();
	}
}
