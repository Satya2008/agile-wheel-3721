package com.masai.Main;

import java.util.Scanner;

import com.masai.Services.IInstructorService;
import com.masai.Services.InstructorServiceImpl;
import com.masai.UI.InstructorUI;
import com.masai.UI.StudentUI;

public class Main {

	 static void handleStudentMenu(Scanner scanner) {
		System.out.println("--- Student Menu ---");
		System.out.println("1. Log In");
		System.out.println("2. Sign Up");
		System.out.println("0. Back to Main Menu");
		System.out.print("Enter your choice: ");
		int choice = scanner.nextInt();
		scanner.nextLine(); 

		switch (choice) {
		case 1:
			StudentUI.logIn(scanner);
			break;
		case 2:
			StudentUI.SingUp(scanner);
			break;
		case 0:
			break;
		default:
			System.out.println("Invalid choice. Please try again.");
			break;
		}
	}

	 static void handleAdministratorMenu(Scanner scanner) {
		System.out.println("--- Administrator Menu ---");
		System.out.println("1. Log In");
		System.out.println("0. Back to Main Menu");
		System.out.print("Enter your choice: ");
		int choice = scanner.nextInt();
		scanner.nextLine(); // Consume the newline character

		switch (choice) {
		case 1:
//                administratorLogIn(scanner);
			break;
		case 0:
			break;
		default:
			System.out.println("Invalid choice. Please try again.");
			break;
		}
	}

	 static void handleInstructorMenu(Scanner scanner) {
		System.out.println("--- Instructor Menu ---");
		System.out.println("1. Log In");
		System.out.println("2. Sing Up");
		System.out.println("0. Back to Main Menu");
		System.out.print("Enter your choice: ");
		int choice = scanner.nextInt();
		scanner.nextLine(); // Consume the newline character

		switch (choice) {
		case 1:
			InstructorUI.logIn(scanner);
			break;
		case 2:
			InstructorUI.SingUp(scanner);
			break;
		case 0:
			break;
		default:
			System.out.println("Invalid choice. Please try again.");
			break;
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int choice;
		do {
			System.out.println("Whelcome to online learning Management System!!");
			System.out.println("1. Administrator");
			System.out.println("2. Instructor");
			System.out.println("3. Student");
			System.out.println("0. Exit");
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();
			switch (choice) {
			case 1:

				handleAdministratorMenu(sc);
				break;
			case 2:
				handleInstructorMenu(sc);
				break;
			case 3:

				handleStudentMenu(sc);
				break;
			case 0:
				System.out.println("Thank you for using the Online Learning Management System. Goodbye!");
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				break;
			}

		} while (choice != 0);
	}
}
