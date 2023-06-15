package com.masai.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "administrator")
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

	public Administrator() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Administrator( String username, String password) {
		super();
		
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}


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
   
	@Override
	public String toString() {
		return "Administrator [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
   
}