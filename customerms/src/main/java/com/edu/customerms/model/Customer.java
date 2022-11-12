package com.edu.customerms.model;

import java.io.Serializable;

import javax.persistence.Entity;

import lombok.Data;
@Data
@Entity
public class Customer implements Serializable{
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String address;
	

}
