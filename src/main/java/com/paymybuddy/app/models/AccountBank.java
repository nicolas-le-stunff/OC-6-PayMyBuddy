package com.paymybuddy.app.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="account_bank")
public class AccountBank {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@OneToOne
	private User user;

	@Column(name="name",nullable=false)
	private String name;

	@Column(name="iban",nullable=false)
	private String iban;

	@Column(name="address")
	private String address;

	@Column(name="city")
	private String city;

	@Column(name="zip")
	private int zip;

	@Column(name ="statut_active")
	private boolean statut_active;

	public int getId() {
		return id;	
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public int getZip() {
		return zip;
	}



	public void setZip(int zip) {
		this.zip = zip;
	}


	public boolean getStatutActive() {
		return statut_active;
	}

	public void setStatutActive(boolean statut_active){
		this.statut_active = statut_active;
	}

	public User getUser() {
		return user;
	}

	public User setUser(User user) {
		return this.user = user ;
	}


}
