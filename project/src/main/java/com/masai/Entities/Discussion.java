package com.masai.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "discussion")
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String topic;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @ManyToOne
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

	public Discussion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Discussion(String topic, Course course, Instructor instructor, Enrollment enrollment) {
		super();
		
		this.topic = topic;
		this.course = course;
		this.instructor = instructor;
		this.enrollment = enrollment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public Enrollment getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(Enrollment enrollment) {
		this.enrollment = enrollment;
	}

	@Override
	public String toString() {
		return "Discussion [id=" + id + ", topic=" + topic + ", course=" + course + ", instructor=" + instructor
				+ ", enrollment=" + enrollment + "]";
	}

   
}
