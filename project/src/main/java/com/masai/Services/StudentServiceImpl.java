package com.masai.Services;

import java.util.List;

import com.masai.Dao.IStudentDao;
import com.masai.Dao.StudentsDaoImpl;
import com.masai.Entities.Assignment;
import com.masai.Entities.Course;
import com.masai.Entities.Discussion;
import com.masai.Entities.Grade;
import com.masai.Entities.Student;
import com.masai.Exceptions.NoRecordFoundException;
import com.masai.Exceptions.SomethingWentWrongException;

public class StudentServiceImpl implements IStudentService {

	@Override
	public void singUp(Student student) throws SomethingWentWrongException {

		IStudentDao st = new StudentsDaoImpl();
		st.singUp(student);

	}

	@Override
	public Student login(String username, String password) throws NoRecordFoundException {
		IStudentDao st = new StudentsDaoImpl();
		return st.login(username, password);

	}

	@Override
	public void accessAssignments(Student loggedInStudent) {
		IStudentDao st = new StudentsDaoImpl();
		st.accessAssignments(loggedInStudent);

	}

	@Override
	public void trackProgress() {
		IStudentDao st = new StudentsDaoImpl();
		st.trackProgress();

	}

	@Override
	public void accessLectures(String username, String password)
			throws SomethingWentWrongException, NoRecordFoundException {
		IStudentDao st = new StudentsDaoImpl();
		st.accessLectures(username, password);

	}

	@Override
	public void accessReadings(String username, String password) throws SomethingWentWrongException {
		IStudentDao st = new StudentsDaoImpl();
		st.accessReadings(username, password);

	}

	@Override
	public void accessVideos(String username, String password) throws SomethingWentWrongException {
		IStudentDao st = new StudentsDaoImpl();
		st.accessVideos(username, password);

	}

	@Override
	public void logout() {
		IStudentDao st = new StudentsDaoImpl();
		st.logout();

	}

	@Override
	public List<Course> getEnrolledCourses(Student student) {
		IStudentDao st = new StudentsDaoImpl();

		return st.getEnrolledCourses(student);
	}

	@Override
	public List<Assignment> getAssignmentsByStudent(Student student) {
		IStudentDao st = new StudentsDaoImpl();

		return st.getAssignmentsByStudent(student);
	}

	@Override
	public void submitAssignment(Student student, Assignment assignment, String submission) {
		IStudentDao st = new StudentsDaoImpl();

		st.submitAssignment(student, assignment, submission);

	}

	@Override
	public List<Grade> getGradesByStudent(Student student) {
		IStudentDao st = new StudentsDaoImpl();

		return st.getGradesByStudent(student);
	}

	@Override
	public List<Discussion> getDiscussionsByCourse(Course course) {
		IStudentDao st = new StudentsDaoImpl();

		return st.getDiscussionsByCourse(course);
	}

	@Override
	public void createDiscussionPost(Student student, Course course, String content) {
		IStudentDao st = new StudentsDaoImpl();

		st.createDiscussionPost(student, course, content);

	}

	@Override
	public List<Discussion> getDiscussionPostsByDiscussion(Discussion discussion) {
		IStudentDao st = new StudentsDaoImpl();

		return st.getDiscussionPostsByDiscussion(discussion);
	}

}
