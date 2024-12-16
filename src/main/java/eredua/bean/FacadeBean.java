package eredua.bean;

import businessLogic.*;
import dataAccess.*;

public class FacadeBean {
	private static FacadeBean singleton = new FacadeBean();
	private static BLFacade facadeInterface;
	private FacadeBean(){
		try { facadeInterface=new BLFacadeImplementation(); }
		catch (Exception e) {
			System.out.println("FacadeBean: negozioaren logika sortzean errorea: "+e.getMessage());
		}}
	public static BLFacade getBusinessLogic( ) {
		if(facadeInterface==null) {
			singleton = new FacadeBean();
		}
		return facadeInterface;
	}
}
