package eredua.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import businessLogic.BLFacade;

public class RegisterBean {
	private String username;
	private String email;
	private String password;
	private boolean gidaria;
	private boolean bidaiaria;
	
	BLFacade facade = FacadeBean.getBusinessLogic();

	public String getUsername() { return username; }
	public void setUsername(String username) { this.username = username; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	public boolean isGidaria() { return gidaria; }
	public void setGidaria(boolean gidaria) {
		if(gidaria) {
			this.bidaiaria = false;		}
		this.gidaria = gidaria; 
	}

	public boolean isBidaiaria() { return bidaiaria; }
	public void setBidaiaria(boolean bidaiaria) { 
		if(bidaiaria) {
			this.gidaria = false;
		}
		this.bidaiaria = bidaiaria; 
	}

	public String register() {
		Boolean registered = false;
		if(this.isGidaria()) {
			registered = facade.addDriver(this.getUsername(), this.getEmail(), this.getPassword());
		} else if(this.isBidaiaria()) {
			registered = facade.addTraveler(this.getUsername(), this.getEmail(), this.getPassword());
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Ez duzu erabiltzaile mota aukeratu."));
			return null;
		}
		if(registered) return "ok";
		else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Erabiltzailea jadanik erregistratuta dago."));
			return null;
		}
				
	}
}
