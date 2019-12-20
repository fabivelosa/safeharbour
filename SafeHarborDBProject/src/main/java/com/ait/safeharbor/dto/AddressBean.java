package com.ait.safeharbor.dto;

public class AddressBean {
	private int addressID;
	private String fulladdress;
	private String address;
	private String city;
	private String county;
	
	/**
	 * @return the addressID
	 */
	public int getAddressID() {
		return addressID;
	}
	/**
	 * @param addressID the addressID to set
	 */
	public void setAddressID(int  addressID) {
		this.addressID = addressID;
	}
	/**
	 * @return the fulladdress
	 */
	public String getFulladdress() {
		return fulladdress;
	}
	/**
	 * @param fulladdress the fulladdress to set
	 */
	public void setFulladdress(String fulladdress) {
		this.fulladdress = fulladdress;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * @return the county
	 */
	public String getCounty() {
		return county;
	}
	/**
	 * @param county the county to set
	 */
	public void setCounty(String county) {
		this.county = county;
	}
	
	
}
