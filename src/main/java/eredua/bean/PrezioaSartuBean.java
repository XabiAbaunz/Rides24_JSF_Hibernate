package eredua.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import businessLogic.BLFacade;
import domain.Ride;

public class PrezioaSartuBean {
	BLFacade facade = FacadeBean.getBusinessLogic();
	
	private float prezioa;
	private List<Ride> bidaiak;
	private Ride bidaia;
	
	public Ride getBidaia() {
		return bidaia;
	}

	public void setBidaia(Ride bidaia) {
		this.bidaia = bidaia;
	}

	public PrezioaSartuBean() {
		this.prezioa = 0;
		this.bidaiak = new ArrayList<>();
	}

	public List<Ride> getBidaiak() {
		return bidaiak;
	}

	public void setBidaiak(List<Ride> bidaiak) {
		this.bidaiak = bidaiak;
	}

	public float getPrezioa() {
		return prezioa;
	}

	public void setPrezioa(float prezioa) {
		this.prezioa = prezioa;
	}
	
	public String bilatu() {
		if(this.prezioa > 0.0) {
			bidaiak = facade.getPriceRides(prezioa);
			return "ok";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Prezio positibo bat sartu."));
			return "error";
		}
	}

}
