package com.masai.Services;

public interface IAdministratorService {
    void login(String username, String password);
    void monitorCourseProgress();
    void trackStudentPerformance();
    void generateCourseStatistics();
    void logout();
}