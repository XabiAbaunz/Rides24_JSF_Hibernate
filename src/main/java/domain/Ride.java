package domain;

import java.io.*;
import java.util.Date;
import java.util.Objects;

import javax.persistence.*;


@SuppressWarnings("serial")
@Entity
public class Ride implements Serializable {
	@Id 
	@GeneratedValue
	private int rideNumber;
	private String departCity;
	private String arrivalCity;
	private int nPlaces;
	private Date data;
	private float price;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Driver driver;  
	
	public Ride(){
		super();
	}
	
	public Ride(int rideNumber, String from, String to, Date date, int nPlaces, float price, Driver driver) {
		super();
		this.rideNumber = rideNumber;
		this.departCity = from;
		this.arrivalCity = to;
		this.nPlaces = nPlaces;
		this.data=date;
		this.price=price;
		this.driver = driver;
	}

	

	public Ride(String from, String to,  Date date, int nPlaces, float price, Driver driver) {
		super();
		this.departCity = from;
		this.arrivalCity = to;
		this.nPlaces = nPlaces;
		this.data=date;
		this.price=price;
		this.driver = driver;
	}
	
	/**
	 * Get the  number of the ride
	 * 
	 * @return the ride number
	 */
	public int getRideNumber() {
		return rideNumber;
	}

	
	/**
	 * Set the ride number to a ride
	 * 
	 * @param ride Number to be set	 */
	
	public void setRideNumber(int rideNumber) {
		this.rideNumber = rideNumber;
	}

	/**
	 * Get the free places of the ride
	 * 
	 * @return the available places
	 */
	
	public float getnPlaces() {
		return nPlaces;
	}

	public String getDepartCity() {
		return departCity;
	}

	public void setDepartCity(String departCity) {
		this.departCity = departCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setnPlaces(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	/**
	 * Set the free places of the ride
	 * 
	 * @param  nPlaces places to be set
	 */

	public void setBetMinimum(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	/**
	 * Get the driver associated to the ride
	 * 
	 * @return the associated driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * Set the driver associated to the ride
	 * 
	 * @param driver to associate to the ride
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		return Objects.hash(arrivalCity, data, departCity, driver, nPlaces, price, rideNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ride other = (Ride) obj;
		return rideNumber == other.rideNumber;
	}

	public String toString(){
		return rideNumber+";"+";"+departCity+";"+arrivalCity+";"+data;  
	}




	
}
