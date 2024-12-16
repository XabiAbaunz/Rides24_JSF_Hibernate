package domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;

@Entity
@DiscriminatorValue("TRAVELER")
public class Traveler extends User implements Serializable {
	private static final long serialVersionUID = 1L;

	public Traveler(String username, String email, String passwd) {
		super(username, email, passwd, "Traveler");
	}
	
	public Traveler() {
		super();
	}

	@Override
	public String toString() {
		return super.toString();
	}
}
