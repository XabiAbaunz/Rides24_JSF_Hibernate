package dataAccess;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.TypedQuery;

import org.hibernate.Query;
import org.hibernate.Session;

import configuration.UtilDate;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class HibernateDataAccess {
	
	public HibernateDataAccess() {}
	
	public void initializeDB() {
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		try {

			   Calendar today = Calendar.getInstance();
			   
			   int month=today.get(Calendar.MONTH);
			   int year=today.get(Calendar.YEAR);
			   if (month==12) { month=1; year+=1;}  
		    
			   
			    //Create drivers 
				Driver driver1=new Driver("Xabi", "xabi1@gmail.com","1234");

				//Create rides
				driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year,month,15), 4, 7);
				driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year,month,6), 4, 8);
				driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year,month,25), 4, 4);
				driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year,month,7), 4, 8);
						
				session.save(driver1);

				session.getTransaction().commit();
				System.out.println("DB initialized");
			}
			catch (Exception e){
				e.printStackTrace();
				session.getTransaction().rollback();
			}
	}
	
	public List<String> getDepartCities() {
		System.out.println(">> DataAccess: getDepartCities");
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		List result = session.createQuery("SELECT DISTINCT r.departCity FROM Ride r ORDER BY r.departCity").list();
		session.getTransaction().commit();
		return result;
		
	}
	
	public List<String> getArrivalCities(String from) {
		System.out.println(">> DataAccess: getArrivalCities=> from= "+from);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query q = session.createQuery("SELECT DISTINCT r.arrivalCity FROM Ride r WHERE r.departCity=:departCity ORDER BY r.arrivalCity");
		q.setParameter("departCity", from);
		
		List result = q.list();
		session.getTransaction().commit();
		return result;
	}
	
	public Ride createRide(String from, String to, Date date, int nPlaces, float price, String username) throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= "+from+" to= "+to+" driver="+username+" date "+date);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		try {
			if(new Date().compareTo(date)>0) {
				throw new RideMustBeLaterThanTodayException("Bidaiak gaur ondorengoa izan behar du.");
			}
			
			Query q = session.createQuery("from Driver where username= :username");
			q.setParameter("username", username);
			Driver driver = (Driver) q.uniqueResult();
			if (driver.doesRideExists(from, to, date)) {
				session.getTransaction().commit();
				throw new RideAlreadyExistException("Bidaia existitzen da jadanik");
			}
			Ride ride = driver.addRide(from, to, date, nPlaces, price);
			session.save(ride);
			//next instruction can be obviated
			session.save(driver); 
			session.getTransaction().commit();

			return ride;
		} catch (NullPointerException e) {
			session.getTransaction().rollback();
			return null;
		}
	}
	
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= "+from+" to= "+to+" date "+date);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query q = session.createQuery("SELECT r FROM Ride r WHERE r.departCity=:departCity AND r.arrivalCity=:arrivalCity AND r.data=:data");   
		q.setParameter("departCity", from);
		q.setParameter("arrivalCity", to);
		q.setParameter("data", date);
		
		@SuppressWarnings("unchecked")
		List<Ride> rides = q.list();
		session.getTransaction().commit();
		return rides;
	}
	
	public Traveler getTraveler(String user) {
		System.out.println(">> DataAccess: getTraveler=> username= "+user);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query q = session.createQuery("from Traveler where username= :user");
		q.setParameter("user", user);
		
		Traveler traveler = (Traveler) q.uniqueResult();
		session.getTransaction().commit();
		return traveler;
	}
	
	public Driver getDriver(String user) {
		System.out.println(">> DataAccess: getDriver=> username= "+user);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query q = session.createQuery("from Driver where username= :user");
		q.setParameter("user", user);
		
		Driver driver = (Driver) q.uniqueResult();
		session.getTransaction().commit();
		return driver;
	}
	
	public User getUser(String username) {
		System.out.println(">> DataAccess: getUser=> username= "+username);
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		
		Query q = session.createQuery("from User where username= :user");
		q.setParameter("user", username);
		
		User user = (User) q.uniqueResult();
		session.getTransaction().commit();
		return user;
	}
	
	public boolean addDriver(String username, String email, String password) {
		System.out.println(">> DataAccess: addedDriver=> username= "+username+ " email= " + email);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			
			Query q = session.createQuery("from User where username= :username");
			q.setParameter("username", username);
			
			User user = (User) q.uniqueResult();
			if (user != null) {
				return false;
			}
	
			Driver driver = new Driver(username, email, password);
			session.save(driver);
			session.getTransaction().commit();
			return true;
		} catch(Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean addTraveler(String username, String email, String password) {
		System.out.println(">> DataAccess: addedTraveler=> username= "+username+ " email= " + email);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		try {
			session.beginTransaction();
			
			Query q = session.createQuery("from User where username= :username");
			q.setParameter("username", username);
			
			User user = (User) q.uniqueResult();
			if (user != null) {
				return false;
			}
	
			Traveler traveler = new Traveler(username, email, password);
			session.save(traveler);
			session.getTransaction().commit();
			return true;
		} catch(Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Ride> getPriceRides(float price) {
		System.out.println(">> DataAccess: getPriceRides=> price= "+ price);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		
		session.beginTransaction();
		
		Query q = session.createQuery("from Ride where price= :price");
		q.setParameter("price", price);
			
		@SuppressWarnings("unchecked")
		List<Ride> rides = q.list();
		session.getTransaction().commit();
		return rides;
	}
}
