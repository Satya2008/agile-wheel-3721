package com.masai.Services;

import com.masai.Entities.Assignment;
import com.masai.Entities.Course;
import com.masai.Entities.Lesson;
import com.masai.Entities.Quiz;

public interface IInstructorService {
    void registerInstructor(String username, String password);
    void login(String username, String password);
    void createCourse(Course course);
    void updateCourse(Course course);
    void deleteCourse(int courseId);
    void createLesson(Lesson lesson);
    void updateLesson(Lesson lesson);
    void deleteLesson(int lessonId);
    void createAssignment(Assignment assignment);
    void updateAssignment(Assignment assignment);
    void deleteAssignment(int assignmentId);
    void createQuiz(Quiz quiz);
    void updateQuiz(Quiz quiz);
    void deleteQuiz(int quizId);
    void logout();
}
