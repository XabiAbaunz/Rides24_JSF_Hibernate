package eredua.bean;

import businessLogic.*;

public class MainGUIBean {
	BLFacade facade = FacadeBean.getBusinessLogic();
	
	public String login() {
		return "1";
	}
	
	public String register() {
		return "2";
	}
	
	public String guest() {
		return "3";
	}
	
	public String prezioBatenBidaiak() {
		return "4";
	}
 }
