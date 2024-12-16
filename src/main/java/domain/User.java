package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String username;
	private String email;
	private String passwd;
	private String mota;
	private double money;

	public User(String username, String email, String passwd, String mota) {
		this.username = username;
		this.email = email;
		this.passwd = passwd;
		this.mota = mota;
		this.money = 0;
	}

	public User() {}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String email) {
		this.username = email;
	}

	public String getPassword() {
		return passwd;
	}

	public void setPassword(String name) {
		this.passwd = name;
	}

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String toString() {
		return username + ";" + mota;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username != other.username)
			return false;
		return true;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
