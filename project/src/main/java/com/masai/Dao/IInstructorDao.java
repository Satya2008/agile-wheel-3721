package com.masai.Dao;

import java.util.List;

import com.masai.Entities.Assignment;
import com.masai.Entities.Course;
import com.masai.Entities.Discussion;
import com.masai.Entities.Grade;
import com.masai.Entities.Instructor;
import com.masai.Entities.Student;
import com.masai.Exceptions.NoRecordFoundException;
import com.masai.Exceptions.SomethingWentWrongException;

public interface IInstructorDao {

    void register(Instructor instructor) throws SomethingWentWrongException;

    Instructor login(String username, String password) throws NoRecordFoundException;

    List<Course> getCoursesByInstructor(Instructor instructor);
    

    void createCourse(Course course) throws SomethingWentWrongException;

    void updateCourse(Course course) throws SomethingWentWrongException, NoRecordFoundException;

    void deleteCourse(Course course) throws SomethingWentWrongException, NoRecordFoundException;

    List<Assignment> getAssignmentsByCourse(Course course);

    void createAssignment(Assignment assignment, Course course) throws SomethingWentWrongException;

    void updateCourse(Course course, int instructorId) throws SomethingWentWrongException, NoRecordFoundException;
   
    void updateAssignment(Assignment assignment) throws SomethingWentWrongException, NoRecordFoundException;
    
    void deleteAssignment(Assignment assignment) throws SomethingWentWrongException, NoRecordFoundException;

    List<Grade> getGradesByAssignment(Assignment assignment);

    void gradeAssignment(Student student, Assignment assignment, int score) throws SomethingWentWrongException, NoRecordFoundException;

    List<Discussion> getDiscussionsByCourse(Course course);

    void createDiscussion(Discussion discussion, Course course) throws SomethingWentWrongException;

    void updateDiscussion(Discussion discussion) throws SomethingWentWrongException, NoRecordFoundException;

    void deleteDiscussion(Discussion discussion) throws SomethingWentWrongException, NoRecordFoundException;

//    List<DiscussionPost> getDiscussionPostsByDiscussion(Discussion discussion);
//
//    void createDiscussionPost(DiscussionPost post, Discussion discussion) throws SomethingWentWrongException;
//
//    void updateDiscussionPost(DiscussionPost post) throws SomethingWentWrongException, NoRecordFoundException;
//
//    void deleteDiscussionPost(DiscussionPost post) throws SomethingWentWrongException, NoRecordFoundException;

    void logout();

	

	
}
