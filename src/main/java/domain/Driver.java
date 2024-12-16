package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.Set;

import javax.persistence.*;

@Entity
@DiscriminatorValue("DRIVER")
public class Driver extends User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.REMOVE, mappedBy="driver")
	private List<Ride> rides;

	public Driver(String username, String email, String passwd) {
		super(username, email, passwd, "Driver");
		this.rides = new ArrayList<Ride>();
	}
	
	public Driver() {
	    super();
	    this.rides = new ArrayList<>();
	}
	
	public String toString(){
		return super.toString();
	}
	
	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Ride addRide(String from, String to, Date date, int nPlaces, float price)  {
        Ride ride=new Ride(from,to,date,nPlaces,price, this);
        rides.add(ride);
        return ride;
	}

	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(String from, String to, Date date)  {	
		for (Ride r:rides)
			if ( (java.util.Objects.equals(r.getDepartCity(),from)) && (java.util.Objects.equals(r.getArrivalCity(),to)) && (java.util.Objects.equals(r.getData(),date)) )
			 return true;
		
		return false;
	}
		
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Driver other = (Driver) obj;
		if (getEmail() != other.getEmail())
			return false;
		return true;
	}

	public Ride removeRide(String from, String to, Date date) {
		boolean found=false;
		int index=0;
		Ride r=null;
		while (!found && index<=rides.size()) {
			r=rides.get(++index);
			if ( (java.util.Objects.equals(r.getDepartCity(),from)) && (java.util.Objects.equals(r.getArrivalCity(),to)) && (java.util.Objects.equals(r.getData(),date)) )
			found=true;
		}
			
		if (found) {
			rides.remove(index);
			return r;
		} else return null;
	}
	
}
