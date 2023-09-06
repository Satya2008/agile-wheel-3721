package com.masai.Services;

import com.masai.Entities.Assignment;
import com.masai.Entities.Course;



import java.util.List;

import com.masai.Entities.Discussion;
import com.masai.Entities.Grade;
import com.masai.Entities.Instructor;
import com.masai.Entities.Student;
import com.masai.Exceptions.NoRecordFoundException;
import com.masai.Exceptions.SomethingWentWrongException;

public interface IInstructorService {
    void register(Instructor instructor) throws SomethingWentWrongException;

    Instructor login(String username, String password) throws NoRecordFoundException;

    List<Course> getCoursesByInstructorId(int instructorId) throws SomethingWentWrongException;
    

    void createCourse(Course course, int instructorId) throws SomethingWentWrongException;
     
    void updateCourse(Course course, int courseId, int instructorId) throws SomethingWentWrongException, NoRecordFoundException;
    
    void deleteCourse(int courseId) throws SomethingWentWrongException, NoRecordFoundException;

    List<Assignment> getAssignmentsByCourseId(int courseId) throws SomethingWentWrongException;

    void createAssignment(Assignment assignment, int courseid, int studentId) throws SomethingWentWrongException;

   
    void updateAssignment(Assignment assignment, int assignmentId, int courseId, int studentId) throws SomethingWentWrongException, NoRecordFoundException;
    
    void deleteAssignment(int assignmentId) throws SomethingWentWrongException, NoRecordFoundException;

    List<Grade> getGradesByAssignmentId(int assignmentId) throws SomethingWentWrongException;

    void gradeAssignment(Student student, Assignment assignment, int score) throws SomethingWentWrongException, NoRecordFoundException;

    List<Discussion> getDiscussionsByCourseId(int courseId)throws SomethingWentWrongException;

    void createDiscussion(Discussion discussion, int courseId) throws SomethingWentWrongException;

    void updateDiscussion(int discussionId) throws SomethingWentWrongException, NoRecordFoundException;

    void deleteDiscussion(int discussionId ) throws SomethingWentWrongException, NoRecordFoundException;

//    List<DiscussionPost> getDiscussionPostsByDiscussion(Discussion discussion);
//
//    void createDiscussionPost(DiscussionPost post, Discussion discussion) throws SomethingWentWrongException;
//
//    void updateDiscussionPost(DiscussionPost post) throws SomethingWentWrongException, NoRecordFoundException;
//
//    void deleteDiscussionPost(DiscussionPost post) throws SomethingWentWrongException, NoRecordFoundException;

    void logout();

	}
