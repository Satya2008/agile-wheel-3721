package com.masai.UI;

public class AdministratorUI {
	
	public static void logIn(String username, String password) {
		if(username.equalsIgnoreCase("admin") && password.equalsIgnoreCase("admin")) {
			System.out.println("Log in Successfully");
			
		}else {
			System.out.println("invalid Creadential");
		}
	}

}
