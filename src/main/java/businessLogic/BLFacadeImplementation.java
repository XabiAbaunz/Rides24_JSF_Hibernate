package businessLogic;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import configuration.ConfigXML;
import dataAccess.HibernateDataAccess;
import domain.Ride;
import domain.Traveler;
import domain.User;
import domain.Driver;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

/**
 * It implements the business logic as a web service.
 */
public class BLFacadeImplementation  implements BLFacade {
	HibernateDataAccess dbManager;

	public BLFacadeImplementation()  {		
		System.out.println("Creating BLFacadeImplementation instance");
		
		
		    dbManager=new HibernateDataAccess();
		    
		//

		
	}
	
    public BLFacadeImplementation(HibernateDataAccess da)  {
		
		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c=ConfigXML.getInstance();
		
		dbManager=da;		
	}
    
    
    /**
     * {@inheritDoc}
     */
    public List<String> getDepartCities(){
    		
		
		 List<String> departLocations=dbManager.getDepartCities();		

		
		
		return departLocations;
    	
    }
    /**
     * {@inheritDoc}
     */
	public List<String> getDestinationCities(String from){
			
		
		 List<String> targetCities=dbManager.getArrivalCities(from);		

		
		
		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
   
   public Ride createRide( String from, String to, Date date, int nPlaces, float price, String driverEmail ) throws RideMustBeLaterThanTodayException, RideAlreadyExistException{
	   
		
		Ride ride=dbManager.createRide(from, to, date, nPlaces, price, driverEmail);		
		
		return ride;
   };
	
   /**
    * {@inheritDoc}
    */
	
	public List<Ride> getRides(String from, String to, Date date){
		
		List<Ride>  rides=dbManager.getRides(from, to, date);
		
		return rides;
	}

    
	/**
	 * {@inheritDoc}
	 */
	
	/*public List<Date> getThisMonthDatesWithRides(String from, String to, Date date){
		
		List<Date>  dates=dbManager.getThisMonthDatesWithRides(from, to, date);
		
		return dates;
	}*/
	
	
	public boolean addDriver(String username, String email, String pass) {
		
		Boolean ret = dbManager.addDriver(username, email, pass);
		
		return ret;
	}
	
	
	public boolean addTraveler(String username, String email, String pass) {
		
		Boolean ret = dbManager.addTraveler(username, email, pass);
		
		return ret;
	}
	
	
	public Driver getDriver(String username) {
		
		Driver d = dbManager.getDriver(username);
		
		return d;
	}
	
	
	public Traveler getTraveler(String username) {
		
		Traveler t = dbManager.getTraveler(username);
		
		return t;
	}
	
	
	public User getUser(String username) {
		
		User u = dbManager.getUser(username);
		
		return u;
	}
	

	/**
	 * {@inheritDoc}
	 */
    	
	 public void initializeBD(){
    	
		dbManager.initializeDB();
		
    }
	 
	public List<Ride> getPriceRides(float price) {
		List<Ride> rides = dbManager.getPriceRides(price);
		
		return rides;
	}
}

