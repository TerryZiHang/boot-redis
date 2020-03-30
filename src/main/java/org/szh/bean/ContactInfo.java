package org.szh.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class ContactInfo implements Serializable{
	
	private static final long serialVersionUID = 6243754144029437971L;

	private String markId;
	
	private String name;
	
	private String phone;
	
	private String email;
	
	private String position;

}
