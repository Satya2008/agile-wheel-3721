package com.masai.Services;

import com.masai.Dao.IStudentDao;
import com.masai.Dao.StudentsDaoImpl;
import com.masai.Entities.Student;
import com.masai.Exceptions.NoRecordFoundException;
import com.masai.Exceptions.SomethingWentWrongException;

public class StudentServiceImpl implements IStudentService {

	@Override
	public void singUp(Student student) throws SomethingWentWrongException {

		IStudentDao st = new StudentsDaoImpl();
		st.signUp(student);

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
	public void submitAssignments(int studentId, int assignmentId) throws SomethingWentWrongException {
		IStudentDao st = new StudentsDaoImpl();
		st.submitAssignments(studentId, assignmentId);
		
		
	}
}
