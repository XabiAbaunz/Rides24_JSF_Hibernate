package eredua.bean;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import businessLogic.BLFacade;
import domain.Ride;

public class QueryRidesBean {

	private String departCity = null;
	private String arrivalCity = null;
	private Date rideDate;
	private List<String> departCities;
	private List<String> arrivalCities;
	private List<Ride> rides;

	private Ride selectedRide;

	BLFacade facade = FacadeBean.getBusinessLogic();

	public QueryRidesBean() {
		updateDepartCities();
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

	public Date getRideDate() {
		return rideDate;
	}

	public void setRideDate(Date rideDate) {
		this.rideDate = rideDate;
	}

	public List<String> getDepartCities() {
		return departCities;
	}

	public void setDepartCities(List<String> departCities) {
		this.departCities = departCities;
	}

	public List<String> getArrivalCities() {
		return arrivalCities;
	}

	public void setArrivalCities(List<String> arrivalCities) {
		this.arrivalCities = arrivalCities;
	}

	public List<Ride> getRides() {
		return rides;
	}

	public BLFacade getFacade() {
		return facade;
	}

	public void setFacade(BLFacade facade) {
		this.facade = facade;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}
	
	public Ride getSelectedRide() {
		return selectedRide;
	}

	public void setSelectedRide(Ride selectedRide) {
		this.selectedRide = selectedRide;
	}

	public void onDepartCityChange(AjaxBehaviorEvent event) {
		this.arrivalCities = facade.getDestinationCities(this.departCity);
		System.out.println(this.arrivalCities);
		this.arrivalCity = null;
		this.rides = null;
	}

	public void updateRidesTable() {
		if (departCity == null || departCity.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Aukeratu irteera hiri bat."));
			rides.clear();
			return;
		}
		if (arrivalCity == null || arrivalCity.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Aukeratu helmugako hiri bat."));
			rides.clear();
			return;
		}
		if (rideDate == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Aukeratu data bat."));
			rides.clear();
			return;
		}

		this.rides = facade.getRides(departCity, arrivalCity, rideDate);
		System.out.println(rides);
		if (this.rides == null || this.rides.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Ez dago bidaiarik data horretan."));
		}
	}
	
	public void updateDepartCities() {
		this.departCities = facade.getDepartCities();
	}
}
