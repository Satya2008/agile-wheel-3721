package com.masai.Services;

public interface IStudentService {
	void registerStudent(String username, String password);
    void login(String username, String password);
    void enrollCourse(int courseId);
    void accessCourseMaterials(int courseId);
    void submitAssignment(int assignmentId, String submission);
    void trackProgress();
    void logout();
}
