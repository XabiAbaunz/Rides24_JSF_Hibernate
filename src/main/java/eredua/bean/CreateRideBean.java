package eredua.bean;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;

import businessLogic.BLFacade;
import domain.Ride;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

public class CreateRideBean {
	
	private String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
	
	private String departCity;
	private String arrivalCity;
	private int seats;
	private float price;
	private Date data;
	
	BLFacade facade = FacadeBean.getBusinessLogic();
	
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

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String createRide() {
	    if (departCity == null || departCity.isEmpty() || arrivalCity == null || arrivalCity.isEmpty() || data == null || username == null || username.isEmpty()) {
	            FacesContext.getCurrentInstance().addMessage(null,
	                    new FacesMessage("Eremu guztiak beharrezkoak dira."));
	            return "error";
	        }
		try {
			if(seats <= 0 || seats >= 9) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Eserleku kopuruak 1 eta 8 artean egon behar du."));
						seats = 0;
						return "error";
			}
			if(price <= 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Prezioak ezin du 0 edo negatiboa izan."));
				price = 0;
				return "error";
			}
			Ride r = facade.createRide(getDepartCity(), getArrivalCity(), getData(), getSeats(), getPrice(), getUsername());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Bidaia sortu da."));
			departCity = "";
	        arrivalCity = "";
	        seats = 0;
	        price = 0;
	        data = null;
			return "ok";
		} catch (RideMustBeLaterThanTodayException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Bidaiaren eguna gaur ondorengoa izan behar du."));
			data = null;
			return "error";
		} catch (RideAlreadyExistException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Bidaia hori existitzen da jadanik."));
			departCity = "";
	        arrivalCity = "";
	        seats = 0;
	        price = 0;
	        data = null;
			return "error";
		}
	}
}
