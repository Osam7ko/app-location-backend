package com.bk.map.main.project.bkProject.dto;

public class LocationsDTO {
	private Long id;

    private String name;
    private String address;

    private double latitude;
    private double longitude;
    private Long userId;
	
 
     // Constructors, getters, setters
    
    public LocationsDTO() {
		super();
		
	}
    
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
   
    
}
