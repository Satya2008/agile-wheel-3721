package com.masai.Entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
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
    private String mobileNo;

//    @ManyToMany(mappedBy = "students")
//    private List<Course> courses;
    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Enrollment> enrollments;
    
    @Column(name = "lecture_access_count")
    private int lectureAccessCount;
    
    @Column(name = "reading_access_count")
    private int readingAccessCount;
    
    @Column(name = "video_access_count")
    private int videoAccessCount;
    @OneToMany(mappedBy = "student")
    private List<Assignment> assignments;
    

    public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	public Student() {
        super();
    }

    public Student(String username, String password, String name, LocalDate dateOfBirth, String mobileNo,
            List<Course> courses, List<Enrollment> enrollments, int lectureAccessCount, int readingAccessCount,
            int videoAccessCount) {
        super();
        this.username = username;
        this.password = password;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.mobileNo = mobileNo;
        this.courses = courses;
        this.enrollments = enrollments;
        this.lectureAccessCount = lectureAccessCount;
        this.readingAccessCount = readingAccessCount;
        this.videoAccessCount = videoAccessCount;
    }

    // Getters and setters

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
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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

    public int getLectureAccessCount() {
        return lectureAccessCount;
    }

    public void setLectureAccessCount(int lectureAccessCount) {
        this.lectureAccessCount = lectureAccessCount;
    }

    public int getReadingAccessCount() {
        return readingAccessCount;
    }

    public void setReadingAccessCount(int readingAccessCount) {
        this.readingAccessCount = readingAccessCount;
    }

    public int getVideoAccessCount() {
        return videoAccessCount;
    }

    public void setVideoAccessCount(int videoAccessCount) {
        this.videoAccessCount = videoAccessCount;
    }
}
