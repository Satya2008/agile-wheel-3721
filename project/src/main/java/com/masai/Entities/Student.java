package com.masai.Entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    
    @Column(name = "mobile_no",nullable = false)
    private String MobileNo;

    @ManyToMany(mappedBy = "students")
    private List<Course> courses;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;

	public Student() {
		super();
		
	}

	public Student( String username, String password, String name, LocalDate dateOfBirth, String mobileNo,
			List<Course> courses, List<Enrollment> enrollments) {
		super();
	
		this.username = username;
		this.password = password;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		MobileNo = mobileNo;
		this.courses = courses;
		this.enrollments = enrollments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMobileNo() {
		return MobileNo;
	}

	public void setMobileNo(String mobileNo) {
		MobileNo = mobileNo;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Enrollment> getEnrollments() {
		return enrollments;
	}

	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name
				+ ", dateOfBirth=" + dateOfBirth + ", MobileNo=" + MobileNo + ", courses=" + courses + ", enrollments="
				+ enrollments + "]";
	}
  
	
  
	
}

   

