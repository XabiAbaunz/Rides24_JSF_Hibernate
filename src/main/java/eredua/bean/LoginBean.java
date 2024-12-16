package eredua.bean;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import businessLogic.BLFacade;
import domain.User;

public class LoginBean {
	private String username;
	private String password;
	
	BLFacade facade = FacadeBean.getBusinessLogic();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String login() {
		User user = facade.getUser(this.getUsername());
		System.out.println(user);
		if(user!=null) {
			if(this.getPassword().equals(user.getPassword())) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("username", user.getUsername());
				return user.getMota();
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Pasahitza okerra da."));
				return null;
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Ez zaude erregistratuta."));
			return null;
		}
	}
}
