package com.masai.Dao;

public interface IInstructorDao {
	
	    void register(String username, String password);
	    void login(String username, String password);
	    void createLesson();
	    void readLesson();
	    void updateLesson();
	    void deleteLesson();
	    void createAssignment();
	    void readAssignment();
	    void updateAssignment();
	    void deleteAssignment();
	    void createQuiz();
	    void readQuiz();
	    void updateQuiz();
	    void deleteQuiz();
	    void createAssessment();
	    void readAssessment();
	    void updateAssessment();
	    void deleteAssessment();
	    void logout();
	

}
