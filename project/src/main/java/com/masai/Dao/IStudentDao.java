package com.masai.Dao;

import java.util.List;

import com.masai.Entities.Assignment;
import com.masai.Entities.Course;
import com.masai.Entities.Discussion;
import com.masai.Entities.Grade;
import com.masai.Entities.Student;
import com.masai.Exceptions.NoRecordFoundException;
import com.masai.Exceptions.SomethingWentWrongException;

public interface IStudentDao {

	
	
	    void singUp(Student student) throws SomethingWentWrongException;
	    Student login(String username, String password) throws NoRecordFoundException;
	   
	    void accessAssignments(Student loggedInStudent);
	    void trackProgress();
	    void accessLectures(String username, String password) throws SomethingWentWrongException, NoRecordFoundException;
	    void accessReadings(String username, String password)throws SomethingWentWrongException;
	    void accessVideos(String username, String password)throws SomethingWentWrongException;
	    void logout();
	    List<Course> getEnrolledCourses(Student student);
	    List<Assignment> getAssignmentsByStudent(Student student);
	    void submitAssignment(Student student, Assignment assignment, String submission);
	    List<Grade> getGradesByStudent(Student student);
	    List<Discussion> getDiscussionsByCourse(Course course);
	    void createDiscussionPost(Student student, Course course, String content);
	    List<Discussion> getDiscussionPostsByDiscussion(Discussion discussion);
		

		
	}


