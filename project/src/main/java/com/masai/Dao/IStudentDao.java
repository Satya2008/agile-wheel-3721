package com.masai.Dao;

public interface IStudentDao {

	
	
	    void register(String username, String password);
	    void login(String username, String password);
	    void accessLectures();
	    void accessReadings();
	    void accessVideos();
	    void accessAssignments();
	    void submitAssignment();
	    void trackProgress();
	    void logout();
	}


