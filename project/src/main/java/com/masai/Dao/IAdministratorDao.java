package com.masai.Dao;

public interface IAdministratorDao {
    void login(String username, String password);
    void monitorCourseProgress();
    void trackStudentPerformance();
    void generateCourseStatistics();
    void logout();
}